// inherits from abstract class Election
// represents a single IR election and conducts the necessary algorithms

import java.util.ArrayList;
public class IRElection extends Election{
    private IRCandidate[] rankedCandidates;
    private ArrayList<IRCandidate> eliminatedCandidates;

    public IRElection(IRCandidate[] rankedCandidates, Ballot[] ballots){
        this.rankedCandidates = rankedCandidates;
        this.ballots = ballots;
        this.eliminatedCandidates = new ArrayList<>();
    }

    public int getNumCandidates(){
        return this.numVoteables;
    }

    public int getNumBallots(){
        return this.numBallots;
    }

    public void eliminateCandidate(IRCandidate cand){
        for(int i = 0; i<rankedCandidates.length; i++){
            if(rankedCandidates[i] == cand) {
                rankedCandidates[i] = null;
                //rerank Candidates
                eliminatedCandidates.add(cand);
            }
        }
        for(int j = 0; j<voteables.length; j++){
            if(voteables[j] == cand) {
                voteables[j] = null;
            }
        }
    }
    public void reRankCandidates(){
        int n = rankedCandidates.length;
        for (int j = 1; j < n; j++) {
            IRCandidate curr = rankedCandidates[j];
            int key = rankedCandidates[j].numVotes;
            int i = j - 1;
            while ((i > -1) && (rankedCandidates[i].numVotes > key)) {
                rankedCandidates[i + 1] = rankedCandidates[i];
                i--;
            }
            rankedCandidates[i + 1] = curr;
        }
    }
    public Voteable[] getCandidates(){
        return this.voteables;
    }

    public void runElection(){}

    public void redistributeVotes(IRCandidate cand){

    }

    public void printElectionResults(){}

    public void produceAuditFile(){}
}