package com.glamcorner.controller;

import com.glamcorner.model.Player;

public interface Board {
    public final static int ROWS = 6; // Board height
    public final static int COLS = 7; // Board width
    public final static char EMPTY = '.'; // Indicate empty place
    public final static char RED = 'R'; // Indicate the first player's checker
    public final static char YELLOW = 'Y'; // Indicate second player's checker
    public final static char [] PIECES = {RED, YELLOW};

    /**
     * Gets a Text Console representing the board.
     *
     * @return the board
     */
    char [][] getBoard();

    /**
     * Gets an array holding 2 Player objects
     * @return the players
     */
    Player[] getPlayers();

    /**
     * Gets the number of the player whose move it is
     * @return the number of the player whose move it is
     */
    int getPlayerNum ();

    /**
     * Gets the Player whose turn it is to move
     * @return the Player whose turn it is to move
     */
     Player getPlayerToMove();

    /**
     * Verify if the current move is a valid move
     * @param col column where we want to move
     * @return true if the move is valid
     */
    boolean isValidMove(int col);

    /**
     * Make a move, dropping a checker in the given column
     * @param col the column to get the new checker
     */
    void makeMove(int col);

    /**
     * Verify and return when the board is full
     * @return true if the board is full
     */
    boolean isFull();

    /**
     * Verify when the game is over
     * @return true iff the game is over
     */
    boolean isGameOver();

    /**
     * Get the score of a board
     * @return int
     */
    int score();
}
