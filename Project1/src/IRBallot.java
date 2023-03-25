// inherits from abstract class Ballot
// represents a single ballot in an IR election

import java.util.ArrayList;
import java.util.LinkedList;

public class IRBallot extends Ballot{
    private LinkedList<IRCandidate> candidatesQueue;

    public IRBallot(LinkedList<IRCandidate> candidatesQueue){
        this.candidatesQueue = candidatesQueue;
    }

    public void redistributeVote(ArrayList<IRCandidate> eliminatedCandidiates){
        System.out.println("candidatesQueue.poll");

        this.candidatesQueue.poll();

        while(eliminatedCandidiates.contains(candidatesQueue.peek())){
            this.candidatesQueue.poll();
        }

    }

    public LinkedList<IRCandidate> getCandidatesQueue(){
        return this.candidatesQueue;
    }

}