package com.glamcorner.ai;

import com.glamcorner.controller.Game;
import com.glamcorner.model.Move;
import org.springframework.stereotype.Component;

@Component
public class AlphaBetaPruning implements ArtificialIntelligenceAlgorithm {

    Game copyGame;

    @Override
    public Move pickMove(Game gameParam, int depth, int low, int high) {

        Move currentMove;
        Move bestMove;

        char[][] board = gameParam.getBoard();
        int playerToMove = gameParam.getPlayerToMoveNum();
        // A dummy move that will be replaced when a real move is evaluated,
        // so the column number is irrelevant.
        bestMove = new Move(Integer.MIN_VALUE, 0);
        // Run through possible moves
        for (int c = 1; c <= Game.COLS; c++) {
            if (gameParam.isValidMove(c)) { // See if legal move
                // clone of board
                copyGame = new Game();
                board = copyGame.getUpdateGame(playerToMove, gameParam.getPlayers(),board);
                copyGame.setPlayerToMoveNum(playerToMove);
                copyGame.setPlayers(gameParam.getPlayers());
                copyGame.setBoard(board);
                copyGame.makeMove(c); // Make the move
                if (copyGame.isGameOver()) {
                    currentMove = new Move(Integer.MAX_VALUE, c);
                }
                else if (playerToMove == copyGame.getPlayerToMoveNum()) { // Did current player change?
                    currentMove = pickMove(copyGame, depth, low, high); // No, so no depth change
                    currentMove.setMove(c); // Remember move made
                }
                else if (depth > 0) { // Player changed, so reduce search depth
                    currentMove = pickMove(copyGame, depth - 1, -high, -low);
                    currentMove.setValue(-currentMove.getValue()); // Good move for opponent is bad for me, opposite
                    currentMove.setMove(c); // Record a move made
                }
                else // Depth exhausted, so estimate who is winning by comparing kalahs
                    currentMove = new Move(copyGame.score(), c);
                if (currentMove.getValue() > bestMove.getValue()) { // Verify if found a new best move
                    bestMove = currentMove;
                    low = Math.max(low, bestMove.getValue()); // Update the low value, also
                }
            }
        }
        return bestMove;
    }
}
