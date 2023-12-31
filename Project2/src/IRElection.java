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
    private int numValidCandidates;

    /**
     * Creates an instance of IRElection with a given list of rankedCandidates and list of ballots
     * @param rankedCandidates a list of the candidates in the election, in order of the most votes to the least votes
     * @param ballots a list of all the ballots in the election
     */
    public IRElection(IRCandidate[] rankedCandidates, IRBallot[] ballots){
        this.rankedCandidates = rankedCandidates;
        this.numVoteables = rankedCandidates.length;
        this.numValidCandidates = rankedCandidates.length;
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
     */
    public void eliminateCandidate(){
        if(numValidCandidates <= 1){
            throw new IllegalCallerException("Can't eliminate candidate" +
                    " if one or less candidates.");
        }
        IRCandidate cand = rankedCandidates[numValidCandidates - 1];
        this.eliminatedCandidates.add(cand);

        for(int i = 0; i<this.rankedCandidates.length; i++){
            if(this.rankedCandidates[i].getName().equals(cand.getName())) {
                this.rankedCandidates[i].numVotes = -1;
            }
        }

        numValidCandidates -= 1;
        this.redistributeVotes(cand);
        this.reRankCandidates();
    }

    private void updateVoteCountHistories(){
        for(IRCandidate tempCand : rankedCandidates) {
            tempCand.updateVoteCountHistory();
        }
    }

    /**
     * Runs the Instant Runoff election. This involves eliminating a candidate until a candidate receives
     * a majority of the votes, at which point that candidate is the winner of the election.
     */
    public void runElection(){
        // Initial input for BallotHistory - for audit file
        updateVoteCountHistories();
        reRankCandidates();
        int total = this.getNumBallots();

        while(this.rankedCandidates[0].getNumVotes() <= total/2) {
            if(numValidCandidates <= 1){
                break;
            }
            else{
                eliminateCandidate();
            }
            updateVoteCountHistories();
        }
    }

    /**
     * Redistributes an eliminated candidate's votes to the remaining candidates
     * @param candidate the candidate to be eliminated
     */
    private void redistributeVotes(IRCandidate candidate){
        for(int k = 0; k < this.ballots.length; k++){
            //Checks to see if the current ballot's first choice candidate is the eliminated candidate
            if(((IRBallot)ballots[k]).getCandidatesQueue().peek() != null &&
                    ((IRBallot)ballots[k]).getCandidatesQueue().peek().getName().equals(candidate.getName())){
                // If so, the poll the candidate from the ballot
                ((IRBallot) ballots[k]).redistributeVote(eliminatedCandidates);
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
            while ((i > -1) && (rankedCandidates[i].getNumVotes() < key)) {
                rankedCandidates[i + 1] = rankedCandidates[i];
                i--;
            }
            rankedCandidates[i + 1] = curr;
        }
        rankedCandidates = breakTies(rankedCandidates);
    }

    private IRCandidate[] breakTies(IRCandidate[] candidates){
        //Checks for ties at the lower end, randomly selects bottom one
        int num_ties = 0;
        int lowestCandidateNum = candidates[numValidCandidates - 1].getNumVotes();
        for(int i = numValidCandidates - 1; i >= 0; i--){
            if(candidates[i].getNumVotes() == lowestCandidateNum){
                num_ties++;
            }
        }
        if(num_ties > 1){
            candidates = rerank_n(candidates, num_ties);
        }
        return candidates;
    }

    private IRCandidate[] rerank_n(IRCandidate[] cands, int num_ties){
        IRCandidate[] temp_candidates = new IRCandidate[num_ties];
        for (int i = 0; i < num_ties; i++){
            temp_candidates[i] = new IRCandidate("name" + i, i);
        }
        int random_pos = breakTie(temp_candidates);

        IRCandidate temp = cands[numValidCandidates - 1];
        cands[numValidCandidates - 1] = cands[numValidCandidates - 1 - random_pos];
        cands[numValidCandidates - 1 - random_pos] = temp;
        return cands;
    }

    /**
     * Prints the election results to the screen
     */
    public void printElectionResults(){
        String out = "IR ELECTION RESULTS:" + "\n\n" + "Total ballots cast: " + numBallots + "\n";
        String lineOfDashes = "-".repeat(20 + ((eliminatedCandidates.size() + 1) * 17)) + "\n";

        String columnHeaders = String.format("%-19s|", "     Candidate");
        for (int i = 0; i < eliminatedCandidates.size() + 1; i++) {
            columnHeaders += String.format("%-16s|", "     Round " + (i + 1));
        }

        columnHeaders += "\n" + String.format("%-19s|", "") ;
        for (int i = 0; i < eliminatedCandidates.size() + 1; i++) {
            columnHeaders += String.format("%-7s| %-7s|", " Votes", "  +-");
        }

        out += lineOfDashes + columnHeaders + "\n" + lineOfDashes;

        ArrayList<Integer> exhaustedPileList = new ArrayList<>();
        for (int i = 0; i < eliminatedCandidates.size() + 1; i++) {
            exhaustedPileList.add(0);
        }
        for (IRCandidate candidate : rankedCandidates) {
            String candidateVoteData = String.format("%-19s", candidate.getName()) + "|";;
            int voteCount;
            int prevVoteCount;
            for (int i = 0; i < eliminatedCandidates.size() + 1; i++) {
                voteCount = candidate.getVoteCountHistory().get(i);
                if (voteCount == -1) {      // Account for votes being set to -1 when candidate is eliminated
                    voteCount = 0;
                }
                if (i == 0) {
                    prevVoteCount = 0;
                }
                else {
                    if (candidate.getVoteCountHistory().get(i - 1) == -1) {     // -1 means that the candidate has already been eliminated
                        candidateVoteData += String.format("%-16s|", "");
                        break;
                    }
                    prevVoteCount = candidate.getVoteCountHistory().get(i - 1);
                }

                int voteCountDiff = voteCount - prevVoteCount;
                String voteCountDiffStr;
                if (voteCountDiff < 0) {
                    voteCountDiffStr = Integer.toString(voteCountDiff);
                }
                else {
                    voteCountDiffStr = "+" + voteCountDiff;
                }
                candidateVoteData += String.format(" %-6s| %-7s|", voteCount, voteCountDiffStr);
                exhaustedPileList.set(i, exhaustedPileList.get(i) + voteCountDiff);
            }
            out += candidateVoteData + "\n";
        }
        String exhaustedPileString = String.format("%-19s|", "EXHAUSTED PILE");
        for (int i = 0; i < exhaustedPileList.size(); i++) {
            if (i == 0) {
                exhaustedPileList.set(0, 0);
                exhaustedPileString += String.format(" %-6s| %-7s|", 0, "+0");
            }
            else {
                int exhaustedPileDiff = exhaustedPileList.get(i) - exhaustedPileList.get(i - 1);
                String exhaustedPileDiffStr;
                if (exhaustedPileDiff >= 0) {
                    exhaustedPileDiffStr = "+" + exhaustedPileDiff;
                }
                else {
                    exhaustedPileDiffStr = String.valueOf(exhaustedPileDiff);
                }
                exhaustedPileString += String.format(" %-6s| %-7s|", exhaustedPileList.get(i), exhaustedPileDiffStr);
            }
        }
        out += exhaustedPileString + "\n";
        out += lineOfDashes;

        System.out.println(out);
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