import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {
    static FileHandler defaultHandler;
    static FileHandler preInputHandler;
    static FileHandler badInputHandler;

    @BeforeAll
    public static void init(){
        defaultHandler = new FileHandler();
        preInputHandler = new FileHandler("Project1/testing/csvTestFiles/testCPL.csv");
        badInputHandler = new FileHandler("NonExistentFile1.csv");
    }

    @Test
    void FileHandlerDefaultConstructorTest(){
        assertNotNull(defaultHandler);
    }

    @Test
    void FileHandlerConstructorWithFileNameTest(){
        assertNotNull(preInputHandler);
    }


    //THIS TEST IS CURRENTLY FAILING. IT HAS TO DO WITH THE FACT
    //THAT WHEN WE TRY TO OPEN A FILE IT AUTOMATICALLY APPENDS
    //"Project1/src/" ONTO THE BEGINNING OF THE STRING, MAKING
    //IT IMPOSSIBLE TO OPEN ANY FILE IN ANY OTHER FOLDER
    @Test
    void openFileTest(){
        // Test Null File -- get input
        // fp is the control
        File fp = new File("Project1/testing/csvTestFiles/testCPL.csv");

        // Test already inputted
        File fp_in = preInputHandler.openFile();
        assertEquals(fp, fp_in);

        // Set System.in to custom stream
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("testCPL.csv".getBytes());
        System.setIn(in);

        File fp_from_input = defaultHandler.openFile();
        System.out.println(fp_from_input);

        // reset System.in to its original
        System.setIn(sysInBackup);
    }
}