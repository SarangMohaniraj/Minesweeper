import javax.swing.*;
import java.awt.event.*;
public class Tile{

private int state = Grid.EMPTY;
private int x;
private int y;
  JToggleButton tile;
  public Tile(int x, int y){
	  this.x = x;
	  this.y = y;
    tile = new JToggleButton();
  }

  public JToggleButton getButton(){return tile;}

  public int getState(){return state;}
  public void setState(int state){this.state = state;}
  public String toString(){
    return "HI";
  }
  public int getX(){return x;}
  public int getY(){return y;}
}