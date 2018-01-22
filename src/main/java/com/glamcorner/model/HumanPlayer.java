package com.glamcorner.model;

import com.glamcorner.controller.Game;

import java.util.Observable;

public class HumanPlayer extends Player{

    public HumanPlayer (String name) {
        super(name);
    }
    @Override
    public int getChoiceOfNextMove(Game game) {
        return game.getUserMove();
    }

    @Override
    public void update(Observable o, Object arg) {
        Game game = (Game) o;
        System.out.println("Human observing!!!");
        int playerToMove = (int) arg;
        if(playerToMove==0){
            game.setMove(this.getChoiceOfNextMove(game));
        }
    }
}
