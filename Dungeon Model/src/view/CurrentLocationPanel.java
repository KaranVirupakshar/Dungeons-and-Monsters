package view;

import java.awt.image.BufferedImage;

/**
 * CurrentLocationPanel interface represents the framework for displaying zoomed in view of the
 * player's current location. Player can view aswell as move or pick items from their current
 * location. Since the classes using this framework exists in the package, this class is made as
 * package-private.
 **/
interface CurrentLocationPanel {

  /**
   * Updates the current location of the player in the view when a move is made.
   *
   * @param image represents the image of current location.
   */
  void updateCurrentLocation(BufferedImage image);

}
