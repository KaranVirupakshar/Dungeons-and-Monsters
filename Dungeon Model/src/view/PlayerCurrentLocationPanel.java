package view;

import controller.GameSwingController;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * PlayerCurrentLocationPanel class represents a class that extends JPanel and provides the user
 * with a zoomed in view of the player's current location. Player can use it to view, move or pick
 * items from the current location. Since the PlayerCurrentLocationPanel is a component of the main
 * game panel, this class is kept as package-private.
 */
class PlayerCurrentLocationPanel extends JPanel implements CurrentLocationPanel {

  private final JLabel currentLocation;

  /**
   * A constructor to initialize the Label with the image of the current location.
   *
   * @param controller represents the controller of the GUI game.
   */
  public PlayerCurrentLocationPanel(GameSwingController controller) {
    this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    currentLocation = new JLabel();
    this.setName("Current Location");
    this.setBackground(Color.BLACK);
    this.add(currentLocation);
    this.addMouseListener(new MouseClickAdapterImpl(controller));
  }

  /**
   * Updates the current location of the player in the view when a move is made.
   *
   * @param image represents the image of current location.
   */
  @Override
  public void updateCurrentLocation(BufferedImage image) {
    currentLocation.setIcon(new ImageIcon(new ImageIcon(image)
            .getImage().getScaledInstance(290,290, BufferedImage.TYPE_INT_ARGB)));
    repaint();
    revalidate();
  }
}
