package com.glamcorner.model;

import com.glamcorner.controller.Board;
import com.glamcorner.view.Console;

public class HumanPlayer extends Player{

    public HumanPlayer (String name) {
        super(name);
    }
    @Override
    public int getChoiceOfNextMove(Board board, Console console) {
        return console.getUserMove(board);
    }
}
