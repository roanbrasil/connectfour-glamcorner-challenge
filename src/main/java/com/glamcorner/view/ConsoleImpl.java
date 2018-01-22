package com.glamcorner.view;

import com.glamcorner.controller.Board;
import com.glamcorner.controller.BoardImpl;
import com.glamcorner.model.ComputerPlayer;
import com.glamcorner.model.HumanPlayer;
import com.glamcorner.model.Player;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class ConsoleImpl implements Console {
    private Scanner input; // Hold the input stream
    private Board board;
    private Player[] player;

    public ConsoleImpl (){
        input = new Scanner(System.in);
    }
    /**
     * Displays the current board
     * @param b current state of the game
     */
    public void display (Board b) {
        char[][] board = b.getBoard();
        System.out.println();
        for (int r = Board.ROWS-1; r>=0; r--) {
            System.out.println(board[r][0] + " " + board[r][1] + " " + board[r][2] + " " + board[r][3] + " " + board[r][4] + " " + board[r][5] + " " + board[r][6]);
        }
        System.out.println("1 2 3 4 5 6 7");
        System.out.println("Please select column between 1 to 7 that is not full.");
    }

    @Override
    public int getUserMove(Board b) {
        int col; // The pit under consideration
        char [][] board = b.getBoard();
        System.out.println();

        col = getIntAnswer("Column to drop a checker into, " + b.getPlayerToMove().getName() + "? ");

        while(b.isValidMove(col)==false)
        {
            System.out.println("Illegal move. Try again. Please choose the column between 1 to 7");
            col = getIntAnswer("Column to drop a checker into? ");
        }

        return col; // Adjust to player's side
    }

    @Override
    public void reportMove (int chosenMove, String name) {
        System.out.println("\n" + name + " drops a Checker in Column " + chosenMove);
    }

    @Override
    public int getIntAnswer (String question) {
        int answer = 0;
        boolean valid = false;

        // Ask for a number
        System.out.print(question + " ");
        while(!valid) {
            try {
                answer = input.nextInt();;
                valid = true; // If got to here we have a valid integer
            }
            catch(InputMismatchException ex) {
                reportToUser("That was not a valid integer");
                valid = false;
                input.nextLine(); // Throw away the rest of the line
                System.out.print(question + " ");
            }
        }
        input.nextLine(); // Throw away the rest of the line

        return answer;
    }

    @Override
    public void reportToUser(String message) {
        // Reports something to the user
        System.out.println(message);
    }

    @Override
    public String getAnswer(String question) {
        System.out.print(question);
        return input.nextLine();
    }

    @Override
    public Player makeComputerPlayer(Console console, int depth) {
            return new ComputerPlayer("Computer", depth);
    }

    @Override
    public Player makeHumanPlayer(Console console) {
        String playerName = console.getAnswer("Enter the name of the human player.");
        return new HumanPlayer(playerName);
    }

    @Override
    public int getLevel() {
        int level = this.getIntAnswer("Please choose the LEVEL of the Game:\n1-Easy\n2-Medium\n3-Hard\n");
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
        int rivalOption = this.getIntAnswer("Choose what rival option of Connect Four you want:\n"+
                "1 - Human VS Human\n"+
                "2 - Human VS Computer\n" +
                "3 - Computer VS Computer\n");
        Player[] playerArr = new Player[2];
        int depthLevel = this.getLevel();;
        switch (rivalOption) {
            case 1:// Human VS Human
                playerArr[0] = this.makeHumanPlayer(this);
                playerArr[1] = this.makeHumanPlayer(this);
                break;
            case 2: // Human VS Computer
                playerArr[0] = this.makeHumanPlayer(this);
                playerArr[1] = this.makeComputerPlayer(this, depthLevel);
                break;
            case 3:// Computer VS Computer
                playerArr[0] = this.makeComputerPlayer(this, depthLevel);
                playerArr[1] = this.makeComputerPlayer(this, depthLevel);
                break;
            default: // by Default => Human VS Computer
                playerArr[0] = this.makeHumanPlayer(this);
                playerArr[1] = this.makeComputerPlayer(this, depthLevel);
        }
        return playerArr;
    }

    public void initToPlay(){
        while(true) {

            this.player = this.chooseVersusPlayers();

            // Hold current game state
            this.board = new BoardImpl(0, this.player);
            this.display(this.board);
            while (!this.board.isGameOver()) {
                int move = board.getPlayerToMove().getChoiceOfNextMove(this.board, this);
                this.board.makeMove(move);
                this.display(this.board);
            }
            if (this.board.isFull())
                this.reportToUser("It is a draw, it is full.");
            else
                this.reportToUser(this.player[1 - this.board.getPlayerNum()].getName() + " wins!");

            String answer = this.getAnswer("Do you wanna play one more time? Y/N");
            if("N".equals(answer.toUpperCase()) || "NO".equals(answer.toUpperCase())){
                break;
            }
        }
    }
}
