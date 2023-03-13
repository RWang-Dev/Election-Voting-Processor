// main class, runs program

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        FileHandler fh;

        // create FileHandler object based on if filename is input to command line or not
        if (args == null){
            fh = new FileHandler();
        }
        else {
            fh = new FileHandler(args[0]);
        }

        // open file
        File fp = fh.openFile();

        // Note: This is a slight deviation from our SDD
        // create FileProcessor object, based on reading first line of input File
        FileProcessor fileP;
        Scanner scnr = new Scanner(fp);
        String firstLine = scnr.nextLine();
        if (firstLine.equals("IR")){
            fileP = new IRFileProcessor();
        }
        else if (firstLine.equals("CPL")){
            fileP = new CPLFileProcessor();
        }
        else { // TODO:: can change how we handle this error
            System.out.println("File incorectly formatted");
            return;
        }
        scnr.close(); // close Scanner

        // process file and create corresponding Election object with all the data
        Election currentElection = fileP.processFile(fp);

        // run election algorithms
        currentElection.runElection();

        // print election results and create Audit file with election information
        currentElection.printElectionResults();
        currentElection.produceAuditFile();
    }
}
