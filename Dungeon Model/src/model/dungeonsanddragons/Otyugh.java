package model.dungeonsanddragons;

/**
 * Otyugh class represents extremely smelly creatures that lead solitary lives in the deep, dark
 * places of the world like our dungeon. There is always at least one Otyugh in the dungeon located
 * at the specially designated end cave. They can be detected by their smell. Since decision was
 * made to create the monster internally without any inputs from the user, Otyugh is kept as
 * package-private.
 */
class Otyugh implements Monster {
  private int monsterHealth;

  /**
   * A constructor to initialize the health of the monster to 100% after its creation.
   */
  public Otyugh() {
    this.monsterHealth = 100;
  }

  /**
   * Adjust the health of the monster to 50% after the first Hit.
   */
  @Override
  public void firstHit() {
    this.monsterHealth -= this.monsterHealth - 50;
  }

  /**
   * Adjust the health of the monster to 0% after the second Hit.
   */
  @Override
  public void getKilled() {
    this.monsterHealth = 0;
  }

  /**
   * Gets the health of the monster. A monster starts with 100% health.
   *
   * @return the health of the monster. A monster starts with 100% health.
   */
  @Override
  public int getHealth() {
    return monsterHealth;
  }

  /**
   * String representation of the Otyugh.
   *
   * @return the string representation of the Otyugh.
   */
  @Override
  public String toString() {
    return "OTYUGH";
  }
}
