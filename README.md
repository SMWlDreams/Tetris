# Tetris

## Authors: Joseph Hable

### Created in Java 11 using JavaFX 11

### Audio powered by Beads Java Audio Library

### Executable launcher created using JSmooth Java Wrapper

# Change Log

## v0.1.0 Alpha

Initial testing release

Move your tetris pieces using A and D. Rotate them with the "." and "/" keys. Soft drop using S.

## v0.2.0 Alpha

### Improvements

Added new animations for line clears and a background flash for a tetris. 

Added an end of game screen when you lose.

After losing you can restart the game by pressing any key on the keyboard.

Moved image files from Assets to src because of a jar issue. Will be moving them back once a solution is found.

### Bug Fixes

Score will no longer drop a digit after placing the first shape

If you attempt to rotate a shape with a tile above the edge of the board it will no longer randomly become visible

## v0.3.0 Alpha

### Improvements

Added a main menu and a stock title screen (actual title screen in progress).

Added background audio looping, the audio will not speed up when you get a piece in the top 4 lines.

You can select a track by clicking on the respective name in the main menu.

You can now select your starting level by clicking on the respective level. If you right click the level it will add 10 to the starting level.

You can register high scores now and the display will properly show the top score, updating in real time if you beat to previous top score during a run.

Added line clear, block rotation, block movement, block placement, and game over sfx.

### Bug Fixes

Stats will no longer always increment the I-piece for the first piece placed in a game

If you top out you will no longer get a counted line clear if the entire top row is full (note: You can still clear the top line normally)

You can no longer lose by rotating pieces over other pieces at the edge of the board while they are off screen

## v0.4.0 Alpha

### Final Alpha Release

### Improvements

The first piece for each new game will be delayed 48 frames to allow the board time to load to make the game run more smoothly. This is also present in the original game.

Added upper and lower case character support for names in high scores.

Added in score incrementation for soft dropping pieces based on row you held down from.

Moved saved high scores to the user.dir\AppData\Roaming\Tetris folder from the user.dir\Tetris folder.

Added a basic pause screen that can be invoked by pressing the space bar.

### Projected Improvements

Added a new title screen and new main menu

Added in some stats found in the Classic Tetris World Championship to the GUI - An I-piece drought counter and the % of line clears that were a tetris

Added a special easter egg on the main menu. Input the secret code to gain access to a hidden song.

### Bug Fixes

When restarting a game on the main menu the stats now properly go to 000 instead of 001.

Ending a game with the music set to none will no longer crash the game.

### NOTE

This is an alpha release. The game will run, all core features of the hit NES game TETRIS are here.

The title screen is from the original game. A new title screen is currently being drawn by a close friend of mine.

The menu screen is simply a basic drawing meant to allow me to hold all the information, a new, better looking menu will be made for the next update.

All audio sample come from the original TETRIS NES game. Audio copyright Nintendo 1989. All audio was upscaled to stereo by me.

All other graphics were manually redrawn by me. The color palate was inspired by the original TETRIS game for the NES.