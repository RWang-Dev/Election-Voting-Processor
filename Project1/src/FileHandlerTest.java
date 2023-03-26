import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {
    static FileHandler defaultHandler;
    static FileHandler preInputHandler;
    static FileHandler badInputHandler;

    @BeforeAll
    public static void init(){
        defaultHandler = new FileHandler();
        preInputHandler = new FileHandler("Project1/testing/csvTestFiles/testCPL.csv");
        badInputHandler = new FileHandler("NonExistentFile.csv");
    }

    @Test
    void FileHandlerDefaultConstructorTest(){
        assertNotNull(defaultHandler);
    }

    @Test
    void FileHandlerConstructorWithFileNameTest(){
        assertNotNull(preInputHandler);
    }

    @Test
    void openFileTest(){
        // Test Null File -- get input
        // fp is the control
        File fp = new File("Project1/testing/csvTestFiles/testCPL.csv");

        // Set System.in to custom stream
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Project1/testing/csvTestFiles/testCPL.csv".getBytes());
        System.setIn(in);

        File fp_from_input = defaultHandler.openFile();
        assertEquals(fp, fp_from_input);

        // reset System.in to its original
        System.setIn(sysInBackup);


        // Test already inputted
        File fp_in = preInputHandler.openFile();
        assertEquals(fp, fp_in);
    }

    @Test
    void openFileNonExistentTest(){
        // This should be handled somehow. Doesn't have to throw an exception,
        // it could catch the exception itself and handle it nicely by
        // asking the user for input until a valid file is found.
        System.out.println(badInputHandler.openFile().exists());
        assertThrows(FileNotFoundException.class, () -> badInputHandler.openFile());
    }
}