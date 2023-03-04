package model.dungeonsanddragons;

/**
 * Treasure enumerations represents the types of Treasure that can be placed in a cave. A player
 * can choose to pick the treasure that is available in their location. Since the user needs to
 * know the type of treasures present in the current location and the list of treasures that the
 * player has collected, this will be used directly by the user. Hence, this enumeration is kept
 * as public.
 */
public enum Treasure {
  DIAMOND,
  RUBY,
  SAPPHIRE,
}
