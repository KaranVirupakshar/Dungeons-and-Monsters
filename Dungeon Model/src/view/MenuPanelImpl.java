package view;

import controller.GameSwingController;

import java.awt.event.WindowEvent;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * MenuPanelImpl class represents the Menu for the game. Menu has two menu items Game and Settings.
 * Game allows the player to start a new game with new settings, restart the game with the same
 * settings, or quit the game. Settings allows the player to change grid size, wrapping status,
 * treasure percentage, monsters, pits, thieves, interconnectivity. Since the MenuPanelImpl is a
 * component of the main game panel, this class is kept as package-private.
 */
class MenuPanelImpl extends JMenuBar implements MenuPanel {

  private final JMenuItem newGame;
  private final JMenuItem restartGame;
  private final JMenuItem quitGame;
  private final JMenuItem gridSettings;
  private final JMenuItem interconnectivitySettings;
  private final JMenuItem wrappingSettings;
  private final JMenuItem treasureSettings;
  private final JMenuItem otyughSettings;
  private final JMenuItem thiefSettings;
  private final JMenuItem pitSettings;
  private int gridRow;
  private int gridColumn;
  private int treasure;
  private boolean wrapping;
  private int monster;
  private int interconnectivity;
  private int enemy;
  private int obstacles;

  /**
   * A constructor to initialize the Menu Bar with menu items to change the settings of the game,
   * start, quit or reset the game.
   */
  public MenuPanelImpl() {

    newGame = new JMenuItem("New Game");
    restartGame = new JMenuItem("Restart Game");
    quitGame = new JMenuItem("Quit Game");
    gridSettings = new JMenuItem("Grid");
    interconnectivitySettings = new JMenuItem("Interconnectivity");
    treasureSettings = new JMenuItem("Treasure");
    otyughSettings = new JMenuItem("Otyughs");
    wrappingSettings = new JMenuItem();
    thiefSettings = new JMenuItem("Thieves");
    pitSettings = new JMenuItem("Pits");
    JMenu gameMenu = new JMenu();
    JMenu settingsMenu = new JMenu();
    gameMenu.setText("Game");
    this.add(gameMenu);
    settingsMenu.setText("Settings");
    this.add(settingsMenu);

    gameMenu.add(newGame);
    gameMenu.add(restartGame);
    gameMenu.add(quitGame);

    settingsMenu.add(gridSettings);
    settingsMenu.add(interconnectivitySettings);
    settingsMenu.add(treasureSettings);
    settingsMenu.add(otyughSettings);
    settingsMenu.add(thiefSettings);
    settingsMenu.add(pitSettings);
    settingsMenu.add(wrappingSettings);
  }

  /**
   * Reads the menu items entered by the user through different JMenu components.
   *
   * @param controller     represents the controller for the GUI game.
   * @param row            represents the number of rows in the grid.
   * @param column         represents the number of columns in the grid.
   * @param treasureNumber represents the treasure percentage in the game.
   * @param degree         represents the interconnectivity degree.
   * @param otyugh         represents the number of monster.
   * @param thieves        represents the number of thieves.
   * @param pits           represents the number of pits in the game.
   * @param status         represents the wrappind status.
   */
  public void menuOptions(GameSwingController controller, int row, int column,
      int treasureNumber, int degree, int otyugh, int thieves, int pits, boolean status) {

    gridSettings.setText("Grid Size -> " + row + " X " + column);
    treasureSettings.setText("Treasure -> " + treasureNumber + "%");
    interconnectivitySettings.setText("Interconnectivity -> " + degree);
    otyughSettings.setText("Otyughs -> " + otyugh);
    thiefSettings.setText("Thieves -> " + thieves);
    pitSettings.setText("Pits -> " + pits);
    wrappingSettings.setText("Wrapping -> " + status);

    gridRow = row;
    gridColumn = column;
    treasure = treasureNumber;
    wrapping = status;
    monster = otyugh;
    interconnectivity = degree;
    enemy = thieves;
    obstacles = pits;

    gridSettings.addActionListener(e -> {
      JTextField rows = new JTextField();
      rows.setText(String.valueOf(gridRow));
      JTextField columns = new JTextField();
      columns.setText(String.valueOf(gridColumn));
      Object[] message = {
          "Rows (min 5):", rows,
          "Columns (min 5):", columns
      };

      boolean validation = true;
      while (validation) {
        try {
          JOptionPane.showConfirmDialog(null, message, "Grid Settings", JOptionPane.PLAIN_MESSAGE);
          gridRow = Integer.parseInt(rows.getText());
          gridColumn = Integer.parseInt(columns.getText());
          gridSettings.setText("Grid Size -> " + gridRow + " X " + gridColumn);
          validation = false;
          JOptionPane.showConfirmDialog(null, "Remember to start a New Game for the settings "
              + "to take effect", "Settings", JOptionPane.PLAIN_MESSAGE);
        } catch (NumberFormatException numberFormatException) {
            /*Intentionally left empty so that the program does "nothing" and not interrupt the
            user experience.
            */
        }
      }
    });

    treasureSettings.addActionListener(e -> {
      boolean validation = true;
      while (validation) {
        try {
          String treasureString = JOptionPane.showInputDialog( "Treasure percentage", treasure);
          if (treasureString == null) {
            break;
          }
          else {
            treasure = Integer.parseInt(treasureString);
            treasureSettings.setText("Treasure -> " + treasure + "%");
            validation = false;
            JOptionPane.showConfirmDialog(null, "Remember to start a New Game for the settings "
                + "to take effect", "Settings", JOptionPane.PLAIN_MESSAGE);
          }
        } catch (NumberFormatException numberFormatException) {
            /*Intentionally left empty so that the program does "nothing" and not interrupt the
            user experience.
            */
        }
      }
    });

    interconnectivitySettings.addActionListener(e -> {
      boolean validation = true;
      while (validation) {
        try {
          String interconnectivityString = JOptionPane.showInputDialog("Interconnectivity degree",
              interconnectivity);
          if (interconnectivityString == null) {
            break;
          }
          else {
            interconnectivity = Integer.parseInt(interconnectivityString);
            interconnectivitySettings.setText("Interconnectivity -> " + interconnectivity);
            validation = false;
            JOptionPane.showConfirmDialog(null, "Remember to start a New Game for the settings "
                + "to take effect", "Settings", JOptionPane.PLAIN_MESSAGE);
          }
        } catch (NumberFormatException numberFormatException) {
            /*Intentionally left empty so that the program does "nothing" and not interrupt the
            user experience.
            */
        }
      }
    });

    otyughSettings.addActionListener(e -> {
      boolean validation = true;
      while (validation) {
        try {
          String monsterString = JOptionPane.showInputDialog("Monsters", monster);
          if (monsterString == null) {
            break;
          }
          else  {
            monster = Integer.parseInt(monsterString);
            otyughSettings.setText("Otyughs -> " + monster);
            validation = false;
            JOptionPane.showConfirmDialog(null, "Remember to start a New Game for the settings "
                + "to take effect", "Settings", JOptionPane.PLAIN_MESSAGE);
          }
        } catch (NumberFormatException numberFormatException) {
            /*Intentionally left empty so that the program does "nothing" and not interrupt the
            user experience.
            */
        }
      }
    });

    thiefSettings.addActionListener(e -> {
      boolean validation = true;
      while (validation) {
        try {
          String thiefString = JOptionPane.showInputDialog("Thieves", enemy);
          if (thiefString == null) {
            break;
          }
          else {
            enemy = Integer.parseInt(thiefString);
            thiefSettings.setText("Thieves -> " + enemy);
            validation = false;
            JOptionPane.showConfirmDialog(null, "Remember to start a New Game for the settings "
                + "to take effect", "Settings", JOptionPane.PLAIN_MESSAGE);
          }
        } catch (NumberFormatException numberFormatException) {
            /*Intentionally left empty so that the program does "nothing" and not interrupt the
            user experience.
            */
        }
      }
    });

    pitSettings.addActionListener(e -> {
      boolean validation = true;
      while (validation) {
        try {
          String pitString = JOptionPane.showInputDialog("Pits", obstacles);
          if (pitString == null) {
            break;
          }
          else {
            obstacles = Integer.parseInt(pitString);
            pitSettings.setText("Pits -> " + obstacles);
            validation = false;
            JOptionPane.showConfirmDialog(null, "Remember to start a New Game for the settings "
                + "to take effect", "Settings", JOptionPane.PLAIN_MESSAGE);
          }
        } catch (NumberFormatException numberFormatException) {
            /*Intentionally left empty so that the program does "nothing" and not interrupt the
            user experience.
            */
        }
      }
    });

    wrappingSettings.addActionListener(e -> {

      JRadioButton r1 = new JRadioButton("True");
      JRadioButton r2 = new JRadioButton("False");

      ButtonGroup bg = new ButtonGroup();
      bg.add(r1);
      bg.add(r2);
      bg.setSelected(wrapping ? r1.getModel() : r2.getModel(), true);

      JPanel panel = new JPanel();
      panel.add(r1);
      panel.add(r2);

      JOptionPane.showConfirmDialog(null, panel, "Wrapping Status", JOptionPane.PLAIN_MESSAGE);
      wrapping = bg.isSelected(r1.getModel());
      wrappingSettings.setText("Wrapping -> " + wrapping);
    });

    quitGame.addActionListener(e -> {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    });

    newGame.addActionListener(
        e -> startNewGame(controller));

    restartGame.addActionListener(
        e -> restartGame(controller));
  }

  /**
   * Starts a new game by creating a new model with a new seed.
   *
   * @param controller represents the GUI controller.
   */
  @Override
  public void startNewGame(GameSwingController controller) {
    controller.createModel(gridRow, gridColumn, wrapping, interconnectivity, treasure,
        monster, enemy, obstacles);
  }

  /**
   * Restarts the game with the same seed.
   *
   * @param controller represents the GUI controller.
   */
  @Override
  public void restartGame(GameSwingController controller) {
    controller.resetModel(gridRow, gridColumn, wrapping, interconnectivity, treasure,
        monster, enemy, obstacles);

  }
}
