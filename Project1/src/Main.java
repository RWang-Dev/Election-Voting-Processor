// author: Alex Iliarski (iliar004)

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.Arrays;

/**
 * Main runner file for the program
 */
//>>>>>>> 996c0d39c3019428a6e00544eed278b4dbe08939
public class Main{
    /**
     * main() of the program
     * @param args command line arguments to program
     */
    public static void main(String[] args){

        FileHandler fh;

        // create FileHandler object based on if filename is input to command line or not
        if (args == null || args.length == 0){
            fh = new FileHandler();
        }
        else {
            fh = new FileHandler(args[0]);
        }

        // retrieve file object for the input file
        File fp = fh.openFile();

        // create Scanner to read File
        Scanner scnr;
        try {
            scnr = new Scanner(fp);
        }
        catch (FileNotFoundException ex) {
            // TODO:: Modify behavior here?
            System.out.println("ERROR: File not found");
            return;
        }

        // Note: This is a slight deviation from our SDD
        // create FileProcessor object, based on reading first line of input File
        FileProcessor fileP;
        String firstLine = scnr.nextLine();
        if (firstLine.equals("IR")){
            fileP = new IRFileProcessor();
        }
        else if (firstLine.equals("CPL")){
            fileP = new CPLFileProcessor();
        }
        else { // TODO:: can change how we handle this error
            System.out.println("ERROR: File incorectly formatted");
            return;
        }
        scnr.close(); // close Scanner, rest of file will be read later

        // read and process file to create corresponding Election object with all the data
        Election currentElection = fileP.processFile(fp);

        // run election algorithms
        currentElection.runElection();

        // print election results and create Audit file with election information
        currentElection.printElectionResults();
        currentElection.produceAuditFile();
    }
}
