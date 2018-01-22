package com.glamcorner.model;

import com.glamcorner.logic.AlphaBetaPruning;
import com.glamcorner.logic.ArtificialIntelligenceAlgorithm;
import com.glamcorner.controller.Board;
import com.glamcorner.view.Console;

public class ComputerPlayer extends Player {

    ArtificialIntelligenceAlgorithm artificialIntelligenceAlgorithm;
    private int depth;

    /**
     * Constructs a computer player that uses alpha-beta pruning
     * @author Ryan Maguire
     * @param name
     * @param maxDepth
     */
    public ComputerPlayer(String name, int maxDepth) {
        super(name);
        depth = maxDepth;
        this.artificialIntelligenceAlgorithm = new AlphaBetaPruning();
    }
    @Override
    public int getChoiceOfNextMove(Board board, Console console) {
        // Returns the computer play's choice using alpha-beta search
        int move = this.artificialIntelligenceAlgorithm.pickMove(board, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE).getMove();
        console.reportMove(move, board.getPlayerToMove().getName());
        return move;
    }
}
