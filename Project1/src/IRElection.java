// represents a single IR election and conducts the necessary algorithms

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
        this.numBallots = ballots.length;
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
            if(this.rankedCandidates[i].getName().equals(cand.getName())) {
                this.rankedCandidates[i].numVotes = -1;
            }
        }
//        for(int j = 0; j<this.voteables.length; j++){
//            if(this.voteables[j].getName().equals(cand.getName())) {
//                this.voteables[j] = null;
//                this.numVoteables -= 1;
//            }
//        }

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
        System.out.println(numBallots);
        for(int i = 0; i<rankedCandidates.length; i++){
            System.out.println(rankedCandidates[i].getName() + ": " + rankedCandidates[i].numVotes);
        }
        while(this.rankedCandidates[0].getNumVotes() <= total/2) {
//            System.out.println(Arrays.toString(rankedCandidates));

            System.out.println();
            if (this.rankedCandidates[rankedCandidates.length-1].getNumVotes() == this.rankedCandidates[0].getNumVotes()){
                int winner = breakTie(this.rankedCandidates);
                rankedCandidates[0] = rankedCandidates[winner];
                break;
            }
            else{

                eliminateCandidate(rankedCandidates[rankedCandidates.length-(eliminatedCandidates.size() + 1)]);
            }


            for(int i = 0; i<rankedCandidates.length; i++){
                System.out.println(rankedCandidates[i].getName() + ": " + rankedCandidates[i].numVotes);
            }
            for(IRCandidate tempCand : rankedCandidates) {
                tempCand.updateVoteCountHistory();
            }
        }
        System.out.println();
        System.out.println("FINAL VOTES: ");
        for(int i = 0; i<rankedCandidates.length; i++){
            System.out.println(rankedCandidates[i].getName() + ": " + rankedCandidates[i].numVotes);
        }


    }

    /**
     * Redistributes an eliminated candidate's votes to the remaining candidates
     * @param candidate the candidate to be eliminated
     */
    private void redistributeVotes(IRCandidate candidate){
        for(int k = 0; k < this.ballots.length; k++){
//            System.out.println(((IRBallot)ballots[k]).getCandidatesQueue().peek() == null);
//            System.out.println(((IRBallot)ballots[k]).getCandidatesQueue().peek().getName());
            //Checks to see if the current ballot's first choice candidate is the eliminated candidate

            if(((IRBallot)ballots[k]).getCandidatesQueue().peek() != null &&
                    ((IRBallot)ballots[k]).getCandidatesQueue().peek().getName().equals(candidate.getName())){
//                System.out.println(((IRBallot)ballots[k]).getCandidatesQueue());
                // If so, the poll the candidate from the ballot
                ((IRBallot) ballots[k]).redistributeVote(eliminatedCandidates);
//                System.out.println(((IRBallot)ballots[k]).getCandidatesQueue());
                // Then, for each rankedCandidate, check to see if it is equal to the current ballot's second choice, which should
                // now be in the front of the ballot queue after the eliminated candidate is gone
                for(int i = 0; i< rankedCandidates.length; i++){

                    // -1 votes is already an eliminated candidate
                    if(rankedCandidates[i].numVotes == -1){
                        continue;
                    }

                    if(((IRBallot)ballots[k]).getCandidatesQueue().peek() != null &&
                            rankedCandidates[i].getName().equals(((IRBallot)ballots[k]).getCandidatesQueue().peek().getName())){

                        rankedCandidates[i].incrementVotes(1);
                        break;
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
        System.out.println("The winner is: "+ rankedCandidates[0].getName() + " with " + rankedCandidates[0].getNumVotes() + " votes");
    }

    /**
     * Helper function for produceAuditFile(), does brunt of formatting of output txt file
     * @return A String that should be pasted into the output auditfile.txt
     */
    private String produceAuditFileString(){
        String out = "IR election audit file\n";
        out += "Candidates: " + rankedCandidates[0];
        for (int i = 1; i < rankedCandidates.length; i ++){
            out += ", " + rankedCandidates[i];
        }
        out += "\n\nRound 0:\n";

        for (IRCandidate candidate : rankedCandidates) {
            out += "\t" + candidate.getName() + ": " + candidate.getVoteCountHistory().get(0) + "\n";
        }

        for (int i = 0; i < eliminatedCandidates.size(); i++) {
            out += "Round " + (i + 1) + ": " +  eliminatedCandidates.get(i) + " eliminated\n";
            for (IRCandidate candidate : rankedCandidates) {
                out += "\t";
                int voteCount = candidate.getVoteCountHistory().get(i + 1);
                if (voteCount <= 0) {
                    out += "-----\n";
                }
                else {
                    out += candidate.getName() + ": " + candidate.getVoteCountHistory().get(i + 1) + "\n";
                }
            }
        }

        out += "\nWinning Candidate: " + rankedCandidates[0].getName();
        return out;
    }

    /**
     * Produces an audit file storing election results and history of each round of reallocated ballots
     */
    public void produceAuditFile(){
        File f = new File("auditfile.csv");

        try {
            boolean fileCreated = f.createNewFile();
            if (!fileCreated){ // if file already exists, delete it and try to create again
                f.delete();
                f.createNewFile();
            }
        }
        catch (IOException e){ // catches possible IO errors when creating file
            System.out.println("ERROR: IOException");
            return;
        }

        // create FileWriter to write to File
        FileWriter fp;
        try {
            fp = new FileWriter("auditfile.txt", false);
        }
        catch(IOException e){
            System.out.println("ERROR: Unable to write to file");
            return;
        }

        String out = produceAuditFileString();

        try {
            fp.write(out); // write output string to file
            fp.close(); // close file
        }
        catch (IOException e) {
            System.out.println("ERROR: writing to file failed, IOException");
            return;
        }
    }
}