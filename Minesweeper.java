import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
public class Minesweeper extends JPanel{

  JFrame frame;
  JPanel northPanel, gamePanel;

  JMenuBar menuBar;
  JMenu game, display, controls;
  JMenuItem beginner, intermediate, expert;

  Grid grid;

  public Minesweeper(){
    frame = new JFrame("Minesweeper");
    frame.add(this);

    northPanel = new JPanel();
    northPanel.setLayout(new GridLayout(2,1));
    
    menuBar = new JMenuBar();
    game = new JMenu("Game");

    beginner = new JMenuItem("Beginner");
    intermediate = new JMenuItem("Intermediate");
    expert = new JMenuItem("Expert");

    game.add(beginner);
    game.add(intermediate);
    game.add(expert);
    menuBar.add(game);


    display = new JMenu("Display");
    menuBar.add(display);

    controls = new JMenu("Controls");
    menuBar.add(controls);

    northPanel.add(menuBar);
    
   
    frame.add(northPanel,BorderLayout.NORTH);
    gamePanel = new JPanel();
    grid = new Grid(Grid.BEGINNER,gamePanel);
    frame.add(gamePanel,BorderLayout.CENTER);

    frame.setSize(900,1000);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  

  public void paintComponent(Graphics g){
    super.paintComponent(g);
  }


  public static void main(String[] args){
    Minesweeper app = new Minesweeper();
  }


}