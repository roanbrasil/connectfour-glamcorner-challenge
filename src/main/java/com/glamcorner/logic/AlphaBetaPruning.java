package com.glamcorner.logic;

import com.glamcorner.controller.Board;
import com.glamcorner.controller.BoardImpl;
import com.glamcorner.model.Move;
import org.springframework.stereotype.Component;

@Component
public class AlphaBetaPruning implements ArtificialIntelligenceAlgorithm {
    private Board copy;
    @Override
    public Move pickMove(Board boardParam, int depth, int low, int high) {

        Move currentMove;
        Move bestMove;

        char[][] board = boardParam.getBoard();
        int playerToMove = boardParam.getPlayerNum();
        // A dummy move that will be replaced when a real move is evaluated,
        // so the column number is irrelevant.
        bestMove = new Move(Integer.MIN_VALUE, 0);
        // Run through possible moves
        for (int c = 1; c <= Board.COLS; c++) {
            if (boardParam.isValidMove(c)) { // See if legal move
                // clone of board
                copy = new BoardImpl(playerToMove, boardParam.getPlayers(), board);
                copy.makeMove(c); // Make the move
                if (copy.isGameOver()) {
                    currentMove = new Move(Integer.MAX_VALUE, c);
                }
                else if (playerToMove == copy.getPlayerNum()) { // Did current player change?
                    currentMove = pickMove(copy, depth, low, high); // No, so no depth change
                    currentMove.setMove(c); // Remember move made
                }
                else if (depth > 0) { // Player changed, so reduce search depth
                    currentMove = pickMove(copy, depth - 1, -high, -low);
                    currentMove.setValue(-currentMove.getValue()); // Good move for opponent is bad for me, opposite
                    currentMove.setMove(c); // Record a move made
                }
                else // Depth exhausted, so estimate who is winning by comparing kalahs
                    currentMove = new Move(copy.score(), c);
                if (currentMove.getValue() > bestMove.getValue()) { // Verify if found a new best move
                    bestMove = currentMove;
                    low = Math.max(low, bestMove.getValue()); // Update the low value, also
                }
            }
        }
        return bestMove;
    }
}
