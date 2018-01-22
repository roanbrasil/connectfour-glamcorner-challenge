package com.glamcorner.model;

import com.glamcorner.controller.Board;
import com.glamcorner.view.Console;

public abstract class Player {
    String playerName;
    /**
     * @param name player's name
     */
    public Player (String name) {
        playerName = name;
    }
    /**
     * @return the player's name
     */
    public String getName() {
        return playerName;
    }
    /**
     * Gets and returns the player's choice of move
     * @param board current game state
     * @param console the object that displays the game
     * @return move chosen by the player, in the range
     * 0 to Connect4State-1
     */
    public abstract int getChoiceOfNextMove(Board board, Console console);
}
