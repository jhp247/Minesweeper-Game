# Minesweeper-Game

A Java game console for MineSweeper.

Player Instructions:  
Enter 1 to Start a new game. 
Enter 2 to Set a the difficulty level.  
Enter 3 to View player time records for each difficulty.

In-game Instructions:  
The main objective of this game is to open all the tiles that are NOT mines. The user has completed the map in this case and has won the game.  
Each tile is assigned a coordinate and certain tiles will leave clues to help locate where the mines are.  
When a mine is opened, the user has walked into a trap and the game will be over. The user has lost the game.

Enter "toggle" to switch between Flagmode on or Flagmode off.

When Flagmode is ON, enter "(Row, Column)" to flag the desired coordinate where "Row" should be replaced with the row of the coordinate and "Column" should be replaced with the column of the coordinate. Flagged coordinates are commonly used by users to locate where mines are and as a reminder to not open the tile in the future.

When Flagmode is OFF, enter "(Row, Column)" to open the desired coordinate where "Row" should be replaced with the row of the coordinate and "Column" should be replaced with the column of the coordinate.

User Interface (UI):  
"-" tile will represent tiles that haven't been opened/revealed yet.  
"/" tile will represent the tiles that the user has flagged.  
"number" tile will be replaced with an integer to represent how many mines are adjacent to this tile.  
" " will mean that there are no mines adjacent to this tile.  
"*" tile will represent the tiles that contain mines and will only reveal once the game is over.  
"X" will represent the tile that the user has flagged but doesn't contain a mine on it. It will only be reveled once the game is over.

Have fun with the game! :)

** YET TO BE IMPLEMENTED**  
A function that records how much time is spent to clear a game.  
A function that shows all the best records for each difficulty.  
Error-handling operations when none of the menus are chosen.  
Error-handling operations when none of the difficulties are chosen.  
Error-handling operations in-game for coordinates out of bounds, or an unrecognized command.  