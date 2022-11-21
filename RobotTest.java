import static org.junit.Assert.*;
import org.junit.Test;

public class RobotTest{
    @Test
    
    //int[] aiBoxDecider(int i, int j, int turn, int gridSize)
    public void testDecideGrid(){
        assertNotNull("error in aiBoxDecider()", SOS1.aiBoxDecider(0, 0, 1, 9));
    }
    //randomGenerator()
    public void testRandom(){
        assertNotNull("error in randomGenerator()", SOS1.randomGenerator());
    }
    //String whichLetter(int x, int y){
    //public void testWhichLetter(){
        //private static Button[][] buttons;
        //buttons[0][0] = "O";
        //assertEquals("error in whichLetter()", "S", SOS1.whichLetter(0,0));
    //}
}
