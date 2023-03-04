package model.dungeonsanddragons;

/**
 * DungeonNodeType enumeration contains the possible types that the DungeonNodeImpl represents. A
 * tunnel is a node with 2 connections whereas a cave is a node with 1,2, or 4 connections. Since
 * type of location is internal to the Dungeon Node implementation this is kept as package-private.
 */
enum DungeonNodeType {
  CAVE,
  TUNNEL
}
