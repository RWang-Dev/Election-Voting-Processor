// Handles processing of a CSV file containing information about a PO election
// author: Alex Iliarski (iliar004)

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * Handles processing of an input CSV file containing information about a PO election
 */
public class POFileProcessor extends FileProcessor{

    /**
     * Reads through a PO file containing PO election data and returns a corresponding POElection object after
     * parsing the necessary election data from the files.
     * @param inputFiles A File object for the input CSV files containing PO election information
     * @return A POElection object containing all the information gathered from the input files
     */
    public Election processFile(File[] inputFiles) {
        Scanner[] scanners = new Scanner[inputFiles.length]; //Scanners for every input file
        for (int i = 0; i < scanners.length; i++){
            try {
                scanners[i] = new Scanner(inputFiles[i]);
            }
            catch (FileNotFoundException ex) {
                throw new IllegalArgumentException("Error: Inputted file not found");
            }
            scanners[i].nextLine(); // skip over line specifying election type, since we already know it is CPL
        }

        // retrieve information from file, knowing it is formatted as specified in the SRS
        // knowing the header information will be the same for all files, we arbitrarily use scanners[0]
        // gets the number of candidates
        int numCands = Integer.parseInt(scanners[0].nextLine());
        if( numCands <= 0){
            throw new IllegalArgumentException("Error: There must be at least 1 candidate");
        }

        // Names and parties of the candidates
        String[] candStrings = scanners[0].nextLine().split(",");

        // create array of POCandidate objects for each candidate in the PO election
        POCandidate[] candidates = new POCandidate[numCands];
        for(int i = 0; i<numCands; i++){
            candidates[i] = new POCandidate(candStrings[i],i);
        }

        // get the rest of the Scanners up to the "ballot line" in the file, skipping header lines
        if (scanners.length > 1){
            for (int i = 1; i < scanners.length; i++){
                int lines_to_skip = 2;
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

        int[] votesPerCand = new int[numCands];
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
                votesPerCand[k] += 1;
            }
        }

        // updaate each candidate's vote amount by number stored to votesPerCand
        for (int i = 0; i < votesPerCand.length; i++){
            candidates[i].incrementVotes(votesPerCand[i]);
        }

        // close all scanners
        for (Scanner s : scanners){
            s.close();
        }

        // return new POElection object with all the data retrieved from input file
        return new POElection(candidates, numBallots);
    }
}
