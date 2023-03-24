// represents a single IR election and conducts the necessary algorithms

import java.util.ArrayList;

/**
 * An Instant Runoff election. An IRElection object is used to determine the winner in a given IR election.
 */
public class IRElection extends Election{
    private IRCandidate[] rankedCandidates;
    private ArrayList<IRCandidate> eliminatedCandidates;

    /**
     * Creates an instance of IRElection with a given list of rankedCandidates and list of ballots
     * @param rankedCandidates a list of the candidates in the election, in order of the most votes to the least votes
     * @param ballots a list of all the ballots in the election
     */
    public IRElection(IRCandidate[] rankedCandidates, IRBallot[] ballots){
        this.rankedCandidates = rankedCandidates;
        this.ballots = ballots;
        this.eliminatedCandidates = new ArrayList<>();
    }

    /**
     * Gets the number of candidates in the election
     * @return an int representing the number of candidates
     */
    public int getNumCandidates(){
        return this.numVoteables;
    }

    /**
     * Gets the number of ballots in the election
     * @return an int representing the number of ballots cast in the election
     */
    public int getNumBallots(){
        return this.numBallots;
    }

    /**
     * Gets a list of the candidates in the election, in order of the most votes to the least votes
     * @return a list of the candidates
     */
    public Voteable[] getCandidates(){
        return this.rankedCandidates;
    }

    /**
     * Eliminates a candidate after no winner can be determined. The losing candidate is removed from the list
     * of candidates and their votes are redistributed.
     * @param cand the candidate to be eliminated
     */
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

    /**
     * Runs the Instant Runoff election. This involves eliminating a candidate until a candidate receives
     * a majority of the votes, at which point that candidate is the winner of the election.
     */
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

    /**
     * Redistributes an eliminated candidate's votes to the remaining candidates
     * @param candidate the candidate to be eliminated
     */
    private void redistributeVotes(IRCandidate candidate){
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

    /**
     * Rerank the order of the candidates after eliminating one and redistributing their votes
     */
    private void reRankCandidates(){
        int n = rankedCandidates.length;
        for (int j = 1; j < n; j++) {
            IRCandidate curr = rankedCandidates[j];
            int key = rankedCandidates[j].getNumVotes();
            int i = j - 1;
            while ((i > -1) && (rankedCandidates[i].getNumVotes() > key)) {
                rankedCandidates[i + 1] = rankedCandidates[i];
                i--;
            }
            rankedCandidates[i + 1] = curr;
        }
    }

    /**
     * Prints the election results to the screen
     */
    public void printElectionResults(){
        System.out.println("The winner is: "+ rankedCandidates[0].getName());
    }

    /**
     * Produces an audit file in the same directory with the election information and election results
     */
    public void produceAuditFile(){}
}