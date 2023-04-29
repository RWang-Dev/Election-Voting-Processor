// author: Alex Iliarski (iliar004)

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.io.File;

/**
 * Handles creating File object and possibly getting filename from user input in console
 */
public class FileHandler{
    private String filename;

    /**
     * Default constructor - used if user doesn't immediately provide filename
     */
    public FileHandler(){
        this.filename = null;
    }

    /**
     * Alternate constructor used when filename provided as run argument
     * @param filename a String representing the name of the file to open
     */
    public FileHandler(String filename){
        this.filename = filename;
    }

    /**
     * Prompts user to enter filename in console, if needed
     */
    public void getFileNameFromInput(){
        Scanner in = new Scanner(System.in);

        // prompt user for input filename
        System.out.println("Enter the name of the CSV file containing ballot information: ");

        // Check that the file entered does indeed exist
        String fname = in.nextLine();
        File fp = new File(fname);
        while (!fp.exists()){
            System.out.println("Filename entered does not exist, try entering again: ");
            fname = in.nextLine();
            fp = new File(fname);
        }

        // assign class variable to user's input
        this.filename = fname;
    }

    /**
     * Returns File object corresponding to filename entered as command line arg / user input
     * @return File object corresponding to filename entered as command line arg / user input
     */
    public File openFile(){
        // if filename wasn't command line arg, prompt user for filename now
        if (this.filename == null){
            getFileNameFromInput();
        }

        File fp = new File(this.filename);

        // Check that filename entered as arg to Main is valid, else prompt for re-entry
        if (!fp.exists()){
            System.out.println("Filename '" + this.filename + "' entered as argument does not exist, try entering again: ");
            getFileNameFromInput();
            fp = new File(this.filename);
        }

        return fp;
    }
}