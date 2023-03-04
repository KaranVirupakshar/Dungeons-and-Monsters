package view;

import controller.GameSwingController;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * PlayerActionPanel represents a class that extends JPanel and provides the user with capability
 * to move, pick and shoot. This is done by listening to mouse clicks from the player and tagging
 * actions to it. Since the PlayerActionPanel is a component of the main game panel, this class is
 * kept as package-private.
 */
class PlayerActionPanel extends JPanel implements ActionPanel {

  private final JComboBox directions;
  private final JTextField distance;
  private final JButton shoot;
  private final JLabel up;
  private final JLabel down;
  private final JLabel left;
  private final JLabel right;
  private final JLabel ruby;
  private final JLabel diamond;
  private final JLabel sapphire;
  private final JLabel arrow;

  /**
   * A constructor to initialize the labels, and buttons that control the player actions.
   */
  public PlayerActionPanel() {

    directions = new JComboBox<>();
    distance = new JTextField();
    shoot = new JButton();
    this.setBackground(Color.WHITE);
    this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    distance.setBorder(BorderFactory.createLineBorder(Color.black));
    shoot.setText("Shoot");
    directions.setModel(new DefaultComboBoxModel<>(new String[]{"Up", "Down", "Left", "Right"}));
    up = new JLabel("Up");
    up.setName("Up");
    up.setIcon(new ImageIcon(new ImageIcon(
        ClassLoader.getSystemResource("up.png"))
        .getImage().getScaledInstance(60, 60, BufferedImage.TYPE_INT_ARGB)));

    down = new JLabel("Down");
    down.setName("Down");
    down.setIcon(new ImageIcon(new ImageIcon(
        ClassLoader.getSystemResource("down.png"))
        .getImage().getScaledInstance(60, 60, BufferedImage.TYPE_INT_ARGB)));

    left = new JLabel("Left");
    left.setName("Left");
    left.setIcon(new ImageIcon(new ImageIcon(
        ClassLoader.getSystemResource("left.png"))
        .getImage().getScaledInstance(60, 60, BufferedImage.TYPE_INT_ARGB)));

    right = new JLabel("Right");
    right.setName("Right");
    right.setIcon(new ImageIcon(new ImageIcon(
        ClassLoader.getSystemResource("right.png"))
        .getImage().getScaledInstance(60, 60, BufferedImage.TYPE_INT_ARGB)));

    ruby = new JLabel("Ruby");
    ruby.setName("Ruby");
    ruby.setIcon(new ImageIcon(new ImageIcon(
        ClassLoader.getSystemResource("ruby.png"))
        .getImage().getScaledInstance(40, 40, BufferedImage.TYPE_INT_ARGB)));

    diamond = new JLabel("Diamond");
    diamond.setName("Diamond");
    diamond.setIcon(new ImageIcon(new ImageIcon(
        ClassLoader.getSystemResource("diamond.png"))
        .getImage().getScaledInstance(40, 40, BufferedImage.TYPE_INT_ARGB)));

    sapphire = new JLabel("Sapphire");
    sapphire.setName("Sapphire");
    sapphire.setIcon(new ImageIcon(new ImageIcon(
        ClassLoader.getSystemResource("emerald.png"))
        .getImage().getScaledInstance(40, 40, BufferedImage.TYPE_INT_ARGB)));

    arrow = new JLabel("Arrow");
    arrow.setName("Arrow");
    arrow.setIcon(new ImageIcon(new ImageIcon(
        ClassLoader.getSystemResource("arrow.png"))
        .getImage().getScaledInstance(70, 80, BufferedImage.TYPE_INT_ARGB)));

    directions.setSelectedItem("Up");

    this.setLayout(null);
    this.add(up).setBounds(111,18,60,60);
    this.add(down).setBounds(111,133,60,60);
    this.add(left).setBounds(52,73,60,60);
    this.add(right).setBounds(170,73,60,60);
    this.add(ruby).setBounds(12,221,40,40);
    this.add(diamond).setBounds(74,221,40,40);
    this.add(sapphire).setBounds(139,221,40,40);
    this.add(arrow).setBounds(205,221,70,40);
    this.add(directions).setBounds(7,287,100,35);
    this.add(distance).setBounds(115,288,64,24);
    this.add(shoot).setBounds(200,287,80,30);
  }

  /**
   * Configures the mouse click events for player actions panel. This allows the player
   * to make moves in north, south, east or west. It also allows for player to pick up
   * sapphires, diamonds, rubies, arrows. It also allows for the player to shoot the arrow
   * in a particular direction at a particular distance.
   *
   * @param controller represents the controller for the view.
   * @param view represents the GUI representation of the game.
   */
  @Override
  public void configurePlayerActions(GameSwingController controller, GameView view) {

    up.addMouseListener(new MouseClickAdapterImpl(controller));

    down.addMouseListener(new MouseClickAdapterImpl(controller));

    left.addMouseListener(new MouseClickAdapterImpl(controller));

    right.addMouseListener(new MouseClickAdapterImpl(controller));

    ruby.addMouseListener(new MouseClickAdapterImpl(controller));

    diamond.addMouseListener(new MouseClickAdapterImpl(controller));

    sapphire.addMouseListener(new MouseClickAdapterImpl(controller));

    arrow.addMouseListener(new MouseClickAdapterImpl(controller));

    shoot.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        try {
          controller.shootArrow(directions.getSelectedItem().toString(),
              Integer.parseInt(distance.getText()));
        }
        catch (NumberFormatException a) {
          view.displayPlayerAction("Distance should be a number");
        }
      }
    });
  }

  /**
   * Override the paintComponent method of the JPanel to draw component headings.
   *
   * @param g represents an instance of Graphics
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    g2d.drawLine(0, 200, 290, 200);
    g2d.drawLine(0, 266, 290, 266);
    g2d.drawString("Use Arrow", 108, 282);
    g2d.drawString("Pick Items", 106, 215);
    g2d.drawString("Move Player", 105, 14);
  }

}
