// represents a candidate object in a PO election

/**
 * A candidate in a Popularity Only election. POCandidates are used in POElections to determine the winner.
 */
public class POCandidate extends Voteable implements Comparable<POCandidate>{
    boolean isWinner;

    /**
     * Creates a new POCandidate instance with the given name and ID
     * @param name The name of the candidate
     * @param ID An int representing the unique ID of the candidate
     */
    public POCandidate(String name, int ID){
        this.name = name;
        this.ID = ID;
        this.numVotes = 0;
        this.isWinner = false;
    }

    /**
     * Sets this POCandidate to be the winner, setting isWinner attribute to true
     */
    public void SetToWinner(){
        this.isWinner = true;
    }

    /**
     * Returns whether this POCandidate is the winner
     * @return boolean representing whether this POCandidate is the winner
     */
    public boolean getIsWinner(){
        return this.isWinner;
    }



    /**
     * Compares two POCandidate objects to determine rankings
     * @param o representing the other candidate that this current candidate is being compared to
     * @return int representing whether this POCandidate is "larger" than the other
     */
    @Override
    public int compareTo(POCandidate o){
        if (this.numVotes > o.getNumVotes()){
            return 1;
        }
        else if (this.numVotes < o.getNumVotes()){
            return -1;
        }
        else { //tied amount of votes, if one candidate is winner that is a tie-break condition
            if (this.isWinner){
                return 1;
            }
            else if (o.getIsWinner()){
                return -1;
            }
            else{
                return 0;
            }

        }
    }

}
