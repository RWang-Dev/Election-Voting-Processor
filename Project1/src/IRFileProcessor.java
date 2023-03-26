// inherits from abstract class FileProcessor
// Handles processing of a CSV file containing information about an IR election

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.util.Arrays;

public class IRFileProcessor extends FileProcessor{
    public IRFileProcessor(){}

    // reads through CSV file containing CPL election data and returns a corresponding CPLElection object
    public Election processFile(File inputFile){
        Scanner s;
        try {
            s = new Scanner(inputFile);
        }
        catch (FileNotFoundException ex) {
            throw new IllegalArgumentException("ERROR: File not found");
        }
        s.nextLine(); // skip over line specifying election type

        // retrieve information from file, knowing it is formatted as specified in the SRS
        // gets the number of candidates
        int numCands = Integer.parseInt(s.nextLine());
        if( numCands <= 0){
            throw new IllegalArgumentException("Error: There must be at least 1 candidate");
        }

        // Names and parties of the candidates
        String[] candStrings = s.nextLine().split(",");

        // create array of IRCandidate objects for each party in the IR election
        IRCandidate[] candidates = new IRCandidate[numCands];
        for(int i = 0; i<numCands; i++){
            candidates[i] = new IRCandidate(candStrings[i],i);
        }
        // Reads in the number of ballots from the input file
        int numBallots = Integer.parseInt(s.nextLine());
        IRBallot[] ballots = new IRBallot[numBallots];
        for(int i = 0; i<numBallots; i++){
            String[] rankings = s.nextLine().split(",");
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
                if(k==0){
                    currBallot[k].incrementVotes(1);
                    temp.add(currBallot[k]);
                }
                else{temp.add(currBallot[k]);}
            }

            System.out.println("Ballot " + (i+1) + ": " + temp);
            IRBallot resBallot = new IRBallot(temp);
            ballots[i] = resBallot;
        }
        System.out.println();
        s.close();

        int n = candidates.length;
        for (int j = 1; j < n; j++) {
            IRCandidate curr = candidates[j];
            int key = candidates[j].numVotes;
            int i = j - 1;
            while ((i > -1) && (candidates[i].numVotes < key)) {
                candidates[i + 1] = candidates[i];
                i--;
            }
            candidates[i + 1] = curr;
        }

        System.out.println("INITIAL VOTE COUNT: ");
        for (int i = 0; i<candidates.length; i ++){
            System.out.println(candidates[i].getName() + ": " + candidates[i].numVotes);
        }
        return new IRElection(candidates, ballots);
    }
}