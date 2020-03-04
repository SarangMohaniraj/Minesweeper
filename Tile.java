import javax.swing.*;
import java.awt.event.*;
public class Tile{

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
  private int state = EMPTY;
  

  JToggleButton tile;
  public Tile(){
    tile = new JToggleButton();
    tile.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
          if(state == BOMB)
            System.out.println("YOU LOSE");
          else if(state != FLAG){
            System.out.println("METHOD TO REVEAL TILE AND FIND NUMBER OF ADJACENT BOMBS");
          }
        }
        if(e.getButton() == MouseEvent.BUTTON3){
          if(state == EMPTY)
            state = FLAG;
          else if(state == FLAG){
            state = EMPTY;
          }
        }
      }
    });
  }

  public JToggleButton getButton(){return tile;}

  public int getState(){return state;}
  public void setState(int state){this.state = state;}
  public String toString(){
    return "HI";
  }
}