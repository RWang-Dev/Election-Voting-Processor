// Created by
// represents a single IR election and conducts the necessary algorithms

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Popularity Only election. A POElection object is used to determine the winner in a given PO election.
 */
public class POElection extends Election{
    POCandidate[] candidates;

    /**
     * Creates an instance of a POElection
     * @param cands Array of POCandidate objects representing the candidates in the election
     * @param numBallots Int number of ballots cast in the election
     */
    public POElection(POCandidate[] cands, int numBallots){
        this.candidates = cands;
        this.numBallots = numBallots;

        this.voteables = cands;
        this.numVoteables = cands.length;
        this.ballots = null; // A PO Election is simple enough that storing ballots is unnecessary
    }

    /**
     * Determines the winner of the PO election, conducting all necessary tie breaking.
     * Sets the winning candidates isWinner field to true
     */
    public void determineWinner(){
        int idxOfMax = -1;
        int maxVotes = -1;
        boolean isTied = false;
        ArrayList<Integer> tieForMax = new ArrayList<>(); // tracks indices of tied candidates for maxvotes, in case of tie
        for (int i = 0; i < candidates.length; i++){
            int votes = candidates[i].getNumVotes();
            if (votes > maxVotes){
                idxOfMax = i;
                maxVotes = votes;
                tieForMax.clear(); // clear tieformax, since we have a new max
                isTied = false;
                tieForMax.add(i);
            }
            else if (votes == maxVotes) { // Tie for Max situation
                isTied = true;
                tieForMax.add(i);
            }
        }

        // resolve breakTie if necesesary
        POCandidate[] tieForMaxArray = new POCandidate[tieForMax.size()];
        for(int i = 0; i < tieForMax.size(); i++){
            tieForMaxArray[i] = candidates[tieForMax.get(i)];
        }

        if (isTied){ // if there is a tie for the max, then must break tie to determine winner of seat
            idxOfMax = tieForMax.get(breakTie( tieForMaxArray));
        }

        candidates[idxOfMax].SetToWinner();
    }

    /**
     * Sorts the POCandidates in sorted descending order, with the winner at candidates[0]
     */
    public void sortCandidates(){
        // use an insertion sort technique to sort candidates in place (https://www.geeksforgeeks.org/insertion-sort/)
        int n = candidates.length;
        for (int i = 1; i < n; ++i) {
            POCandidate key = candidates[i];
            int j = i - 1;

            /* Move elements of candidates[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            // uses the overwritten compareTo() method
            while (j >= 0 && candidates[j].compareTo(key) < 0) {
                candidates[j + 1] = candidates[j];
                j = j - 1;
            }
            candidates[j + 1] = key;
        }

    }

    /**
     * Runs the PO election algorithm, determining the winner(s)
     */
    public void runElection(){
        determineWinner(); // sets the isWinner attribute of the winning POCandidate to true

        sortCandidates(); // sort the POCandidates descending order, since we have now determined the winner
    }

    /**
     * Produces the results of the PO election in the form of a String
     * @return a String representing the results of the PO election
     */
    public String produceResultsString(){
        String out = "";
        out += "The winner is: " + candidates[0].getName() + "\n";
        for (POCandidate cand : candidates){ // loop through each candidate in results[] and print it
            double percent = 100 * ((double) cand.getNumVotes() / numBallots);
            out += (cand.getName() + " with " + String.valueOf(percent) + "% of the vote.\n");
        }
        return out;
    }


    /**
     * Prints the results of the PO election to the console
     */
    public void printElectionResults(){
        System.out.println(produceResultsString());
    }

    /**
     * Creates the auditfile.txt file in the same directory, containing the results of the PO election
     */
    public void produceAuditFile(){
        File f = new File("auditfile.csv");

        try {
            boolean fileCreated = f.createNewFile();
            if (!fileCreated){ // if file already exists, delete it and try to create again
                f.delete();
                f.createNewFile();
            }
        }
        catch (IOException e){ // catches possible IO errors when creating file
            System.out.println("ERROR: IOException");
            return;
        }

        // create FileWriter to write to File
        FileWriter fp;
        try {
            fp = new FileWriter("auditfile.txt", false);
        }
        catch(IOException e){
            System.out.println("ERROR: Unable to write to file");
            return;
        }

        String out = produceResultsString();

        try {
            fp.write(out); // write output string to file
            fp.close(); // close file
        }
        catch (IOException e) {
            System.out.println("ERROR: writing to file failed, IOException");
            return;
        }
    }
}
