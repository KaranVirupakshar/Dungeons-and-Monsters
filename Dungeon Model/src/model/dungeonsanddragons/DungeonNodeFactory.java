package model.dungeonsanddragons;

/**
 * DungeonNodeFactory class represents the factory for creation of dungeons. This object factory
 * can create caves or tunnels representing the dungeon nodes. Since this factory is only required
 * by the DungeonImpl class which is already package-private, this class is kept as
 * package-private.
 */
class DungeonNodeFactory {

  /**
   * Creates a dungeon node with an unique identifier.
   *
   * @param dungeonIdentifier represents the unique identifier of the dungeon.
   * @return the newly created Dungeon node object.
   * @throws IllegalArgumentException if identifier is less than 0.
   */
  public DungeonNodeImpl createDungeonNode(int dungeonIdentifier) throws IllegalArgumentException {

    if (dungeonIdentifier < 0) {
      throw new IllegalArgumentException("Illegal Identifier!");
    }
    return new DungeonNodeImpl(dungeonIdentifier);
  }
}
