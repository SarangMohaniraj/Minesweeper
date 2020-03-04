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
public static final int EMPTY = -10;
public static final int BOMB = -2;
public static final int FLAG = -1;



  private int bombs;
  private Tile[][] grid;
  public static boolean firstClick;
  java.util.Timer timer;
  private int time;

  public Grid(int mode, JPanel panel){
    panel.setLayout(new GridLayout(9,9));
    createGrid(mode,panel);
  }
  public void createGrid(int mode, JPanel panel){ //find a way to
  	firstClick = true;
    if(mode == BEGINNER){
      grid = new Tile[9][9];
      bombs = 10;
    }
    else if(mode == INTERMEDIATE){
      grid = new Tile[16][16];
      bombs = 40;
    }
    else if(mode == EXPERT){
      grid = new Tile[16][30];
      bombs = 99;
    }
    for(int row = 0; row < grid.length; row++){
      for(int col = 0; col < grid[0].length; col++){
        Tile tile = new Tile(row,col);
        tile.getButton().addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
							int row = tile.getX();
							int col = tile.getY();
						if(e.getButton() == MouseEvent.BUTTON1){
							if(firstClick){
								init(row,col);
								firstClick = false;
							}
							if(tile.getState() == BOMB)
								System.out.println("YOU LOSE");
							else if(tile.getState() != FLAG){
								System.out.println("METHOD TO REVEAL TILE AND FIND NUMBER OF ADJACENT BOMBS");
							}
						}
						if(e.getButton() == MouseEvent.BUTTON3){
							if(tile.getState() == EMPTY)
								tile.setState(FLAG);
							else if(grid[row][col].getState() == FLAG){
								tile.setState(EMPTY);
							}
						}
					}
				});
				grid[row][col] = tile;
        panel.add(grid[row][col].getButton());
      }
    }
  }


  public void setBombs(int x, int y){
    for (int n = 0; n < bombs; n++){
      int row = (int)(Math.random()*grid.length);
      int col = (int)(Math.random()*grid[0].length);


      if(grid[row][col].getState() != BOMB && row != x && col != y && getNeighbors(row,col).size() > 0){ //bomb cannot be on first click
        grid[row][col].setState(BOMB);
        grid[row][col].getButton().setText("BOMB");
      }else
        n--;
    }

  }
  public void init(int x, int y){ //get location of first click so set bombs after each click
    setBombs(x,y);
    timer = new java.util.Timer();
  	time = 0;
    timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				time+=1;
				System.out.println(time);
			}
		}, 1000,1000);
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

            if (tx >= 0 && tx < grid.length && ty >= 0 && ty < grid[0].length) //checks if it is in bounds
                neighbors.add(grid[tx][ty]);
        }
    }
    return neighbors;
  }

  public int getNumRows(){return grid.length;}
  public int getNumCols(){return grid[0].length;}

}