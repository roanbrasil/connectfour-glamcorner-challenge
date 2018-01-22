package com.glamcorner.model;

import com.glamcorner.controller.Game;

import java.util.Observer;

public abstract class Player implements Observer {
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
     * @param game current game state
     * @return move chosen by the player, in the range
     * 0 to Connect4State-1
     */
    public abstract int getChoiceOfNextMove(Game game);
}
