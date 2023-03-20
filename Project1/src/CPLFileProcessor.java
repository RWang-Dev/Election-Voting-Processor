// CPLFileProcessor.java inherits from abstract class FileProcessor
// Handles processing of a CSV file containing information about a CPL election
// author: Alex Iliarski (iliar004)

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * Handles processing of input CSV file containing information about a CPL election
 */
public class CPLFileProcessor extends FileProcessor{
    /**
     * Creates a new CPLFileProcessor object
     */
    public CPLFileProcessor(){}

    /**
     * reads through CSV file containing CPL election data and returns a corresponding CPLElection object
     * @param inputFile A File object for the input CSV file containing CPL election information
     * @return A CPLElection object containing all the information gathered from the input file
     */
    public Election processFile(File inputFile){
        // create Scanner to read file
        Scanner s;
        try {
            s = new Scanner(inputFile);
        }
        catch (FileNotFoundException ex) {
            // TODO:: Modify behavior here?
            System.out.println("ERROR: File not found");
            return null;
        }
        s.nextLine(); // skip over line specifying election type

        // retrieve information from file, knowing it is formatted as specified in the SRS
        int numParties = Integer.parseInt(s.nextLine());
        String[] partiesStrings = s.nextLine().split(",");

        // create array of CPLParty objects for each party in the CPL election
        CPLParty[] parties = new CPLParty[numParties];
        for (int i = 0; i < numParties; i++){
            String[] cands = s.nextLine().split(",");
            parties[i] = new CPLParty(partiesStrings[i], i, cands);
        }

        int numSeats = Integer.parseInt(s.nextLine());
        int numBallots = Integer.parseInt(s.nextLine());

        // create array of CPLBallot objects, representing all ballots cast in the CPL election
        CPLBallot[] ballots = new CPLBallot[numBallots];
        for (int i = 0; i < numBallots; i++){
            String[] arr = s.nextLine().split(","); // a single ballot (ex: ["","","1",""])
            int k = -1;
            for (int j = 0; j < arr.length; j++){ // loop through ballot to find vote
                if (! (arr[j].equals(""))) { // find only slot where it is not ""
                    k = j;
                }
            }
            ballots[i] = new CPLBallot(parties[k]); // add new ballot to array, with specified party choice
        }

        s.close(); // close Scanner

        // return new CPLElection object with all the data retrieved from input file
        CPLElection election = new CPLElection(parties, ballots, numBallots, numSeats);
        return election;
    }
}