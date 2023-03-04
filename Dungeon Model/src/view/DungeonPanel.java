package view;

import controller.GameSwingController;
import model.dungeonsanddragons.ReadOnlyGame;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * DungeonPanel class represents a class that extends JPanel and provides the player with a view
 * of the dungeon locations. The grid initially starts with the starting location of the player and
 * opens up as the player explores. Each location also contains information about the node which
 * includes treasure, arrows, monsters, pits and thieves. Since the PlayerCurrentLocationPanel is
 * a component of the main game panel, this class is kept as package-private.
 */
class DungeonPanel extends JScrollPane implements GridPanel {

  private final JPanel dungeon;
  private JLabel [][] jLabels;
  private final BufferedImage arrows;
  private final BufferedImage rubies;
  private final BufferedImage diamonds;
  private final BufferedImage sapphires;
  private final BufferedImage stench1;
  private final BufferedImage stench2;
  private final BufferedImage thief;
  private final BufferedImage monster;
  private final BufferedImage sound;
  private final BufferedImage pit;
  private final BufferedImage dead;
  private int row;
  private int column;
  private String location;
  private final MouseListener m;

  /**
   * A constructor to initializes the DungeonPanel with all the images for the treasure, arrows,
   * player and locations.
   *
   * @param controller represents the controller of the GUI game.
   */
  public DungeonPanel(GameSwingController controller) {

    dungeon = new JPanel();
    dungeon.setBackground(Color.BLACK);

    try {
      rubies = ImageIO.read(ClassLoader.getSystemResource("ruby.png"));
      diamonds = ImageIO.read(ClassLoader.getSystemResource("diamond.png"));
      sapphires = ImageIO.read(ClassLoader.getSystemResource("emerald.png"));
      arrows = ImageIO.read(ClassLoader.getSystemResource("arrow.png"));
      stench1 = ImageIO.read(ClassLoader.getSystemResource("stench01.png"));
      stench2 = ImageIO.read(ClassLoader.getSystemResource("stench02.png"));
      thief = ImageIO.read(ClassLoader.getSystemResource("thief.png"));
      monster = ImageIO.read(ClassLoader.getSystemResource("otyugh.png"));
      sound = ImageIO.read(ClassLoader.getSystemResource("sound.png"));
      pit = ImageIO.read(ClassLoader.getSystemResource("pit.png"));
      dead = ImageIO.read(ClassLoader.getSystemResource("dead.png"));
    }
    catch (IOException e) {
      throw new IllegalStateException("Read Failed");
    }

    m = new MouseClickAdapterImpl(controller);

    dungeon.setSize(400,400);
    this.setViewportView(dungeon);
    this.setLocation(200,200);
    this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
  }

  /**
   * Initialises the dungeon grid with given number of rows and columns. Cascaded down from the
   * main game panel.
   *
   * @param rows represents the number of rows in the dungeon.
   * @param columns represents the number of columns in the dungeon.
   */
  public void initializeDungeon(int rows, int columns) {
    jLabels = new JLabel[rows][columns];
    location = "";
    this.dungeon.removeAll();
    this.dungeon.revalidate();
    this.dungeon.repaint();
    this.revalidate();
    this.repaint();

    dungeon.setLayout(new GridLayout(rows,columns));

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        jLabels[i][j] = new JLabel();
        jLabels[i][j].setName("Grid Location");
        jLabels[i][j].setIcon(new ImageIcon(
            new ImageIcon(ClassLoader.getSystemResource("ES.png"))
                .getImage().getScaledInstance(130, 130, BufferedImage.TYPE_INT_ARGB)));
        jLabels[i][j].setVisible(!jLabels[i][j].isVisible());
        dungeon.add(jLabels[i][j]);
      }
    }
  }

  /**
   * Draws the player's current location. It also returns the same image representing the players
   * current location to further set in the player location panel.
   *
   * @param readOnlyGame represents the read-only version of the model.
   * @returns the image representing the players current location.
   */
  public BufferedImage dungeonNodeAtPlayersLocation(ReadOnlyGame readOnlyGame) {

    BufferedImage player;
    BufferedImage currentLocation;

    if (!location.equals("")) {
      jLabels[row][column]
          .setIcon(new ImageIcon(new ImageIcon(ClassLoader.getSystemResource(location))
              .getImage().getScaledInstance(130, 130, BufferedImage.TYPE_INT_ARGB)));
      jLabels[row][column].removeMouseListener(m);
    }

    try {
      player = ImageIO.read(ClassLoader.getSystemResource("player.png"));
      currentLocation = ImageIO.read(
          ClassLoader.getSystemResource("" + readOnlyGame.nodeT() + ".png"));
    }
    catch (IOException e) {
      throw new IllegalStateException("Read Failed");
    }

    BufferedImage newImg = new BufferedImage(128 * 2, 128 * 2, BufferedImage.TYPE_INT_ARGB);
    BufferedImage smell1 = makeImageTranslucent(stench1, 0.35);
    BufferedImage smell2 = makeImageTranslucent(stench2, 0.35);
    BufferedImage crackling = makeImageTranslucent(sound, 0.35);


    Graphics2D g = newImg.createGraphics();
    configureGraphic(g);

    g.drawImage(currentLocation, 0, 0, null);
    if (readOnlyGame.getRubyCount() != 0) {
      g.drawImage(rubies, 19, 31, 15,15, null);
      g.drawString(" x" + readOnlyGame.getRubyCount(), 31,45);
    }

    if (readOnlyGame.getDiamondCount() != 0) {
      g.drawImage(diamonds, 19, 85, 15,15, null);
      g.drawString(" x" + readOnlyGame.getDiamondCount(), 31,97);
    }

    if (readOnlyGame.getSapphireCount() != 0) {
      g.drawImage(sapphires, 80, 31, 15,15, null);
      g.drawString(" x" + readOnlyGame.getSapphireCount(), 92,45);
    }


    if (readOnlyGame.nodeT().equals("WN")) {
      g.drawImage(player, 32, 35, 24,24,null);
      if (readOnlyGame.getArrowCount() != 0) {
        g.drawImage(arrows, 47, 16, 20,27, null);
        g.drawString(" x" + readOnlyGame.getArrowCount(), 53,20);
      }
      if (readOnlyGame.getThief() != null) {
        g.drawImage(thief, 18, 48, 15,15, null);
      }
    }
    else if (readOnlyGame.nodeT().equals("NE")) {
      g.drawImage(player, 70, 35, 24,24,null);
      if (readOnlyGame.getArrowCount() != 0) {
        g.drawImage(arrows, 63, 16, 20,27, null);
        g.drawString(" x" + readOnlyGame.getArrowCount(), 60,20);
      }
      if (readOnlyGame.getThief() != null) {
        g.drawImage(thief, 95, 51, 15,15, null);
      }
    } else if (readOnlyGame.nodeT().equals("SW")) {
      g.drawImage(player, 35, 72, 24,24,null);
      if (readOnlyGame.getArrowCount() != 0) {
        g.drawImage(arrows, 54, 93, 20,27, null);
        g.drawString(" x" + readOnlyGame.getArrowCount(), 53,100);
      }
      if (readOnlyGame.getThief() != null) {
        g.drawImage(thief, 22, 60, 15,15, null);
      }
    } else if (readOnlyGame.nodeT().equals("ES")) {
      g.drawImage(player, 63, 75, 24,24,null);
      if (readOnlyGame.getArrowCount() != 0) {
        g.drawImage(arrows, 82, 59, 20,27, null);
        g.drawString(" x" + readOnlyGame.getArrowCount(), 95,68);
      }
      if (readOnlyGame.getThief() != null) {
        g.drawImage(thief, 57, 92, 15,15, null);
      }
    } else if (readOnlyGame.nodeT().equals("EW")) {
      g.drawImage(player, 52, 53, 24,24,null);
      if (readOnlyGame.getArrowCount() != 0) {
        g.drawImage(arrows, 84, 58, 20,27, null);
        g.drawString(" x" + readOnlyGame.getArrowCount(), 83,65);
      }
      if (readOnlyGame.getThief() != null) {
        g.drawImage(thief, 33, 60, 15,15, null);
      }
    } else if (readOnlyGame.nodeT().equals("NS")) {
      g.drawImage(player, 52, 55, 24,24,null);
      if (readOnlyGame.getArrowCount() != 0) {
        g.drawImage(arrows, 57, 85, 20,27, null);
        g.drawString(" x" + readOnlyGame.getArrowCount(), 55,90);
      }
      if (readOnlyGame.getThief() != null) {
        g.drawImage(thief, 57, 25, 15,15, null);
      }
    } else {
      g.drawImage(player, 52, 55, 24,24,null);
      if (readOnlyGame.getArrowCount() != 0) {
        g.drawImage(arrows, 78, 85, 30,30, null);
        g.drawString(" x" + readOnlyGame.getArrowCount(), 82,90);
      }
      if (readOnlyGame.getMonster() != null) {
        g.drawImage(monster, 52, 16, 23,23, null);
      }
      if (readOnlyGame.getMonsterHealth() == 0) {
        g.drawImage(monster, 52, 16, 23,23, null);
        g.drawImage(dead, 52, 16, 23,23, null);

      }
      if (readOnlyGame.getPit() != null) {
        g.drawImage(pit, 52, 95, 23,23, null);
      }
    }


    if (readOnlyGame.getSmell().equals("PUNGENT")) {
      if (readOnlyGame.nodeT().equals("WN")) {
        g.drawImage(smell1, 26, 30, 70, 70, null);
      } else if (readOnlyGame.nodeT().equals("NE")) {
        g.drawImage(smell1, 61, 30, 70, 70, null);
      } else if (readOnlyGame.nodeT().equals("SW")) {
        g.drawImage(smell1, 28, 64, 70, 70, null);
      } else if (readOnlyGame.nodeT().equals("ES")) {
        g.drawImage(smell1, 58, 64, 70, 70, null);
      } else {
        g.drawImage(smell1, 37, 40, 110, 110, null);
      }
    }
    if (readOnlyGame.getSmell().equals("MOREPUNGENT")) {
      if (readOnlyGame.nodeT().equals("WN")) {
        g.drawImage(smell2, 26, 30, 70, 70, null);
      } else if (readOnlyGame.nodeT().equals("NE")) {
        g.drawImage(smell2, 61, 30, 70, 70, null);
      } else if (readOnlyGame.nodeT().equals("SW")) {
        g.drawImage(smell2, 28, 64, 70, 70, null);
      } else if (readOnlyGame.nodeT().equals("ES")) {
        g.drawImage(smell2, 58, 64, 70, 70, null);
      } else {
        g.drawImage(smell2, 37, 40, 110, 110, null);
      }
    }

    if (readOnlyGame.getSound().equals("CRACKLING")) {
      if (readOnlyGame.nodeT().equals("WN")) {
        g.drawImage(crackling, 29, 30, 100, 100, null);
      } else if (readOnlyGame.nodeT().equals("NE")) {
        g.drawImage(crackling, 64, 30, 100, 100, null);
      } else if (readOnlyGame.nodeT().equals("SW")) {
        g.drawImage(crackling, 31, 64, 100, 100, null);
      } else if (readOnlyGame.nodeT().equals("ES")) {
        g.drawImage(crackling, 61, 64, 100, 100, null);
      } else {
        g.drawImage(crackling, 45, 45, 100, 100, null);
      }
    }

    g.dispose();

    location = "" + readOnlyGame.nodeT() + ".png";
    row = readOnlyGame.getRowIn2D();
    column = readOnlyGame.getColIn2D();
    jLabels[readOnlyGame.getRowIn2D()][readOnlyGame.getColIn2D()]
        .setIcon(new ImageIcon(new ImageIcon(
            newImg).getImage().getScaledInstance(130, 130, BufferedImage.TYPE_INT_ARGB)));
    jLabels[readOnlyGame.getRowIn2D()][readOnlyGame.getColIn2D()].setVisible(true);

    jLabels[readOnlyGame.getRowIn2D()][readOnlyGame.getColIn2D()].addMouseListener(m);

    this.getVerticalScrollBar().setValue((readOnlyGame.getRowIn2D()) * 100);
    this.getHorizontalScrollBar().setValue((readOnlyGame.getColIn2D()) * 100);

    return newImg;
  }

  /**
   * Private helper method to set the rendering and antialiasing for the graohics.
   *
   * @param g represents the instance of the graphics.
   */
  private void configureGraphic(Graphics2D g) {
    g.scale(2, 2);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
        RenderingHints.VALUE_COLOR_RENDER_QUALITY);

    g.setColor(Color.BLACK);
    g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
  }

  /**
   * Private helper method to make the given image translucent.
   *
   * @param source represents the source image.
   * @param alpha represents the alpha for the conversion.
   * @returns the converted image as target.
   */
  private BufferedImage makeImageTranslucent(BufferedImage source,
      double alpha) {
    BufferedImage target = new BufferedImage(128 * 2,
        128 * 2, java.awt.Transparency.TRANSLUCENT);
    Graphics2D g = target.createGraphics();
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
        (float) alpha));
    g.drawImage(source, null, 0, 0);
    g.dispose();
    return target;
  }
}
