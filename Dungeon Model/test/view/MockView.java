package view;

import controller.GameSwingController;
import model.dungeonsanddragons.ReadOnlyGame;

import java.util.List;

/**
 * GameViewImplTest represents a mock view used for testing purpose.
 */
public class MockView implements GameView {

  private List<String> log;

  /**
   * Constructor to initialize mock view.
   *
   * @param log the log for testing
   */
  public MockView(List<String> log) {
    this.log = log;
  }


  @Override
  public void setFeatures(GameSwingController controller) {
    log.add("called setFeatures");

  }

  @Override
  public void resetFocus() {
    log.add("called resetFocus");
  }

  @Override
  public void generateDungeonPanel(int rows, int columns, ReadOnlyGame readOnlyGame) {
    log.add("called generateDungeonPanel");

  }

  @Override
  public void updateDungeonPanel(ReadOnlyGame readOnlyGame) {
    log.add("called updateDungeonPanel");
  }

  @Override
  public void displayPlayerAction(String message) {
    log.add("called displayPlayerAction");

  }

  @Override
  public void displayPlayerAction(String message, int delay) {
    log.add("called displayPlayerAction with delay");
  }

  @Override
  public void gameOver(GameSwingController controller, ReadOnlyGame readOnlyGame, String title) {
    log.add("called gameOver");
  }
}