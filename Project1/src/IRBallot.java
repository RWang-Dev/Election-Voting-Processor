// inherits from abstract class Ballot
// represents a single ballot in an IR election

import java.util.PriorityQueue;

// TODO:: actually implement
public class IRBallot extends Ballot{
    private PriorityQueue<IRCandidate> candidatesQueue;

    public IRBallot(PriorityQueue<IRCandidate> candidatesQueue){
        this.candidatesQueue = candidatesQueue;
    }

    public void redistributeVote(){
//        PriorityQueue<IRCandidate> temp = new PriorityQueue<>();
//        while(this.candidatesQueue.size() > 1){
//            temp.add(this.candidatesQueue.poll());
//        }
//        this.candidatesQueue.clear();
//        this.candidatesQueue = temp;
        this.candidatesQueue.poll();
    }

    public PriorityQueue<IRCandidate> getCandidatesQueue(){
        return this.candidatesQueue;
    }

}