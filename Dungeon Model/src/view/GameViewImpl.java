package view;

import controller.GameSwingController;
import model.dungeonsanddragons.ReadOnlyGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

/**
 * GameViewImpl class represents the Graphical User Interface(GUI) view of the game that contains
 * dungeonPanel representing the grid, menuBar representing the menuBar of the game,
 * playerCurrentLocationPanel representing the current location of the player, playerActionPanel
 * representing the actions available for the player and the player description panel showing the
 * details of the items collected by the treasure. Players can interact with the GUI using mouse
 * clicks or keyboard inputs. Since the driver uses this class to display and interact with the
 * view, this class is kept as public.
 */
public class GameViewImpl extends JFrame implements GameView {

  private GridPanel dungeonPanel;
  private final MenuPanel menuBar;
  private CurrentLocationPanel playerCurrentLocationPanel;
  private final ActionPanel playerActionPanel;
  private final DescriptionPanel playerDescriptionPanel;

  /**
   * A constructor to initialize the Game Frame with dungeonPanel representing the grid, menuBar
   * representing the menuBar of the game, playerCurrentLocationPanel representing the current
   * location of the player, playerActionPanel representing the actions available for the player
   * and the player description panel.
   */
  public GameViewImpl() {
    super("Dungeons And Dragons");

    playerActionPanel = new PlayerActionPanel();
    playerDescriptionPanel = new PlayerDescriptionPanel();
    menuBar = new MenuPanelImpl();

    setSize(1100, 820);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setBackground(new Color(200,161,230));
    this.setLayout(null);

    this.setJMenuBar((JMenuBar) menuBar);
    this.add((Component) playerActionPanel).setBounds(770,85, 290, 320);
    this.add((Component) playerDescriptionPanel).setBounds(320,10, 484, 50);

    setFocusable(true);
    setVisible(true);
    setResizable(false);
    revalidate();
    repaint();
  }

  /**
   * Get the set of feature callbacks that the view can use. This provides means for view to be
   * 'wired' with the controller. Callback is cascading onto the panels that exist in the frame.
   *
   * @param controller the set of feature callbacks as a Features object
   */
  @Override
  public void setFeatures(GameSwingController controller) {
    dungeonPanel = new DungeonPanel(controller);
    playerCurrentLocationPanel = new PlayerCurrentLocationPanel(controller);
    this.add((Component) dungeonPanel).setBounds(40,80,658,658);
    this.add((Component) playerCurrentLocationPanel).setBounds(770,440, 290, 300);
    menuBar.menuOptions(controller, 5, 5, 60, 5, 1, 2, 1, false);
    playerActionPanel.configurePlayerActions(controller, this);
    addKeyListener(new KeyBoardAdapterImpl(controller, this));
  }

  /**
   * Resets the focus on the appropriate part of the view that has the keyboard listener attached
   * to it, so that keyboard events will still flow through.
   */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  /**
   * Updates the dungeon panel to display the nodes that have only been visited by the player.
   *
   * @param readOnlyGame represents the read-only model of the game.
   */
  @Override
  public void updateDungeonPanel(ReadOnlyGame readOnlyGame) {
    BufferedImage img = dungeonPanel.dungeonNodeAtPlayersLocation(readOnlyGame);
    playerCurrentLocationPanel.updateCurrentLocation(img);
    playerDescriptionPanel.updatePlayerDescription(readOnlyGame);
    revalidate();
    repaint();
  }

  /**
   * Initializes the dungeon panel with the grid size entered by the user.
   *
   * @param rows represents the number of rows in the dungeon grid.
   * @param columns represents the number of columns in the dungeon grid.
   * @param readOnlyGame represents the read-only mode of the game.
   */
  @Override
  public void generateDungeonPanel(int rows, int columns, ReadOnlyGame readOnlyGame) {
    dungeonPanel.initializeDungeon(rows, columns);
    playerDescriptionPanel.initializePlayerDescription(readOnlyGame);
  }

  /**
   * Sets the toast message with the given string.
   *
   * @param message represents the string that needs to be displayed.
   */
  @Override
  public void displayPlayerAction(String message) {
    new PlayerActionDisplay(this.getRootPane(), message);
    resetFocus();
  }

  /**
   * Sets the toast message with the given string.
   *
   * @param message represents the string that needs to be displayed.
   * @param delay represents the delay of the toast message.
   */
  @Override
  public void displayPlayerAction(String message, int delay) {
    new PlayerActionDisplay(this.getRootPane(), message, delay);
    resetFocus();
  }

  /**
   * Pop up message when the game gets over. Game gets over either when the player reaches the end
   * cave or gets killed or falls into pit.
   *
   * @param controller represents the GUI controller.
   * @param readOnlyGame represents the readOnly model of the game.
   * @param title represents the title of the Dialog box.
   */
  @Override
  public void gameOver(GameSwingController controller, ReadOnlyGame readOnlyGame, String title) {

    Object[] options = {"New Game", "Restart Game", "Quit Game"};

    String message = String.format("You end the game with\n"
        + "Rubies: %d\n"
        + "Diamonds: %d\n"
        + "Sapphire: %d\n"
        + "Arrows: %d\n", readOnlyGame.getPlayerRubyCount(), readOnlyGame.getPlayerDiamondCount(),
        readOnlyGame.getPlayerSapphireCount(), readOnlyGame.getPlayerArrowCount());
    int n = JOptionPane.showOptionDialog(this,
        message,
        title,
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,     //do not use a custom Icon
        options,  //the titles of buttons
        options[0]);

    if (n == JOptionPane.YES_OPTION) {
      menuBar.startNewGame(controller);
    } else if (n == JOptionPane.NO_OPTION) {
      menuBar.restartGame(controller);
    } else {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
  }
}
