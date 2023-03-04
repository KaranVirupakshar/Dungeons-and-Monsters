package model.dungeonsanddragons;

import java.util.Objects;

/**
 * InterconnectionImpl class represents the edge or a connection between 2 nodes. Source and
 * Destination can either be a cave or a tunnel. Edges provide means for the player to traverse
 * in the dungeon grid through a series of moves. Since the InterconnectionImpl is used in a
 * package-private Dungeon implementation, this class is also kept as package-private.
 */
class InterconnectionImpl implements Interconnection {
  private  final DungeonNode source;
  private final DungeonNode destination;

  /**
   * A constructor to initialize the interconnection that represents the path in the dungeon.
   *
   * @param source represents the source node.
   * @param destination represents the destination node.
   * @throws IllegalArgumentException if source or destination is empty.
   */
  public InterconnectionImpl(DungeonNode source, DungeonNode destination) {

    if (Objects.isNull(source) || Objects.isNull(destination)) {
      throw new IllegalArgumentException("Illegal Parameters");
    }

    this.source = source;
    this.destination = destination;
  }

  /**
   * Gets the source node of the interconnection pair.
   *
   * @return the source node of the interconnection pair.
   */
  @Override
  public DungeonNode getSource() {
    return source;
  }

  /**
   * Gets the destination node of the interconnection pair.
   *
   * @return the destination node of the interconnection pair.
   */
  @Override
  public DungeonNode getDestination() {
    return destination;
  }
}