package TrustyVoteCounter;// FileHandler.java, handles creating File object and possibly getting filename from user input
// author: Alex Iliarski (iliar004)

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
     * @param filename
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

        // assign class variable to user's input
        this.filename = in.nextLine();
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
        return fp;
    }
}