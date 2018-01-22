package com.glamcorner.controller;

import com.glamcorner.ai.AlphaBetaPruning;
import com.glamcorner.model.ComputerPlayer;
import com.glamcorner.model.HumanPlayer;
import com.glamcorner.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class GameTest {

    Game game;
    ComputerPlayer computer;
    HumanPlayer human;
    Player[] players;

    @Before
    public void setUp() throws Exception {
        this.players =  new Player[2];
        this.computer = new ComputerPlayer("Computer", 6, new AlphaBetaPruning());
        this.human = new HumanPlayer("Roan");
        this.players[0] = this.human;
        this.players[1] = this.computer;
        this.game = new Game();
        this.game.setPlayerToMoveNum(0);
        this.game.setPlayers(players);
        ;
    }

    @Test
    public void validGameBoardInteraction() {

        char[][] board = this.game.getBoard();

        char[][] testBoard = {{'.','.','.','.','.','.','.'},{'.','.','.','.','.','.','.'},
                {'.','.','.','.','.','.','.'},{'.','.','.','.','.','.','.'},
                {'.','.','.','.','.','.','.'},{'.','.','.','.','.','.','.'}};

        assertThat(Arrays.deepToString(board)).isEqualTo(Arrays.deepToString(testBoard));

        assertThat(this.game.getPlayers()).isEqualTo(this.players);

        assertThat(this.game.getPlayerToMove()).isEqualTo(this.human);

        assertThat(this.game.isValidMove(1)).isTrue();
        assertThat(this.game.isValidMove(7)).isTrue();
        assertThat(this.game.isValidMove(0)).isFalse();
        assertThat(this.game.isValidMove(8)).isFalse();
    }

}
