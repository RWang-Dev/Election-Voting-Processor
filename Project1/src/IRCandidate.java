// represents a candidate object in an IR election

import java.util.ArrayList;

/**
 * A candidate in an Instant Runoff election. IRCandidates are used in IRElections to determine the winner.
 */
public class IRCandidate extends Voteable implements Comparable<IRCandidate>{
    private ArrayList<Integer> voteCountHistory;

    /**
     * Creates a new IRCandidate instance with the given name and ID
     * @param name The name of the candidate
     * @param ID An int representing the unique ID of the party
     */
    public IRCandidate(String name, int ID){
        this.name = name;
        this.ID = ID;
        this.numVotes = 0;
        this.voteCountHistory = new ArrayList<>();
    }

    /**
     * Updates the vote count history by adding the current number of votes to the history
     */
    public void updateVoteCountHistory(){
        voteCountHistory.add(this.numVotes);
    }


    /**
     * Gets the vote count history
     * @return an ArrayList representing the vote count history and recording the number of votes each
     * time votes were redistributed after eliminating a candidate
     */
    public ArrayList<Integer> getVoteCountHistory(){

        return this.voteCountHistory;
    }


    public int compareTo(IRCandidate other){
        return 1;
    }

    public String toString(){
        return this.getName();
    }
}
