package com.glamcorner.model;

import com.glamcorner.ai.AlphaBetaPruning;
import com.glamcorner.ai.ArtificialIntelligenceAlgorithm;
import com.glamcorner.controller.Game;
import org.springframework.beans.factory.annotation.Autowired;

public class ComputerPlayer extends Player {

    @Autowired
    ArtificialIntelligenceAlgorithm artificialIntelligenceAlgorithm;
    private int depth;

    /**
     * Constructs a computer player that uses alpha-beta pruning
     * @author Ryan Maguire
     * @param name
     * @param maxDepth
     */
    public ComputerPlayer(String name, int maxDepth, ArtificialIntelligenceAlgorithm artificialIntelligenceAlgorithm) {
        super(name);
        depth = maxDepth;
        this.artificialIntelligenceAlgorithm =  artificialIntelligenceAlgorithm;
    }
    @Override
    public int getChoiceOfNextMove(Game game) {
        // Returns the computer play's choice using alpha-beta search
        int move = this.artificialIntelligenceAlgorithm.pickMove(game, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE).getMove();
        return move;
    }
}
