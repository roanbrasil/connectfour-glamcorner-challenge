package com.glamcorner.controller;

import com.glamcorner.ai.ArtificialIntelligenceAlgorithm;
import com.glamcorner.model.ComputerPlayer;
import com.glamcorner.model.HumanPlayer;
import com.glamcorner.model.Player;
import com.glamcorner.view.Console;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Observable;

@Setter
@Getter
@Controller
public class Game extends Observable{

    public final static int ROWS = 6; // Board height
    public final static int COLS = 7; // Board width
    public final static char EMPTY = '.'; // Indicate empty place
    public final static char RED = 'R'; // Indicate the first player's checker
    public final static char YELLOW = 'Y'; // Indicate second player's checker
    public final static char [] PIECES = {RED, YELLOW};
    private char [][] board;
    private int playerToMoveNum; // 0 or 1 for first and second player
    private int move;
    @Autowired
    Console console; //view

    @Autowired
    ArtificialIntelligenceAlgorithm artificialIntelligenceAlgorithm;

    Player[] players; //model

    public Game(){this.board = this.initGame();}

    public void display () {
        char[][] board = this.getBoard();
        System.out.println();
        for (int r = ROWS-1; r>=0; r--) {
            System.out.println(board[r][0] + " " + board[r][1] + " " + board[r][2] + " " + board[r][3] + " " + board[r][4] + " " + board[r][5] + " " + board[r][6]);
        }
    }

    public char [][] initGame(){
        char [][] initBoard = new char[ROWS][COLS];
        for (int j = 0; j < ROWS; j++) {
            for (int k = 0; k < COLS; k++) {
                initBoard[j][k] = '.';
            }
        }
        return  initBoard;
    }

    public char [][] getUpdateGame(int playerToMoveNum, Player [] players, char [][] initBoard){
        this.board = new char[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j =0; j<COLS; j++) {
                this.board[i][j] = initBoard[i][j];
            }
        }
        this.players = players;
        this.playerToMoveNum = playerToMoveNum;
        return this.board;
    }

    public Player getPlayerToMove() {
        return players[playerToMoveNum];
    }

    public boolean isValidMove(int col) {
        if((col-1)<COLS && (col-1)>=0){
            if (board[ROWS-1][col-1] == EMPTY){
                return true;
            }
            return false;
        }
        return false;
    }

    public void makeMove(int col) {
        int r = 0;
        while (this.board[r][col-1] != EMPTY && r < ROWS) {
            r++;
        }
        this.board[r][col-1] = PIECES[this.playerToMoveNum];
        this.playerToMoveNum = 1 - this.playerToMoveNum;
    }

    public int getUserMove() {

        int col; // The pit under consideration
        System.out.println();

        col = this.console.getIntAnswer("Column to drop a checker into, " + this.getPlayerToMove().getName() + "? ");
        while(this.isValidMove(col)==false)
        {
            System.out.println("Illegal move. Try again. Please choose the column between 1 to 7");
            col = this.console.getIntAnswer("Column to drop a checker into? ");
        }
        return col; // Adjust to player's side
    }

    public boolean isFull() {
        boolean full = true;
        for (int c = 0; c < COLS; c++) {
            if (this.board[ROWS-1][c] == EMPTY) {
                full = false;
            }
        }
        return full;
    }

    public boolean isGameOver() {
        boolean gameOver = false;
        if (isFull()) {
            gameOver = true;
        }
        else if (this.connectFourAnywhere()) {
            gameOver = true;
        }
        return gameOver;
    }

    /**
     * Get the score of a board
     */
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

                if (this.board[r][col] == PIECES[1-this.playerToMoveNum]) {
                    unblocked = false;
                }
                if (this.board[r][col] == PIECES[this.playerToMoveNum]) {
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
                    if (this.board[r][c] == PIECES[1-this.playerToMoveNum]) {
                        unblocked = false;
                    }
                    if (this.board[r][c] == PIECES[this.playerToMoveNum]) {
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
                if (this.board[row][c] == PIECES[1-this.playerToMoveNum]) {
                    unblocked = false;
                }
                if (this.board[row][c] == PIECES[this.playerToMoveNum]) {
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
                    if (this.board[r][c] == PIECES[1-this.playerToMoveNum]) {
                        unblocked = false;
                    }
                    if (this.board[r][c] == PIECES[this.playerToMoveNum]) {
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

    public Player makeComputerPlayer(int depth) {
        return new ComputerPlayer("Computer", depth, this.artificialIntelligenceAlgorithm);
    }

    public Player makeHumanPlayer() {
        String playerName = this.console.getAnswer("Enter the name of the human player.");
        return new HumanPlayer(playerName);
    }

    public int getLevel() {
        int level = this.console.getIntAnswer("Please choose the LEVEL of the Game:\n1-Easy\n2-Medium\n3-Hard\n");
        int depth = -1;
        switch (level) {
            case 1:
                depth = 1;
                break;
            case 2:
                depth = 3;
                break;
            case 3:
                depth = 6;
                break;
            default:
                depth = 1;
        }
        return depth;
    }

    public Player[] chooseVersusPlayers(){
        int rivalOption = this.console.getIntAnswer("Choose what rival option of Connect Four you want:\n"+
                "1 - Human VS Human\n"+
                "2 - Human VS Computer\n" +
                "3 - Computer VS Computer\n");
        Player[] playerArr = new Player[2];
        int depthLevel = this.getLevel();;
        switch (rivalOption) {
            case 1:// Human VS Human
                playerArr[0] = this.makeHumanPlayer();
                playerArr[1] = this.makeHumanPlayer();
                break;
            case 2: // Human VS Computer
                playerArr[0] = this.makeHumanPlayer();
                playerArr[1] = this.makeComputerPlayer(depthLevel);
                break;
            case 3:// Computer VS Computer
                playerArr[0] = this.makeComputerPlayer(depthLevel);
                playerArr[1] = this.makeComputerPlayer(depthLevel);
                break;
            default: // by Default => Human VS Computer
                playerArr[0] = this.makeHumanPlayer();
                playerArr[1] = this.makeComputerPlayer(depthLevel);
        }
        this.addObserver(playerArr[0]);
        this.addObserver(playerArr[1]);
        return playerArr;
    }

    public void initToPlay(){
        while(true) {

            this.board = this.initGame();
            this.setPlayers(this.chooseVersusPlayers());
            this.setPlayerToMoveNum(0);

            this.display();
            while (!this.isGameOver()) {
                notifyChange();
//                int move = this.getPlayerToMove().getChoiceOfNextMove(this);
                this.console.reportMove(this.getMove(), this.getPlayerToMove().getName());
                this.makeMove(this.getMove());
                this.display();
            }
            if (this.isFull())
                this.console.reportToUser("It is a draw, it is full.");
            else
                this.console.reportToUser(this.players[1 - this.getPlayerToMoveNum()].getName() + " wins!");

            String answer = this.console.getAnswer("Do you wanna play one more time? Y/N");
            if("N".equals(answer.toUpperCase()) || "NO".equals(answer.toUpperCase())){
                break;
            }
        }
    }
    private void notifyChange(){
        // Call Observable method which sets flag that state changed
        setChanged();

        // Call Observable method with next player (i.e. R or Y)
        notifyObservers(getPlayerToMoveNum());
    }
}
