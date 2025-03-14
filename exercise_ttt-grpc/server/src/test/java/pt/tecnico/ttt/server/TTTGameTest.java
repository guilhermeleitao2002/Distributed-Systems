package pt.tecnico.ttt.server;

import org.junit.jupiter.api.*;
import pt.tecnico.ttt.PlayResult;

import static org.junit.jupiter.api.Assertions.*;

/** Integration test suite */
public class TTTGameTest {

    @Test
    public void testOutOfBoundsBehaviour() {
        // do something ...
        TTTGame ttt = new TTTGame();
        PlayResult result = ttt.play(3,2,0);
        // if the ass
        assertEquals(PlayResult.OUT_OF_BOUNDS,result);
    }

    @Test
    public void testWrongTurn() {
        TTTGame ttt = new TTTGame();
        PlayResult result = ttt.play(1,2,1);
        // if the ass
        assertEquals(PlayResult.WRONG_TURN,result);
    }

    @Test
    public void testSuccessfulPlay() {
        TTTGame ttt = new TTTGame();
        PlayResult result = ttt.play(1,2,0);
        assertEquals(PlayResult.SUCCESS,result);
    }

}