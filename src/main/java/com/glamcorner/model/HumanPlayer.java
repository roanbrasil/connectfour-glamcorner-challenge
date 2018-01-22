package com.glamcorner.model;

import com.glamcorner.controller.Game;

public class HumanPlayer extends Player{

    public HumanPlayer (String name) {
        super(name);
    }
    @Override
    public int getChoiceOfNextMove(Game game) {
        return game.getUserMove();
    }
}
