import static org.junit.Assert.*;
import org.junit.Test;

//0 is general 1 is simple
//public static int gameMode = -1;

public class GameOverTest {
    @Test
    //checkForGameOver(redPoints, bluePoints, gameMode, turnCount, maxTurns, recording)
    public void testGeneralBlueWon() {
        assertEquals("error in checkForGameOver()", true, 
        SOS1.checkForGameOver(0, 1, 0, 9, 9, false));
    }
    public void testGeneralRedWon() {
        assertEquals("error in checkForGameOver()", true, 
        SOS1.checkForGameOver(1, 0, 0, 9, 9, false));
    }
    public void testGeneralTie() {
        assertEquals("error in checkForGameOver()", true, 
        SOS1.checkForGameOver(1, 1, 0, 9, 9, false));
    }
    public void testSimpleBlueWon() {
        assertEquals("error in checkForGameOver()", true, 
        SOS1.checkForGameOver(0, 1, 1, 9, 9, false));
    }
    public void testSimpleRedWon() {
        assertEquals("error in checkForGameOver()", true, 
        SOS1.checkForGameOver(1, 0, 1, 9, 9, false));
    }
    public void testSimpleTie() {
        assertEquals("error in checkForGameOver()", true, 
        SOS1.checkForGameOver(0, 0, 1, 9, 9, false));
    }
}
