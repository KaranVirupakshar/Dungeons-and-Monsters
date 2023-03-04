package model.dungeonsanddragons;

/**
 * Enemy enumeration represents an adversary that the player can face in the dungeon. Thieves
 * can exist only in the tunnel and will rob all of player's treasure when he comes across the
 * player. The player can escape the thief if he can get away from the tunnel in 5 seconds. If the
 * player doesn't escape the tunnel in under 5 seconds, then he loses all of the treasure. Since an
 * enemy is part of the internal implementation of the dungeon, Obstacle is kept as package-
 * private.
 */
enum Enemy {
  THIEF
}
