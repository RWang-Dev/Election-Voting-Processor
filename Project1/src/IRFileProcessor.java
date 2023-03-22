// inherits from abstract class FileProcessor
// Handles processing of a CSV file containing information about an IR election

import java.io.FileNotFoundException;
import java.util.PriorityQueue;
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
        for(int i = 0; i<numBallots; i++){
            String[] rankings = s.nextLine().split(",");
            String[] currBallot = new String[numCands];
            for(int k = 0; k < numCands; k++){
                currBallot[Integer.parseInt(rankings[k])] = candStrings[k];
            }

            PriorityQueue<IRCandidate> temp = new PriorityQueue<>();
            for(String str : currBallot){
                IRCandidate curr = new IRCandidate(str, i);
                temp.add(curr);
            }
            IRBallot resBallot = new IRBallot(temp);
            ballots[i] = resBallot;

        }
        s.close();

        int n = candidates.length;
        for (int j = 1; j < n; j++) {
            IRCandidate curr = candidates[j];
            int key = candidates[j].numVotes;
            int i = j - 1;
            while ((i > -1) && (candidates[i].numVotes > key)) {
                candidates[i + 1] = candidates[i];
                i--;
            }
            candidates[i + 1] = curr;
        }
        IRElection res = new IRElection(candidates, ballots);
        return res;
    }
    public void countVotes(IRBallot[] ballots){
    }
    public IRCandidate[] rankCandidates(IRCandidate[] candidates){
        return null;
    }
}