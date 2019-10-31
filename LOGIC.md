***Written by:*** _Max (21/10/2019)_

## Board Logic:
The game board’s logic is stored as an array of nodes, each of which represents a space on the board. Each node stores the following information: pointers to any nodes that the player can move to after this one; whether the space has a ladder or snake on it, and where it would take the player; the space’s coordinates on the visual board; and any additional information, such as powerups, special events, or whether the space is a goal. 

## Player Logic:
Each player has the following associated with them: a piece colour, which represents them on the board; a player number, from one to four; a pointer to a node that the player’s piece occupies; and any additional powerups or abilities the player has access to. 

## Game Logic:
After turn order is decided, the game loops through the turns of the players in a set order. On each players turn, the player rolls a die, which returns a randomly generated value from 1 to 6. That player’s piece moves from its current position forward to the next space pointed to by the current node (if two or more spaces could be moved to, the player may choose which way to proceed). This process is repeated until the piece has moved a number of spaces equal to the number rolled. Any powerups collected along the way are applied to that player or put into their inventory. When the player stops moving, the current space is checked. If that space has a snake or a ladder on it, the piece is moved to its endpoint. If at any point a player reaches the goal, that player wins the game. 

## Visual Logic:
The game board is visually shown to the player as a 2D image, with spaces marked on it and visual indicators of which space each player’s piece is on, where each space leads, and the locations of snakes and ladders. When the player’s piece moves, the image of the piece moves smoothly between the coordinates of the two nodes in a parabolic path. In addition, the player can zoom in and out, scaling the size of the background and its elements to get a more general or specific view.
