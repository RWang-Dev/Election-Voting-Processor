// Handles processing of a CSV file containing information about an IR election

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.util.Arrays;

/**
 * Handles processing of an input CSV file containing information about an IR election
 */
public class IRFileProcessor extends FileProcessor{
    /**
     * Default constructor initializing the IRFileProcessor
     */
    public IRFileProcessor(){}

    /**
     * Reads through CSV files containing IR election data and returns a corresponding IRElection object after
     * parsing the necessary election data from the files.
     * @param inputFiles An array of File objects for the input CSV files containing election information
     * @return An IRElection object with all the candidates and ballots parsed from the files
     */
    public Election processFile(File[] inputFiles){
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

        // create array of IRCandidate objects for each party in the IR election
        IRCandidate[] candidates = new IRCandidate[numCands];
        for(int i = 0; i<numCands; i++){
            candidates[i] = new IRCandidate(candStrings[i],i);
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

        // create array of IRBallot objects
        IRBallot[] ballots = new IRBallot[numBallots];
        int ballot_num = 0;
        for (int i = 0; i < scanners.length; i++){ // loop through each Scanner (each file inputted)
            int file_ballot_num = ballots_per_file[i];
            for (int j = 0; j < file_ballot_num; j++){ // loop through all ballots in that file
                String[] rankings = scanners[i].nextLine().split(",");

                IRCandidate[] currBallot = new IRCandidate[numCands];
                int numVotes = 0;
                for(int k = 0; k < numCands; k++){
                    if(k > rankings.length-1){
                        break;
                    }
                    if((k == numCands-1 && numCands > rankings.length) || rankings[k].equals("")){
                        continue;
                    }
                    int candidateIndex = Integer.parseInt(rankings[k]) - 1;
                    currBallot[candidateIndex] =  candidates[k];
                    numVotes += 1;
                }

                LinkedList<IRCandidate> temp = new LinkedList<>();
                for(int k = 0; k<numVotes; k++){
                    if (k==0){
                        currBallot[k].incrementVotes(1);
                        temp.add(currBallot[k]);
                    }
                    else{temp.add(currBallot[k]);}
                }

                IRBallot resBallot = new IRBallot(temp);
                ballots[ballot_num] = resBallot;
                ballot_num += 1; // increment ballot_num
            }
        }

        // close all scanners
        for (Scanner s : scanners){
            s.close();
        }

        int n = candidates.length;
        for (int j = 1; j < n; j++) {
            IRCandidate curr = candidates[j];
            int key = candidates[j].getNumVotes();
            int i = j - 1;

            //THIS MAY BE WRONG (THE WHILE LOOP CONDITION)
            while ((i > -1) && (candidates[i].numVotes < key)) {
                candidates[i + 1] = candidates[i];
                i--;
            }
            candidates[i + 1] = curr;
        }

        // return the new IRElection object to begin the execution of the IR algorithm
        return new IRElection(candidates, ballots);
    }
}