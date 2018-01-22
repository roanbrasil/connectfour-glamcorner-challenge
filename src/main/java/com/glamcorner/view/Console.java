package com.glamcorner.view;

import com.glamcorner.controller.Board;
import com.glamcorner.model.Player;

public interface Console {

    /**
     * Display the board
     * @param board
     */
    void display (Board board);

    /**
     * Asks the user for a move
     * The move will be in the range 0 to Connect4State.COLS-1.
     * @param board current state of the game
     * @return the number of the move that player chose
     */
    int getUserMove(Board board);

    /**
     * Reports the move that a player has made.
     * The move should be in the range 0 to Connect4State.COLS-1.
     * @param chosenMove the move to be reported
     * @param name the player's name
     */
    void reportMove (int chosenMove, String name);

    /**
     * Ask the user the question and return the answer as an int
     * @param question the question to ask
     * @return The depth the player chose
     */
    int getIntAnswer (String question);

    /**
     * Convey a message to user
     * @param message the message to be reported
     */
    void reportToUser(String message);

    /**
     * Ask the question and return the answer
     * @param question the question to ask
     * @return the answer to the question
     */
    String getAnswer(String question);

    /**
     * Constructs a Connect 4 player through Controller.
     * @param console the view to use to communicate to the world
     * @param depth depth of level AI algorithm
     * @return
     */
    Player makeComputerPlayer(Console console, int depth);

    /**
     * Constructs a Connect 4 player through Controller.
     * @param console the view to use to communicate to the world
     * @return
     */
    Player makeHumanPlayer(Console console);

    /**
     * Get the difficulty level that user has chosen
     * @return int
     */
    int getLevel();

    /**
     * Orchestration to init the game to play
     */
    void initToPlay();

    /**
     * Get which option of players that are going to players against other
     * 1-Human VS Human
     * 2-Human VS Computer
     * 3-Computer VS Computer
     * @return Player[]
     */
    Player[] chooseVersusPlayers();
}
