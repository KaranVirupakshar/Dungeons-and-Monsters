package model.dungeonsanddragons;

/**
 * Monster interface represents a framework for creation of different beasts that can exist in
 * the dungeon. Beasts can kill a player when they come in contact so it's important for the player
 * to slay the monster before entering their location. Since the classes using this framework
 * exists in the package, this class is made as package-private.
 */
interface Monster {

  /**
   * Adjust the health of the monster to 50% after the first Hit.
   */
  void firstHit();

  /**
   * Adjust the health of the monster to 0% after the second Hit.
   */
  void getKilled();

  /**
   * Gets the health of the monster. A monster starts with 100% health.
   *
   * @return the health of the monster. A monster starts with 100% health.
   */
  int getHealth();
}
