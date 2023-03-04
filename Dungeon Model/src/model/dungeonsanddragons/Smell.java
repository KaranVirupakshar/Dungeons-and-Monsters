package model.dungeonsanddragons;

/**
 * Smell enumeration represents the two types of smells that the player can recognize to identify
 * near-by Otyughs. PUNGENT smell can be detected when there is a single Otyugh 2 positions from
 * the player's current location. MOREPUNGENT smell either means that there is a single Otyugh 1
 * position from the player's current location or that there are multiple Otyughs within 2
 * positions from the player's current location. Since the user uses the smell state to traverse
 * through the dungeon, this enumeration is kept as public.
 */
public enum Smell {
  PUNGENT,
  MOREPUNGENT
}
