package view;

import java.awt.Color;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * PlayerActionDisplay class represents the message that provides a clear indication of the
 * results of each action a player takes. This is a fading message that remains on the screen
 * temporarily. Source: https://stackoverflow.com/questions/10161149/android-like-toast-in-swing
 * Since the PlayerActionDisplay is a component of the main game panel, this class is kept as
 * package-private.
 */
class PlayerActionDisplay extends JDialog {

  private static Boolean display = false;

  /**
   * A constructor to initialize the display message.
   *
   * @param caller represents the component on which the message needs to be displayed.
   * @param toastString represents the message that needs to be displayed.
   */
  public PlayerActionDisplay(JComponent caller, String toastString) {
    this(caller, toastString, 500);
  }

  /**
   * A constructor to initialize the display message.
   *
   * @param caller represents the component on which the message needs to be displayed.
   * @param toastString represents the message that needs to be displayed.
   * @param delay represents how long the message needs to be displayed.
   */
  public PlayerActionDisplay(JComponent caller, String toastString, int delay) {
    if (display) {
      return;
    }
    JPanel displayPanel = new JPanel();
    displayPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    displayPanel.setBackground(Color.WHITE);
    JLabel toastLabel = new JLabel(toastString);
    toastLabel.setForeground(Color.BLACK);
    displayPanel.add(toastLabel);
    add(displayPanel);

    setFocusableWindowState(false);
    setUndecorated(true);
    setAlwaysOnTop(true);

    pack();

    setLocation(550, 800);
    setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
    setVisible(true);

    new Thread(() -> {
      try {
        display = true;
        Thread.sleep(delay);
        dispose();
        display = false;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
  }
}