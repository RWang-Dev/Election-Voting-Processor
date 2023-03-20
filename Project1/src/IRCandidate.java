// inherits from abstract class Voteable
// represents a candidate object in an IR election

import java.util.ArrayList;
// TODO:: implement
public class IRCandidate extends Voteable{
    private ArrayList<Integer> voteCountHistory;

    public IRCandidate(String name, int ID){
        this.name = name;
        this.ID = ID;
    }

    public void updateVoteCountHistory(){
        voteCountHistory.add(this.numVotes);
    }
    public ArrayList<Integer> getVoteCountHistory(){
        return this.voteCountHistory;
    }

//    public int compareTo(IRCandidate otherCandidate){
//        return this.numVotes - otherCandidate.numVotes;
//    }
}
