package view;

import model.dungeonsanddragons.ReadOnlyGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * PlayerDescriptionPanel class represents a class that extends JPanel and provides the user with
 * player description. This gets updated when the model gets updated. Since the
 * PlayerDescriptionPanel is a component of the main game panel, this class is kept as
 * package-private.
 */
class PlayerDescriptionPanel extends JPanel implements DescriptionPanel {

  private final JLabel rubyCount;
  private final JLabel diamondCount;
  private final JLabel sapphireCount;
  private final JLabel arrowCount;

  /**
   * A constructor to initialize the labels that display the player's description.
   */
  public PlayerDescriptionPanel() {

    this.setLayout(null);
    this.setBackground(Color.WHITE);

    rubyCount = new JLabel();
    diamondCount = new JLabel();
    sapphireCount = new JLabel();
    arrowCount = new JLabel();
    rubyCount.setFont(new Font("Serif", Font.PLAIN, 22));
    diamondCount.setFont(new Font("Serif", Font.PLAIN, 22));
    sapphireCount.setFont(new Font("Serif", Font.PLAIN, 22));
    arrowCount.setFont(new Font("Serif", Font.PLAIN, 22));

    JLabel player = new JLabel(new ImageIcon(new ImageIcon(
        ClassLoader.getSystemResource("player.png"))
        .getImage().getScaledInstance(52, 50, BufferedImage.TYPE_INT_ARGB)));
    JLabel ruby = new JLabel(new ImageIcon(new ImageIcon(
        ClassLoader.getSystemResource("ruby.png"))
        .getImage().getScaledInstance(40, 40, BufferedImage.TYPE_INT_ARGB)));
    JLabel diamond = new JLabel(new ImageIcon(new ImageIcon(
        ClassLoader.getSystemResource("diamond.png"))
        .getImage().getScaledInstance(40, 40, BufferedImage.TYPE_INT_ARGB)));
    JLabel sapphire = new JLabel(new ImageIcon(new ImageIcon(
        ClassLoader.getSystemResource("emerald.png"))
        .getImage().getScaledInstance(40, 40, BufferedImage.TYPE_INT_ARGB)));
    JLabel arrow = new JLabel(new ImageIcon(new ImageIcon(
        ClassLoader.getSystemResource("arrow.png"))
        .getImage().getScaledInstance(70, 90, BufferedImage.TYPE_INT_ARGB)));

    this.add(player).setBounds(3,0,52,50);
    this.add(ruby).setBounds(69,6,40,40);
    this.add(diamond).setBounds(165,6,40,40);
    this.add(sapphire).setBounds(265,6,40,40);
    this.add(arrow).setBounds(375,8,60,36);
    this.add(rubyCount).setBounds(104,6,50,50);
    this.add(diamondCount).setBounds(200,6,50,50);
    this.add(sapphireCount).setBounds(300,6,50,50);
    this.add(arrowCount).setBounds(432,6,50,50);
  }

  /**
   * Initialize the player description at the start of the game.
   *
   * @param readOnlyGame represents a read-only model of the game.
   */
  @Override
  public void initializePlayerDescription(ReadOnlyGame readOnlyGame) {
    rubyCount.setText(" X " + readOnlyGame.getPlayerRubyCount());
    diamondCount.setText(" X " + readOnlyGame.getPlayerDiamondCount());
    sapphireCount.setText(" X " + readOnlyGame.getPlayerSapphireCount());
    arrowCount.setText(" X " + readOnlyGame.getPlayerArrowCount());
  }

  /**
   * Update the player description as and when the description gets updated.
   *
   * @param readOnlyGame represents a read-only model of the game.
   */
  @Override
  public void updatePlayerDescription(ReadOnlyGame readOnlyGame) {
    rubyCount.setText(" X " + readOnlyGame.getPlayerRubyCount());
    diamondCount.setText(" X " + readOnlyGame.getPlayerDiamondCount());
    sapphireCount.setText(" X " + readOnlyGame.getPlayerSapphireCount());
    arrowCount.setText(" X " + readOnlyGame.getPlayerArrowCount());
  }

  /**
   * Override the paintComponent method of the JPanel to draw a separator line.
   *
   * @param g represents an instance of Graphics
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    g2d.drawLine(60, 0, 60, 50);
  }
}
