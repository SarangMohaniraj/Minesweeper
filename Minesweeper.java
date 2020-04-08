import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
public class Minesweeper extends JPanel{

  JFrame frame;
  JPanel northPanel, gamePanel, scorePanel;

  JMenuBar menuBar;
  JMenu game, display, controls;
  JMenuItem beginner, intermediate, expert, classic, theme1, theme2;
  JLabel mineLabel, timeLabel;
  JButton resetButton;

  Grid grid;

  public Minesweeper(){
    frame = new JFrame("Minesweeper");
    frame.add(this);

    northPanel = new JPanel();
    northPanel.setLayout(new GridLayout(2,1));

    menuBar = new JMenuBar();
    
    game = new JMenu("Game");
    beginner = new JMenuItem(new AbstractAction("Beginner") {
      public void actionPerformed(ActionEvent e) {
        grid.reset(Grid.BEGINNER);
      }
    });
    intermediate = new JMenuItem(new AbstractAction("Intermediate") {
      public void actionPerformed(ActionEvent e) {
        grid.reset(Grid.INTERMEDIATE);
      }
    });
    expert = new JMenuItem(new AbstractAction("Expert") {
      public void actionPerformed(ActionEvent e) {
        grid.reset(Grid.EXPERT);
        
      }
    });

    game.add(beginner);
    game.add(intermediate);
    game.add(expert);
    menuBar.add(game);


    display = new JMenu("Display");
    classic = new JMenuItem(new AbstractAction("Classic") {
      public void actionPerformed(ActionEvent e) {
        grid.reset(Grid.CLASSIC);
      }
    });
    theme1 = new JMenuItem(new AbstractAction("Monochrome") {
      public void actionPerformed(ActionEvent e) {
        grid.reset(Grid.MONOCHROME);
      }
    });
    theme2 = new JMenuItem(new AbstractAction("Mario Kart") {
      public void actionPerformed(ActionEvent e) {
        grid.reset(Grid.MARIO_KART);
      }
    });
    display.add(classic);
    display.add(theme1);
    display.add(theme2);
    menuBar.add(display);

    controls = new JMenu("Controls");
    JTextArea textArea = new JTextArea("   - Left-click an empty square to reveal it.\n   - Right-click an empty square to flag it.\n   - Press the middle top button to restart the game.   ");
    textArea.setEditable(false);
    controls.add(textArea);
    menuBar.add(controls);

    scorePanel = new JPanel();
    mineLabel = new JLabel("Mine count: ");
    timeLabel = new JLabel("Time: 0");

    gamePanel = new JPanel();

    resetButton = new JButton(new AbstractAction("") {
      public void actionPerformed(ActionEvent e) {
        grid.reset();
      }
    });

    scorePanel.add(mineLabel,BorderLayout.WEST);
    scorePanel.add(resetButton,BorderLayout.CENTER);
    scorePanel.add(timeLabel,BorderLayout.EAST);

    northPanel.add(menuBar);
    northPanel.add(scorePanel);

    frame.add(northPanel,BorderLayout.NORTH);
    frame.add(gamePanel,BorderLayout.CENTER);

    frame.setVisible(true);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    grid = new Grid(Grid.BEGINNER,Grid.CLASSIC,frame,gamePanel,mineLabel,timeLabel,resetButton);
  }


  public void paintComponent(Graphics g){
    super.paintComponent(g);
  }


  public static void main(String[] args){
    Minesweeper app = new Minesweeper();
  }


}