// inherits from abstract class Election
// author: Alex Iliarski (iliar004)

import java.lang.Math;
import java.io.File;
import java.io.FileWriter; // TODO:: may need to add to UML class diagram?
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Closed Party list election. A CPLElection object is used to determine the winner in a given CPL election.
 */
public class CPLElection extends Election {
    private CPLParty[] parties;
    private int numSeats;
    private String[] results;
    private int totalNumCandidates;

    /**
     * Creates a CPLElection object and assigns values to instance variables
     * @param parties A CPLParty[] of the parties to vote for in a CPL election
     * @param ballots A CPLBallot[] of all the ballots cast in a CPL election
     * @param numballots An int representing the number of ballots cast in a CPL election
     * @param numSeats An int representing the number of seats to be filled in the CPL election
     */
    public CPLElection(CPLParty[] parties, CPLBallot[] ballots, int numballots, int numSeats){
        this.parties = parties;
        this.voteables = parties;
        this.numVoteables = parties.length;
        this.ballots = ballots;
        this.numBallots = numballots;
        this.numSeats = numSeats;
        this.results = new String[numSeats];

        for(var party: parties){
            totalNumCandidates += party.getNumPartyCandidates();
        }
    }

    /**
     * Gets the number of seats to be filled in the CPL election
     * @return An int representing the number of seats to be filled in the CPL election
     */
    public int getNumSeats() {
        return numSeats;
    }

    /**
     * Gets the names of candidates that won seats in the CPL election
     * @return A String[] of the names of candidates that won seats in the CPL election
     */
    public String[] getResults() {
        return results;
    }

    // assigns every value in input array to null

    /**
     * Assigns every value in the input array to null
     * @param arr A CPLParty[] array of all null values
     */
    public void clearArray(CPLParty[] arr){
        for (int i = 0; i < arr.length; i++){
            if (arr[i] != null){
                arr[i] = null;
            }
        }
    }

    /**
     * Conducts the first allocation of seats in a CPL election, that can be assigned solely by meeting the quota
     * @param quota An int representing the number of votes needed to automatically be assigned a seat
     * @param seatsAllocated An int representing the number of seats priorly allocated (always 0)
     * @return An int representing the number of seats that were allocated in this round of allocation
     */
    public int firstSeatAlloc(int quota, int seatsAllocated){
        for (int i = 0; i < parties.length;i++){
            int seats = (int) (Math.floor(parties[i].getNumVotes() / quota)); // seats that can be assigned without further work
            if(seats > parties[i].getNumPartyCandidates()){
                seats = parties[i].getNumPartyCandidates();
                parties[i].setNumVotesAfterFirstAllocation(-1);
            }
            else{
                parties[i].setNumVotesAfterFirstAllocation(parties[i].getNumVotes() - quota*seats);
            }

            seatsAllocated += seats;
            parties[i].setNumSeatsAllotedFirst(seats);
            // subtract votes used towards seats already alloted to this party

        }
        return seatsAllocated;
    }

    /**
     * Conducts second allocation of seats in order of most votes remaining after initial allocation of seats (largest remainder algorithm)
     * @param quota An int representing the number of votes needed to automatically be assigned a seat
     * @param seatsAllocated An int representing the number of seats priorly allocated
     */
    public void secondSeatAlloc(int quota, int seatsAllocated){
        while (seatsAllocated < numSeats && seatsAllocated < totalNumCandidates){
            int max = -1;
            CPLParty maxParty = null;
            ArrayList<CPLParty> tieForMax = new ArrayList<>(); // tracks tied parties for maxvotes, in case of tie
            boolean isTie = false;
            for (CPLParty party : parties){ // loop through all parties to find the one with the most remaining votes
                if (party.getNumVotesAfterFirstAllocation() > max){ // new max found
                    max = party.getNumVotesAfterFirstAllocation();
                    maxParty = party;
                    tieForMax.clear(); // clear tieformax, since we have a new max
                    isTie = false;
                    tieForMax.add(maxParty);
                }
                else if (party.getNumVotesAfterFirstAllocation() == max){ // tie for max
                    isTie = true;
                    tieForMax.add(maxParty);
                }
            }

            CPLParty[] tieForMaxArray = new CPLParty[tieForMax.size()];
            for(int i = 0; i < tieForMax.size(); i++){
                tieForMaxArray[i] = tieForMax.get(i);
            }

            if (isTie){ // if there is a tie for the max, then must break tie to determine winner of seat
                maxParty = tieForMax.get(breakTie( tieForMaxArray));
            }

            if(maxParty == null){
                break;
            }

            seatsAllocated ++;
            maxParty.setNumSeatsAllotedSecond(1); // allocate additional seat for maxparty
            maxParty.setNumVotesAfterFirstAllocation(-1); // so that it no longer is considered for remainding seats
        }
    }

    /**
     * Since some parties got NumVotesAfterFirstAllocation changed to -1, we have to reset it to its original value
     * @param quota An int representing the number of votes needed to automatically be assigned a seat
     */
    public void resetNumVotesFirstAlloc(int quota){
        for (CPLParty party : parties){
            int numSeatsAfterFirstAlloc = party.getNumVotes() - party.getNumSeatsAllotedFirst() * quota;
            party.setNumVotesAfterFirstAllocation(numSeatsAfterFirstAlloc);
        }
    }

    /**
     * After all seats are fully allocated, the names of seat-winning candidates are added to results[]
     */
    public void addToResults(){
        int k = 0;
        for (CPLParty party : parties){ // loop through all parties
            String[] partycands = party.getPartyCandidates();
            int partySeats = party.getNumSeatsAllotedFirst() + party.getNumSeatsAllotedSecond();
            for (int i = 0; i < partySeats; i++){ // for each seat alloted to party, add candidate to results[]
                results[k] = partycands[i];
                k++;
            }
        }
    }

    /**
     * Implements the largest remainder algorithm of CPL election seat distribution
     */
    public void assignSeats(){
        // quota is the amount of votes to automatically get a seat
        int quota = Math.round(numBallots / numSeats); // TODO:: is it correct to do rounding on it?
        int seatsAllocated = 0;

        // first allocation of seats that can be assigned solely by meeting quota
        if(quota > 0){
            seatsAllocated = firstSeatAlloc(quota, seatsAllocated);
        }
        else{
            seatsAllocated = firstSeatAlloc(Integer.MAX_VALUE, seatsAllocated);
        }


        // second allocation of seats
        secondSeatAlloc(quota, seatsAllocated);

        // since some parties got NumVotesAfterFirstAllocation changed to -1, we have to reset it to its original value
        resetNumVotesFirstAlloc(quota);

        // all seats are now fully allocated. Now add to results[]
        addToResults();
    }

    /**
     * Executes the CPL election algorithms using the largest remainder algorithm
     */
    public void runElection(){
        CPLParty tempParty;
        for (CPLBallot ballot : (CPLBallot[]) ballots){ // tally votes and assign to each party
            tempParty = ballot.getPartyChoice();
            tempParty.incrementVotes(1);
        }
        assignSeats(); // executes largest remainder algorithm
    }

    /**
     * Prints winners of seats in CPL election results to console
     */
    public void printElectionResults(){
        System.out.println("The winners of seats are: ");
        for (String cand : results){ // loop through each candidate in results[] and print it
            if(cand != null){
                System.out.println(cand);
            }
        }
    }

    /**
     * helper function for produceAuditFile(), does brunt of formatting of output txt file
     * @return A String that should be pasted into the output auditfile.txt
     */
    private String produceAuditFileString(){
        String out = "";
        String lineOfDashes = "-".repeat(175) + "\n";
        String columnHeadersLine1 = String.format("%-20s | %-10s | %-11s | %-9s | %-10s | %-5s | %-10s \n",
                "", "", "First", "Remaining", "Second", "Final", "% of Vote");
        String columnHeadersLine2 = String.format("%-20s | %-10s | %-11s | %-9s | %-10s | %-5s | %-10s \n",
                "Parties", "Votes", "Allocation", "Votes", "Allocation", "Seat", "to");
        String columnHeadersLine3 = String.format("%-20s | %-10s | %-11s | %-9s | %-10s | %-5s | %-10s \n",
                "", "", "Of Seats", "", "Of Seats", "Total", "% of Seats");

        out += lineOfDashes + columnHeadersLine1 + columnHeadersLine2 + columnHeadersLine3 + lineOfDashes;

        for (CPLParty party : parties){ // loop through each party and add its info to auditfile on a single line
            int partyTotalSeats = party.getNumSeatsAllotedFirst() + party.getNumSeatsAllotedSecond();
            int percSeats = Math.round(100 * partyTotalSeats / numSeats);

            int percVote;
            if(numBallots == 0){
                percVote = 0;
            }
            else{
                percVote = Math.round(100 * party.getNumVotes() / numBallots);
            }
            String percString = String.valueOf(percVote) + "% / " + String.valueOf(percSeats) + "% \n";
            out += String.format("%-20s | %-10d | %-11d | %-9d | %-10d | %-5d | %-10s \n", party.getName(),
                    party.getNumVotes(), party.getNumSeatsAllotedFirst(), party.getNumVotesAfterFirstAllocation(),
                    party.getNumSeatsAllotedSecond(), partyTotalSeats, percString);
        }

        out += lineOfDashes;
        return out;
    }

    /**
     * produces an audit file storing election results and seat distribution (auditfile.txt)
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