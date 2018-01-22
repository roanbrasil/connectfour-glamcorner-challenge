package com.glamcorner.ai;

import com.glamcorner.controller.Game;
import com.glamcorner.model.Move;

public interface ArtificialIntelligenceAlgorithm {
    Move pickMove(Game gameParam, int depth, int low, int high);
}
