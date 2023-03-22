// inherits from abstract class Election
// represents a single IR election and conducts the necessary algorithms

import java.util.ArrayList;
public class IRElection extends Election{
    private IRCandidate[] rankedCandidates;
    private ArrayList<IRCandidate> eliminatedCandidates;

    public IRElection(IRCandidate[] rankedCandidates, IRBallot[] ballots){
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

    public Voteable[] getCandidates(){
        return this.rankedCandidates;
    }

    public void eliminateCandidate(IRCandidate cand){
        this.eliminatedCandidates.add(cand);

        for(int i = 0; i<this.rankedCandidates.length; i++){
            if(this.rankedCandidates[i] == cand) {
                this.rankedCandidates[i] = null;
                //rerank Candidates
            }
        }
        for(int j = 0; j<this.voteables.length; j++){
            if(this.voteables[j] == cand) {
                this.voteables[j] = null;
                this.numVoteables -= 1;
            }
        }

        // RECOUNT THE BALLOTS
        this.redistributeVotes(cand);
        this.reRankCandidates();
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


    public void runElection(){
        // Initial input for BallotHistory - for audit file
        for(IRCandidate tempCand : rankedCandidates) {
            tempCand.updateVoteCountHistory();
        }
        int total = this.getNumBallots();
        while(this.rankedCandidates[0].getNumVotes() < total/2) {
            if (this.rankedCandidates[rankedCandidates.length-1].getNumVotes() == this.rankedCandidates[0].getNumVotes()){
                int winner = breakTie(this.rankedCandidates);
                rankedCandidates[0] = rankedCandidates[winner];
                break;
            }
            else{
                eliminateCandidate(rankedCandidates[rankedCandidates.length-1]);
            }
        }


    }

    public void redistributeVotes(IRCandidate candidate){
        for(IRBallot ballot : (IRBallot[]) this.ballots){
            if(ballot.getCandidatesQueue().peek() == candidate){
                ballot.redistributeVote();
                for(IRCandidate tempCand : this.rankedCandidates){
                    if(tempCand == ballot.getCandidatesQueue().peek()){
                        tempCand.incrementVotes(1);
                    }
                }

            }
        }
    }

    public void printElectionResults(){
        System.out.println("The winner is: "+ rankedCandidates[0]);
    }

    public void produceAuditFile(){}
}