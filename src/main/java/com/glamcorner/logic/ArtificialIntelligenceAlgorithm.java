package com.glamcorner.logic;

import com.glamcorner.controller.Board;
import com.glamcorner.model.Move;

public interface ArtificialIntelligenceAlgorithm {
    Move pickMove(Board boardParam, int depth, int low, int high);
}
