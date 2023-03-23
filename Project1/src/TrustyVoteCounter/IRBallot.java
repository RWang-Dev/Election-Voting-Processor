package TrustyVoteCounter;// inherits from abstract class Ballot
// represents a single ballot in an IR election

import java.util.PriorityQueue;

// TODO:: actually implement

/**
 * A cast ballot in an Instant Runoff election. Contains data about which candidates were voted for and the order
 * in which they were voted for.
 */
public class IRBallot extends Ballot{
    private PriorityQueue<IRCandidate> candidatesQueue;

    public IRBallot(PriorityQueue<IRCandidate> candidatesQueue){
        this.candidatesQueue = candidatesQueue;
    }

    /**
     * Changes the ballot's vote from the current top candidate to the next choice candidate by removing
     * the top candidate from the queue
     */
    public void redistributeVote(){
//        PriorityQueue<IRCandidate> temp = new PriorityQueue<>();
//        while(this.candidatesQueue.size() > 1){
//            temp.add(this.candidatesQueue.poll());
//        }
//        this.candidatesQueue.clear();
//        this.candidatesQueue = temp;
        this.candidatesQueue.poll();
    }

    /**
     * Gets the queue of candidates, with the highest priority candidate at the front of the queue
     * @return  candidates queue
     */
    public PriorityQueue<IRCandidate> getCandidatesQueue(){
        return this.candidatesQueue;
    }

}