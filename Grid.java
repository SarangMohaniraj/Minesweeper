import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class Grid{

  public static final int BEGINNER = 0;
  public static final int INTERMEDIATE = 1;
  public static final int EXPERT = 2;
  private int bombs;
  private Tile[][] grid;

  public Grid(int mode, JPanel panel){
    panel.setLayout(new GridLayout(9,9));
    createGrid(mode,panel);
    init(4,6);
  }
  public void createGrid(int mode, JPanel panel){ //find a way to 
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
        grid[row][col] = new Tile();
        panel.add(grid[row][col].getButton());
      }
    }
  }

  public void startTimer(){
    new Timer(1000, new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
          //...Perform a task...
      }
    }).start();
  }

  public void setBombs(int x, int y){
    for (int n = 0; n < bombs; n++){
      int row = (int)(Math.random()*grid.length);
      int col = (int)(Math.random()*grid[0].length);


      if(grid[row][col].getState() != Tile.BOMB && row != x && col != y && getNeighbors(row,col).size() > 0){ //bomb cannot be on first click
        grid[row][col].setState(Tile.BOMB);
        grid[row][col].getButton().setText("BOMB");
      }else
        n--;
    }

  }
  public void init(int x, int y){ //get location of first click so set bombs after each click
    setBombs(x,y);
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

}