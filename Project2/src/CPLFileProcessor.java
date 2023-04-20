// Handles processing of a CSV file containing information about a CPL election
// author: Alex Iliarski (iliar004)

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * Handles processing of an input CSV file containing information about a CPL election
 */
public class CPLFileProcessor extends FileProcessor{
    /**
     * Default constructor initializing the CPLFileProcessor
     */
    public CPLFileProcessor(){}

    /**
     * Reads through CSV files containing CPL election data and returns a corresponding CPLElection object after
     * parsing the necessary election data from the files.
     * @param inputFiles A File object for the input CSV files containing CPL election information
     * @return A CPLElection object containing all the information gathered from the input files
     */
    public Election processFile(File[] inputFiles){
        // create Scanner to read file
        Scanner[] scanners = new Scanner[inputFiles.length];
        for (int i = 0; i < scanners.length; i++){
            try {
                scanners[i] = new Scanner(inputFiles[i]);
            }
            catch (FileNotFoundException ex) {
                throw new IllegalArgumentException("Error: Inputted file not found");
            }
            scanners[i].nextLine(); // skip over line specifying election type, since we already know it is CPL
        }

        // retrieve information from first file, knowing it is formatted as specified in the SRS,
        //  and that each file has the same header information
        int numParties = Integer.parseInt(scanners[0].nextLine());
        if(numParties <= 0){
            throw new IllegalArgumentException("Error: There must be at least 1 party");
        }
        String[] partiesStrings = scanners[0].nextLine().split(",");

        // create array of CPLParty objects for each party in the CPL election
        CPLParty[] parties = new CPLParty[numParties];
        for (int i = 0; i < numParties; i++){
            String[] cands = scanners[0].nextLine().split(",");
            parties[i] = new CPLParty(partiesStrings[i], i, cands);
        }

        // get number of seats to be allocated this election
        int numSeats = Integer.parseInt(scanners[0].nextLine());
        if(numSeats <= 0){
            throw new IllegalArgumentException("Error: There must be at least 1 seat");
        }

        // get the rest of the Scanners up to the "ballot line" in the file, skipping header lines
        if (scanners.length > 1){
            for (int i = 1; i < scanners.length; i++){
                int lines_to_skip = numParties + 3;
                for (int j = 0; j < lines_to_skip; j++){
                    scanners[i].nextLine();
                }
            }
        }

        // get total number of ballots in the election and ballots for each file
        int numBallots = 0;
        int[] ballots_per_file = new int[scanners.length];
        for (int i = 0; i < scanners.length; i++){
            int ballots = Integer.parseInt(scanners[i].nextLine());
            numBallots += ballots;
            ballots_per_file[i] = ballots;
        }

        // create array of CPLBallot objects, representing all ballots cast in the CPL election
        CPLBallot[] ballots = new CPLBallot[numBallots];
        int ballot_num = 0;
        for (int i = 0; i < scanners.length; i++){ // loop through each Scanner (each file inputted)
            int file_ballot_num = ballots_per_file[i];
            for (int j = 0; j < file_ballot_num; j++){ // loop through all ballots in that file
                String[] arr = scanners[i].nextLine().split(","); // a single ballot (ex: ["","","1",""])
                int k = -1;
                for (int z = 0; z < arr.length; z++){ // loop through ballot to find vote
                    if (! (arr[z].equals(""))) { // find only slot where it is not ""
                        k = z;
                    }
                }
                ballots[ballot_num] = new CPLBallot(parties[k]); // add new ballot to array, with specified party choice
                ballot_num += 1; // increment ballot_num
            }
        }

        // close all scanners
        for (Scanner s : scanners){
            s.close();
        }

        // return new CPLElection object with all the data retrieved from input file
        return new CPLElection(parties, ballots, numBallots, numSeats);
    }
}