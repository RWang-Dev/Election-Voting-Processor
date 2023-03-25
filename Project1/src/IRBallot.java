// represents a single ballot in an IR election

import java.util.ArrayList;
import java.util.LinkedList;


// TODO:: actually implement
/**
 * A cast ballot in an Instant Runoff election. Contains data about which candidates were voted for and the order
 * in which they were voted for.
 */
public class IRBallot extends Ballot{
    private LinkedList<IRCandidate> candidatesQueue;

    public IRBallot(LinkedList<IRCandidate> candidatesQueue){
        this.candidatesQueue = candidatesQueue;
    }

    /**
     * Changes the ballot's vote from the current top candidate to the next choice candidate by removing
     * the top candidate from the queue
     */
    public void redistributeVote(ArrayList<IRCandidate> eliminatedCandidiates){
        System.out.println("candidatesQueue.poll");

//        this.candidatesQueue.poll();
        System.out.println(candidatesQueue);
        while(eliminatedCandidiates.contains(candidatesQueue.peek())){
            this.candidatesQueue.poll();
        }
        System.out.println(candidatesQueue);

    }

    public LinkedList<IRCandidate> getCandidatesQueue(){
        return this.candidatesQueue;
    }

}