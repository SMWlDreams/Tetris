# Tetris

## Authors: Joseph Hable

### Created in Java 11 using JavaFX 11

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

### NOTE

This is an alpha release. There is no audio currently. The game will run and can be restarted after a loss only. All core features of the hit NES game TETRIS are here.

All graphics were manually redrawn by me. The color palate was inspired by the original TETRIS game for the NES.