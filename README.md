# Dungeons-and-Monsters

## Overview
In an adventure game, the player takes on the role of the protagonist in an interactive story driven by exploration and/or puzzle solving. In a text-based game, the way the game is played is through printable text data, usually through a console. The game can also be played using an interactive graphical user interface (GUI).

This project represents such a fantasy-based dungeon game, built with a model component containing the logic for the game and a controller component to handle the inputs of the user either via a console or a Graphical User Interface(GUI). Primary components of the game include creation of wrapping or a non-wrapping dungeon represented as a 2d grid of caves and tunnels. Each location in the dungeon is connected in such a way that the player can traverse all the nodes. The structure allows for the treasure, monster, and pits allocation in the caves, arrows, and thieves allocation in the tunnels and caves and provision for a player to pick up the treasure, arrows as they traverse along the different locations of the dungeon. The system also allows for the player to win the game by slaying the monsters, dodging the pits and reaching the end tunnel, else the player loses.


## Features

- Ability to create both wrapping and non-wrapping dungeons with different degrees of interconnectivity.
- Provides support for three types of treasure: diamonds, rubies, and sapphires.
- Treasure can be added to a specified percentage of caves. For example, the client can ask the model to add treasure to 20% of the caves and the model should add a random treasure to at least 20% of the caves in the dungeon. 
- Ability for a cave to provision more than one treasure.
- Ability to pick a starting and ending cave with a distance of atleast 5. Player always begins in the starting cave.
- Ability for a player to move from their current location and pick up treasure and arrows that is located in their same location.
- Ability to provide a description of the player that, that includes a description of what treasure the player has collected and the number of arrows remaining in the bag.
- Provides support to give a description of the player's location that includes a description of treasure and arrows in the room and the possible moves (up, down, left, and right) that the player can make from their current location.
- Provides support to increase the difficulty of the game by adding monsters to the game. Player can detect two levels of smell based on the proximity to the monster.
- Ability for a player to slay the monsters using the crooked arrows that are present in the dungeon.
- Game is completed when the player reaches the designated end cave without getting eaten by the monster.
- If the player enters the cave with an injured Otyugh that has been hit by a single crooked arrow, they have 50% chance of escaping.
- Provides support to further increase the difficulty of the game by adding pits to the game. Player can detect the sound based on the proximity to the pits.
- Player can escape the pit without falling if the player has one item of each treasure.
- Provides support to add thieves to the game who has the ability to steal items from the player.
- Player can escape from the thief if he gets away within 5 seconds from the location.

## How to Run

- Game can be played either via a console or a Graphical User Interface (GUI)
- To play the game via a Graphical User Interface (GUI):
    -   All the images are bundled along with the JAR.
    -   Download the JAR file which is present in the res/ folder and run it using **java -jar project05.jar** to execute the driver for this project.
    -   Graphical User Interface (GUI) starts off with a pre-configured 'interesting' dungeon. Player can continue with the game or change the settings using the 'Settings' menu.
    -   Once the settings are changed, select 'Game -> New Game' from the menu for the changes to take effect and new game to begin.
    -   Following are the controls for the game:
        -    Arrow keys - Move Player
        -    S - Pick Sapphire
        -    D - Pick Diamond
        -    R - Pick Ruby
        -    A - Pick Arrow
        -    Space -> Arrow key -> Number between 0-9 - Shoot Arrow
    - Additionally player can use Action Panel or clicks on the player's location to perform same actions.
- To play the game via a console:
    - Download the JAR file which is present in the res/ folder and run it using  **java -jar project05.jar <rows> <columns> <wrapping> <interconnectivity> <treasure> <monsters>** to execute the driver for this project. rows, columns, wrapping status, interconnectivity degree, treasure percentage, number of monsters are the arguments that needs to be passed on to the program.
    - Example command: **java --jar project05.jar 5 5 false 9 50 5**
    - Controller provides following options/features for the text-based adventure game
        - Move Player (UP, DOWN, LEFT, RIGHT)
        - Pick Items (ARROW, DIAMOND, RUBY, SAPPHIRE)
        - Shoot Arrow (Distance and Direction as input)
        - Game state which includes the smell, treasure and arrow is displayed at every move.
        - Description of the player which includes the treasure collected and arrows left to use is displayed at every move.


## How to Use the Program

 - game (row, column, wrapping, interconnectivity, treasure, monsters) - A constructor that creates a dungeon with given rows and columns. Dungeon is represented as a 2d grid of given rows and columns. It also takes in a degree of interconnectivity, wrapping status, the percentage of caves/tunnels that the treasure and arrows needs to be added to along with allocation of monsters
 - movePlayer (Move) - Move the player from the current location using the given move. UP, DOWN, RIGHT, LEFT are the possible moves for the player.
 - isReachedEnd () - Checks if the player has reached the end cave. This is required to determine if the game is over.
 - getPlayerCurrentLocation () - Gets the current location of the player. Location can be a cave or a tunnel.
 - pickTreasure (Treasure) - Pick the specified treasure if it is available in the current node.
 - getTreasureCollectedByPlayer () - Gets the list of treasures collected by the player.
 - printDungeonAs2D () - Gets the 2D representation of the dungeon. Rows and columns of the 2d grid represent an individual location which can be either a cave or a tunnel. Edges represent the connection with the adjacent nodes. Lines(| or —) represent the path the player can take.
 - getSmell() - Gets the type of smell that the player is currently smelling from their location. Smell is given out by the monsters. Player can use it to their advantage to survive and traverse through the dungeon.
 - getArrowsInPlayerBag() - Gets the arrows currently in the player bag.
 - pickArrow() - Picks an arrow and adds it to the player's bag so that it can be further used to slay a monster.
 - shootArrow(distance, direction) - Shoots the arrow in the specified direction and distance.
 - isKilled() - Checks if the player has been killed by the monster. This is required to determine if the game is over.
 - checkTreasureStolen() - Checks if the thief has stolen the treasure. The player can escape the thief if he can get away from the tunnel in 5 seconds. If the player doesn't escape the tunnel in under 5 seconds, then he loses all of the treasure.
 - isFallen() - Checks if the player has fallen into the pit. If the player does not have one treasure of each type then the player falls in the pit and dies.
 - getSound() - Gets the type of sound that the player can hear to identify near-by pit. Crackling sound can be heard by the player when the player is one location away from the pit. Since the user uses hearing to traverse through the dungeon, this enumeration is kept as public.

## Description of Examples
Diagram.jpg in the /res folder contains the screenshots of the Graphical User Interface(GUI) running. Following features are represented in the screenshots.
- Player navigating through the dungeon with key presses is illustrated.
- Player navigating through the dungeon with mouse clicks is illustrated.
- Player picking up different pieces of treasure items individually, one at a time is illustrated.
- Player picking up arrows individually, one at a time is illustrated.
- Player getting killed by an Otyugh and losing the game is illustrated.
- Player falling into the pit and losing the game is illustrated.
- Player coming across a thief and losing all the collected items, both treasure and arrows is illustrated.
- Scrollable dungeon being created which requires the player to use scroll bars to be able to see the entire dungeon is illustrated.
- Player accessing different menu items to change the settings of the game is illustrated.
- Player accessing different menu items to start a new game or reset the existing game is illustrated.
- Player description getting updated after every player action is illustrated.
- Description of each location is being displayed for the player is illustrated.
- Player winning the game by reaching the end cave is illustrated.

## Design/Model Changes
- Design of the mouse click listener and the keyboard listener was changed. In the initial design, anonymous classes were used but was modified to make a seperate classes extending Mouse Adapter and Keyboard Adapter to capture all the events of the game frame.
- Design of the player description and action was changed, In the initial design, implementation was in the main game frame, but was modified to move the implementations to sub panels.

## Assumptions

- Monster can only exist in caves and each cave can house only a single monster. Maximum number of monsters allowed is 20.
- There are only three types of treasures that can be allocated to the caves. These include Ruby, Diamond, and Sapphire.
- Dungeon can only be created if the given interconnectivity degree leads to atleast one of the start and end cave pairs having 5 as minimum distance.
- Grid of the dungeon can have a maximum of 100 rows and 100 columns and a minimum of 5 rows and 5 columns.
- Interconnectivity degree can only be lesser than or equal to 'leftover' edges, which is remaining after the construction of minimum spanning tree.
- Percentage of caves to which the treasure is added to rounded up to ceiling, if a decimal number generated.
- CrookedArrow is the only type of weapon that exists in the game.
- If the number of monsters given is greater than the caves then the number of monsters is equal to the number of caves.
- Pits can exist only in the caves. Only one pit can be allocated into a cave. It can exist along with monsters or other items. Player escapes the pit if the player has one treasure item each.
- Thieves can exist only in tunnels. Only one thief can be allocated into a tunnel. Player has five seconds to escape from the thief or else the player loses all the items from the bag.
- System of user running the program is in working order.

## Limitations

As per the requirement, if the dungeon creation doesn't enforce distance between start and end cave as 5, dungeon creation is not allowed. Since the dungeon creation is itself random, there may arise a scenario where the creation may fail for some builders even though the parameters stay the same.

## Citations

- (2021, October 22). Kruskal’s Algorithm – Minimum Spanning Tree (MST). Tutorial Horizon. https://algorithms.tutorialhorizon.com/kruskals-algorithm-minimum-spanning-tree-mst-complete-java-implementation/
 - (2021, October 22). Implement graph using map. Tutorial Horizon. https://algorithms.tutorialhorizon.com/implement-graph-using-map-java/
 - (2021, November 12). Command Pattern in spring boot. Medium. https://medium.com/codex/how-i-implemented-command-pattern-in-spring-boot-870c1f2c73b0
 - (2021, November 14).Command patterns in spring framework. Dzone. https://dzone.com/articles/command-patterns-in-spring-framework
 - (2021, November 14). Shortest Path Algorithms. Hackerearth. https://www.hackerearth.com/practice/algorithms/graphs/shortest-path-algorithms/tutorial/
 - (2021, November 15). Depth first search or dfs for a graph. Geeksforgeeks. https://www.geeksforgeeks.org/minimum-number-of-edges-between-two-vertices-of-a-graph-using-dfs/?ref=rp
 - (2021, November 25). Android like toast in swing. StackOverflow. https://stackoverflow.com/questions/10161149/android-like-toast-in-swing
 - (2021, November 26). Dialog boxes. Oracle Docs. https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
