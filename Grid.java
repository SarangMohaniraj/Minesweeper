import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class Grid{

  public static final int BEGINNER = 0;
  public static final int INTERMEDIATE = 1;
  public static final int EXPERT = 2;

/* State
 * Bomb is -2
 * Flag is -1
 * 0 - 8 is the number of bombs
 * Only after it is clicked
 * if 0, it will show an empty, clicked block
 * if 1-8, it will display the number of bombs
 */
public static final int BLOCK = -10;
public static final int BOMB = -2;
public static final int FLAG = -1;

public static final String CLASSIC = "classic";
public static final String MONOCHROME = "monochrome";
public static final String MARIO_KART = "mario_kart";


private String theme;
public static ImageIcon block, empty, one, two, three, four, five, six, seven, eight, mine, clickedmine, flagged, facedead, faceooh, facepressed, facesmile, facewin;

  private int rows = 9 , cols = 9, bombs = 10, initBombs = 10, numSelected = 0;
  private Tile[][] grid;
  public static boolean firstClick;
  java.util.Timer timer;
  private int time;

  private int mode;
  private JFrame frame;
  private JPanel panel;
  private JLabel mineLabel, timeLabel;
  private JButton resetButton;
  private boolean playing = true;

  public Grid(int mode, String theme, JFrame frame, JPanel panel, JLabel mineLabel, JLabel timeLabel, JButton resetButton){
    this.mode = mode;
    this.theme = theme;
    this.frame = frame;
    this.panel = panel;
    this.mineLabel = mineLabel;
    this.timeLabel = timeLabel;
    this.resetButton = resetButton;
    setTheme(theme);
    resetButton.setIcon(facesmile);
    resetButton.setPressedIcon(facepressed);
    resetButton.setPreferredSize(new Dimension(facesmile.getIconWidth(),facesmile.getIconHeight()));
    createGrid();
  }
  public void createGrid(){
  	firstClick = true;
    playing = true;
    if(mode == BEGINNER){
      rows = 9;
      cols = 9;
      bombs = 10;
    }
    else if(mode == INTERMEDIATE){
      rows = 16;
      cols = 16;
      bombs = 40;
    }
    else if(mode == EXPERT){
      rows = 16;
      cols = 30;
      bombs = 99;
    }
    initBombs = bombs;
    numSelected = 0;

    panel.setLayout(new GridLayout(rows,cols));
    grid = new Tile[rows][cols];

    timer = new java.util.Timer();
    time = 0;
    mineLabel.setText("Mine count: "+bombs);
    for(int row = 0; row < rows; row++){
      for(int col = 0; col < cols; col++){
        Tile tile = new Tile(row,col);
        tile.getButton().addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            if(playing) resetButton.setIcon(faceooh);
          }
					@Override
					public void mouseReleased(MouseEvent e) {
            if(playing) resetButton.setIcon(facesmile);
						int row = tile.getX();
						int col = tile.getY();
						if(playing && e.getButton() == MouseEvent.BUTTON1){
							if(firstClick){
								init(row,col);
								firstClick = false;
							}
							if(tile.getValue() == BOMB && tile.getState() != FLAG){
                tile.setState(BOMB);
                revealBombs(tile);
              }
							else if(tile.getState() != FLAG){
                expand(tile);
                if(numSelected == rows*cols-initBombs)
                  win();
							}
						}
						if(playing && e.getButton() == MouseEvent.BUTTON3){
							if(tile.getState() == BLOCK){
                tile.setState(FLAG);
                bombs--;
                mineLabel.setText("Mine count: "+bombs);
              }
							else if(grid[row][col].getState() == FLAG){
                tile.setState(BLOCK);
                bombs++;
                mineLabel.setText("Mine count: "+bombs);
							}
						}
					}
				});
				grid[row][col] = tile;
        panel.add(grid[row][col].getButton());
      }
    }
    frame.setSize(32*getNumCols(),32*getNumRows()+95);
    frame.revalidate();
  }


  public void setBombs(int x, int y){
    for (int n = 0; n < bombs; n++){
      int row = (int)(Math.random()*grid.length);
      int col = (int)(Math.random()*grid[0].length);


      if(grid[row][col].getValue() != BOMB && row != x && col != y && !getNeighbors(x,y).contains(grid[row][col])){ //bomb cannot be on first click
        grid[row][col].setValue(BOMB);
      }else
        n--;
    }
  }
  public void setNumbers(int x, int y){
    for(int row = 0; row < rows; row++){
      for(int col = 0; col < cols; col++){
        if(grid[row][col].getValue() != BOMB && (row != x || col != y)){
          int numMines = (int)getNeighbors(row,col).stream().filter(neighbor -> neighbor.getValue() == BOMB).count();
          grid[row][col].setValue(numMines);
        }
      }
    }
  }
  public void init(int x, int y){ //get location of first click so set bombs after each click
    setBombs(x,y);
    setNumbers(x,y);
    timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				time+=1;
				timeLabel.setText("Time: "+time);
			}
		}, 1000,1000);
    grid[x][y].setValue(0);
	}

  public ArrayList<Tile> getNeighbors(int row, int col) { //get adjacent nodes (up to 8)
    ArrayList<Tile> neighbors = new ArrayList<Tile>();

    //check 8 adjacent nodes relative to current node
    for (int x = -1; x <= 1; x++)
    {
        for (int y = -1; y <= 1; y++)
        {
            if (x == 0 && y == 0) //skips center since that is the relative origin
                continue;

            int tx = row + x;
            int ty = col + y;

            if (tx >= 0 && tx < rows && ty >= 0 && ty < cols) //checks if it is in bounds
                neighbors.add(grid[tx][ty]);
        }
    }
    return neighbors;
  }

  public ArrayList<Tile> getNeighbors(Tile tile){
    return getNeighbors(tile.getX(),tile.getY());
  }

  public void expand(Tile tile){
    if(tile.getButton().isEnabled()){
      numSelected++;
      tile.setState(tile.getValue());
      if(tile.getValue() == 0){
        getNeighbors(tile).stream().filter(neighbor -> neighbor.getButton().isEnabled()).forEach(neighbor -> expand(neighbor));
      }
    }
  }

  public void revealBombs(Tile tile){ //lose
    if(playing){
      playing = false;
      timer.cancel();
      Arrays.stream(grid).flatMap(a->Arrays.stream(a)).forEach(t -> {
        t.getButton().setEnabled(false);
        t.setImage(t.getIcon(t.getState())); //set disabled icon
      });
      Arrays.stream(grid).flatMap(a->Arrays.stream(a)).filter(t -> t.getValue() == BOMB && t != tile).forEach(t -> t.setState(t.getValue()));
      tile.setImage(clickedmine);
      resetButton.setIcon(facedead);
    }
  }

  public void win(){
    if(playing){
      playing = false;
      timer.cancel();
      Arrays.stream(grid).flatMap(a->Arrays.stream(a)).forEach(tile -> {
        if(tile.getValue() == BOMB && tile.getState() != FLAG)
          tile.setState(FLAG);
        tile.getButton().setEnabled(false);
        tile.setImage(tile.getIcon(tile.getState()));
      });
      resetButton.setIcon(facewin);
    }
  }

  public int getNumRows(){return rows;}
  public int getNumCols(){return cols;}

  public void reset(){
    timer.cancel();
    timeLabel.setText("Time: 0");
    frame.remove(panel);
    panel = new JPanel();
    frame.add(panel,BorderLayout.CENTER);
    resetButton.setIcon(facesmile);
    resetButton.setPressedIcon(facepressed);
    resetButton.setPreferredSize(new Dimension(facesmile.getIconWidth(),facesmile.getIconHeight()));
    createGrid();
  }
  public void reset(int mode){
    this.mode = mode;
    reset();
  }
  public void reset(String theme){
    this.theme = theme;
    setTheme(theme);
    reset();
  }


  public void setTheme(String theme){
    block = resizeIcon(new ImageIcon("assets/"+theme+"/block.png"));
    empty = resizeIcon(new ImageIcon("assets/"+theme+"/empty.png"));
    one = resizeIcon(new ImageIcon("assets/"+theme+"/one.png"));
    two = resizeIcon(new ImageIcon("assets/"+theme+"/two.png"));
    three = resizeIcon(new ImageIcon("assets/"+theme+"/three.png"));
    four = resizeIcon(new ImageIcon("assets/"+theme+"/four.png"));
    five = resizeIcon(new ImageIcon("assets/"+theme+"/five.png"));
    six = resizeIcon(new ImageIcon("assets/"+theme+"/six.png"));
    seven = resizeIcon(new ImageIcon("assets/"+theme+"/seven.png"));
    eight = resizeIcon(new ImageIcon("assets/"+theme+"/eight.png"));
    mine = resizeIcon(new ImageIcon("assets/"+theme+"/mine.png"));
    clickedmine = resizeIcon(new ImageIcon("assets/"+theme+"/clickedmine.png"));
    flagged = resizeIcon(new ImageIcon("assets/"+theme+"/flagged.png"));
    facedead = resizeIcon(new ImageIcon("assets/"+theme+"/facedead.png"));
    faceooh = resizeIcon(new ImageIcon("assets/"+theme+"/faceooh.png"));
    facepressed = resizeIcon(new ImageIcon("assets/"+theme+"/facepressed.png"));
    facesmile = resizeIcon(new ImageIcon("assets/"+theme+"/facesmile.png"));
    facewin = resizeIcon(new ImageIcon("assets/"+theme+"/facewin.png"));
  }

  public ImageIcon resizeIcon(ImageIcon icon){
    Image img = icon.getImage() ;  
    Image newimg = img.getScaledInstance( Tile.WIDTH, Tile.HEIGHT,  java.awt.Image.SCALE_SMOOTH ) ;  
    return new ImageIcon( newimg );
  }

}