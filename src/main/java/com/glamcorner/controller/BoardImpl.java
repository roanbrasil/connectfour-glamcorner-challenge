package com.glamcorner.controller;

import com.glamcorner.model.Player;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class BoardImpl implements Board{
    private char [][] board;
    private int playerToMoveNum; // 0 or 1 for first and second player
    @Autowired
    private Player[] players; // Array of the two players

    /**
     * Constructs a game in the initial state
     * @param playerNum the player whose move it is
     * @param thePlayers the player objects
     */
    public BoardImpl(int playerNum, Player [] thePlayers) {
        char [][] initBoard = new char[ROWS][COLS];
        for (int j = 0; j < ROWS; j++) {
            for (int k = 0; k < COLS; k++) {
                initBoard[j][k] = '.';
            }
        }
        board = initBoard;
        playerToMoveNum = playerNum;
        players = thePlayers;
    }

    /**
     * Constructs a game given a board
     * @param playerNum the player whose move it is
     * @param thePlayers the player objects
     * @param initBoard the board to copy into this state
     */
    public BoardImpl(int playerNum, Player [] thePlayers, char [][] initBoard) {
        board = new char[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j =0; j<COLS; j++) {
                board[i][j] = initBoard[i][j];
            }
        }

        playerToMoveNum = playerNum;
        players = thePlayers;
    }
   @Override
    public char [][] getBoard() {
        return board;
    }

   @Override
    public Player [] getPlayers() {
        return players;
    }

   @Override
    public int getPlayerNum () {
        return playerToMoveNum;
    }

    @Override
    public Player getPlayerToMove() {
        return players[playerToMoveNum];
    }

    @Override
    public boolean isValidMove(int col) {
        if((col-1)<COLS && (col-1)>=0){
            if (board[ROWS-1][col-1] == EMPTY){
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public void makeMove(int col) {
        int r = 0;
        while (board[r][col-1] != EMPTY && r < ROWS) {
            r++;
        }
        board[r][col-1] = PIECES[playerToMoveNum];
        playerToMoveNum = 1 - playerToMoveNum;
    }

   @Override
    public boolean isFull() {
        boolean full = true;
        for (int c = 0; c < COLS; c++) {
            if (board[ROWS-1][c] == EMPTY) {
                full = false;
            }
        }
        return full;
    }

    @Override
    public boolean isGameOver() {
        boolean gameOver = false;
        if (isFull()) {
            gameOver = true;
        }
        else if (connectFourAnywhere()) {
            gameOver = true;
        }
        return gameOver;
    }

    @Override
    public int score(){
        int score = 0;
        for (int r= 0; r < ROWS; r++) {
            if (r <= ROWS-4) {
                for (int c = 0; c < COLS; c++) {
                    score += score(r, c);
                }
            }
            else {
                for (int c = 0; c <= COLS-4; c++) {
                    score += score(r, c);
                }
            }
        }

        return score;
    }

    /**
     * Helper method to get the score of a board
     */
    public int score(int row, int col){
        int score = 0;
        boolean unblocked = true;
        int tally = 0;
        //int r, c;
        if (row < ROWS-3) {
            //check up
            unblocked = true;
            tally = 0;
            for (int r=row; r<row+4; r++) {

                if (board[r][col] == PIECES[1-playerToMoveNum]) {
                    unblocked = false;
                }
                if (board[r][col] == PIECES[playerToMoveNum]) {
                    tally ++;
                }
            }
            if (unblocked == true) {
                score = score + (tally*tally*tally*tally);
            }

            if (col < COLS-3) {
                //check up and to the right
                unblocked = true;
                tally = 0;
                for (int r=row, c=col; r<row+4; r++, c++) {
                    if (board[r][c] == PIECES[1-playerToMoveNum]) {
                        unblocked = false;
                    }
                    if (board[r][c] == PIECES[playerToMoveNum]) {
                        tally ++;
                    }
                }
                if (unblocked == true) {
                    score = score + (tally*tally*tally*tally);
                }
            }
        }
        if (col < COLS-3) {
            //check right
            unblocked = true;
            tally = 0;
            for (int c=col; c<col+4; c++) {
                if (board[row][c] == PIECES[1-playerToMoveNum]) {
                    unblocked = false;
                }
                if (board[row][c] == PIECES[playerToMoveNum]) {
                    tally ++;
                }
            }
            if (unblocked == true) {
                score = score + (tally*tally*tally*tally);
            }

            if (row > 2) {
                //check down and to the right
                unblocked = true;
                tally = 0;
                for (int r=row, c=col; c<col+4; r--, c++) {
                    if (board[r][c] == PIECES[1-playerToMoveNum]) {
                        unblocked = false;
                    }
                    if (board[r][c] == PIECES[playerToMoveNum]) {
                        tally ++;
                    }
                }
                if (unblocked == true) {
                    score = score + (tally*tally*tally*tally);
                }
            }
        }

        return score;

    }
    /**
     * Check the board to see if there is a Connect Four anywhere
     * @return true if there is a connect 4 somewhere
     */
    public boolean connectFourAnywhere() {
        boolean connect4 = false;
        for (int r= 0; r < ROWS && connect4 == false; r++) {
            if (r <= ROWS-4) {
                for (int c = 0; c < COLS && connect4 == false; c++) {
                    connect4 = connectFour(r, c);
                }
            }
            else {
                for (int c = 0; c <= COLS-4 && connect4 == false; c++) {
                    connect4 = connectFour(r, c);
                }
            }
        }

        return connect4;
    }

    /**
     * Given a row and column, check for a connect 4 from that position
     * @return true if there is a connect 4.
     *
     */
    public boolean connectFour(int row, int col) {
        boolean c4 = false;
        //int r, c;
        if (row < ROWS-3) {
            //check up
            c4 = true;
            for (int r=row; r<row+4; r++) {
                if (board[r][col] != PIECES[1-playerToMoveNum]) {
                    c4 = false;
                }
            }

            if (col < COLS-3 && c4==false) {
                //check up and to the right
                c4 = true;
                for (int r=row, c=col; r<row+4; r++, c++) {
                    if (board[r][c] != PIECES[1-playerToMoveNum]) {
                        c4 = false;
                    }
                }
            }
        }
        if (col < COLS-3 && c4==false) {
            //check right
            c4 = true;
            for (int c=col; c<col+4; c++) {
                if (board[row][c] != PIECES[1-playerToMoveNum]) {
                    c4 = false;
                }
            }

            if (row > 2 && c4==false) {
                //check down and to the right
                c4 = true;
                for (int r=row, c=col; c<col+4; r--, c++) {
                    if (board[r][c] != PIECES[1-playerToMoveNum]) {
                        c4 = false;
                    }
                }
            }
        }

        return c4;
    }
}
