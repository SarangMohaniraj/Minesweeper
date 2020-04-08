import javax.swing.*;
import java.awt.event.*;
public class Tile{

  private int state = Grid.BLOCK;
  private int value = Grid.BLOCK;
  private int x, y;
  JToggleButton tile;
  public static final int WIDTH = 29;
  public static final int HEIGHT = WIDTH;

  public Tile(int x, int y){
	  this.x = x;
	  this.y = y;
    tile = new JToggleButton(Grid.block);
    tile.setFocusPainted(false);
  }

  public JToggleButton getButton(){return tile;}

  public int getValue(){return value;}
  public void setValue(int value){this.value = value;}//should only be called in Grid.init()


  public int getState(){return state;}
  public void setState(int state){
    this.state = state;

    if(state >= 0)
      tile.setEnabled(false);

    setImage(getIcon(state));
  } 
  
  public int getX(){return x;}
  public int getY(){return y;}
  public void setImage(ImageIcon icon){
    if(tile.isEnabled())
      tile.setIcon(icon);
    else 
      tile.setDisabledIcon(icon);
  }
  public ImageIcon getIcon(int state){
    ImageIcon icon = Grid.block;
    if(state == Grid.BLOCK)
      icon = Grid.block;
    else if(state == Grid.FLAG)
      icon = Grid.flagged;
    else if(state == Grid.BOMB)
      icon = Grid.mine;
    else if(state == 0)
      icon = Grid.empty;
    else if(state == 1)
      icon = Grid.one;
    else if(state == 2)
      icon = Grid.two;
    else if(state == 3)
      icon = Grid.three;
    else if(state == 4)
      icon = Grid.four;
    else if(state == 5)
      icon = Grid.five;
    else if(state == 6)
      icon = Grid.six;
    else if(state == 7)
      icon = Grid.seven;
    else if(state == 8)
      icon = Grid.eight;
    return icon;
  }
}