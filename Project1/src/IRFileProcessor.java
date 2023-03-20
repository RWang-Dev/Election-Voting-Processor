// inherits from abstract class FileProcessor
// Handles processing of a CSV file containing information about an IR election

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class IRFileProcessor extends FileProcessor{
    public IRFileProcessor(){}

    // reads through CSV file containing CPL election data and returns a corresponding CPLElection object
    public Election processFile(File inputFile){
        Scanner s;
        try {
            s = new Scanner(inputFile);
        }
        catch (FileNotFoundException ex) {
            System.out.println("ERROR: File not found");
            return null;
        }
        s.nextLine(); // skip over line specifying election type

        // retrieve information from file, knowing it is formatted as specified in the SRS
        int numCands = Integer.parseInt(s.nextLine());
        String[] candStrings = s.nextLine().split(",");

        // create array of IRCandidate objects for each party in the IR election
        IRCandidate[] candidates = new IRCandidate[numCands];
        for(int i = 0; i<numCands; i++){
            candidates[i] = new IRCandidate(candStrings[i],i);
        }

        int numBallots = Integer.parseInt(s.nextLine());
        IRBallot[] ballots = new IRBallot[numBallots];
        for(int i = 0; i<numCands; i++){
            String[] currBallot = s.nextLine().split(",");
            ballots[i] = new IRBallot(currBallot);
        }
        s.close();

        return null;
    }
    public void countVotes(IRBallot[] ballots){

    }
    public IRCandidate[] rankCandidates(IRCandidate[] candidates){

    }
}