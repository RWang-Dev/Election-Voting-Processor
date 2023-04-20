// author: Alex Iliarski (iliar004)

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
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
        FileHandler[] fh_list;

        // create list of FileHandler object based on if filename is input to command line or not
        if (args == null || args.length == 0){
            // prompt user for number of input filenames
            Scanner in = new Scanner(System.in);
            System.out.println("Enter the number of files you wish to process for the election: ");
            int num_files = Integer.parseInt(in.nextLine());

            fh_list = new FileHandler[num_files];
            for (int i = 0; i < num_files; i++){
                fh_list[i] = new FileHandler();
            }
//            fh = new FileHandler();
        }
        else {
            fh_list = new FileHandler[args.length];
            for (int i = 0; i < args.length; i++){
                fh_list[i] = new FileHandler(args[i]);
            }
           // fh = new FileHandler(args[0]);
        }

        // retrieve file objects for the input files
        File[] fp_list = new File[fh_list.length];
        for (int i = 0; i < fp_list.length; i++){
            fp_list[i] = fh_list[i].openFile();
        }
//        File fp = fh.openFile();

        // create Scanner to read first File, to determine the type of election to be run
        Scanner scnr;
        try {
            scnr = new Scanner(fp_list[0]);
        }
        catch (FileNotFoundException ex) {
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
//        else if (firstLine.equals("PO")){
//            fileP = new POFileProcessor();
//        }
        else {
            System.out.println("ERROR: File incorrectly formatted");
            return;
        }
        scnr.close(); // close Scanner, rest of file will be read later

        // read and process files to create corresponding Election object with all the data
        Election currentElection = fileP.processFile(fp_list);

        // run election algorithms
        currentElection.runElection();


        // print election results and create Audit file with election information
        currentElection.printElectionResults();
        currentElection.produceAuditFile();
    }
}
