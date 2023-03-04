package model.dungeonsanddragons;

/**
 * Obstacle enumeration represents the barrier in the dungeon that the player has to overcome.
 * Player can recognize the pit when they are one location away from it. Player will hear a
 * crackling sound indicating that the pit is one location away. If the player does not have one
 * treasure of each type then the player falls in the pit and dies. If the player does have a
 * treasure of each type, then the player survives. Pits can exist only in the caves. Since an
 * obstacle is part of the internal implementation of the dungeon, Obstacle is kept as package-
 * private.
 */
enum Obstacle {
  PIT
}
