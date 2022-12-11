import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.Test;

public class recordWriteTest {

    public String readFile(String fileName) throws IOException{
        StringBuilder fileContents = new StringBuilder();
        Scanner scanner = new Scanner(new File(fileName));
        while(scanner.hasNextLine()){
            fileContents.append(scanner.nextLine() + "\n");
        }
        scanner.close();
        return fileContents.toString();
    }
    @Test
    public void testCompareTextFiles() throws IOException {
        ArrayList<String> recordList = new ArrayList<String>();
        recordList.add("Hello World!");
        SOS1.writeToFile(recordList);
        String file1 = readFile("sosreplay.txt");
        String file2 = readFile("sosreplayTest.txt");

        assertEquals("Files are equivalent", file1, file2);
    }

}
