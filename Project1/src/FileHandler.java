// class handles opening file and possibly getting file from user input

import java.util.Scanner;
import java.io.File;

public class FileHandler{
    private String filename;

    // default constructor if user doesn't immediately provide filename
    public FileHandler(){
        this.filename = null;
    }

    // assign filename if user provides it as argument
    public FileHandler(String filename){
        this.filename = filename;
    }

    // prompts user to enter filename in console
    public void getFileNameFromInput(){
        Scanner in = new Scanner(System.in);

        // prompt user for input filename
        System.out.println("Enter the name of the CSV file containing ballot information: ");

        // assign class variable to user's input
        this.filename = in.nextLine();
    }

    // returns File object corresponding to filename entered as command line arg or from user prompt
    public File openFile(){
        // if filename wasn't command line arg, prompt user for filename now
        if (this.filename == null){
            getFileNameFromInput();
        }

        File fp = fileopen(this.filename);
        return fp;
    }
}