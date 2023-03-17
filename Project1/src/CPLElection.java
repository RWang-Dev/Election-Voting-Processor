// inherits from abstract class Election
// represents a single CPL election and conducts the necessary algorithms

import java.lang.Math;
import java.io.File;
import java.io.FileWriter; // TODO:: may need to add to UML class diagram?
import java.io.FileNotFoundException;
import java.io.IOException;

public class CPLElection extends Election {
    private CPLParty[] parties; // Just realized this is just the same as voteables - TODO:: remove comment
    private int numSeats;
    private String[] results;

    // constructor assigns values to instance variables
    public CPLElection(CPLParty[] parties, CPLBallot[] ballots, int numballots, int numSeats){
        this.parties = parties;
        this.numVoteables = parties.length;
        this.ballots = ballots;
        this.numBallots = numballots;
        this.numSeats = numSeats;
        this.results = new String[numSeats];
    }

    public int getNumSeats() {
        return numSeats;
    }

    public String[] getResults() {
        return results;
    }

    // assigns every value in input array to null
    public void clearArray(CPLParty[] arr){
        for (int i = 0; i < arr.length; i++){
            if (arr[i] != null){
                arr[i] = null;
            }
        }
    }

    // first allocation of seats that can be assigned solely by meeting quota
    public int firstSeatAlloc(int quota, int seatsAllocated){
        for (CPLParty party : parties){
            int seats = (int) (Math.floor(party.getNumVotes() / quota)); // seats that can be assigned without further work
            seatsAllocated += seats;
            party.setNumSeatsAllotedFirst(seats);
            // subtract votes used towards seats already alloted to this party
            party.setNumVotesAfterFirstAllocation(party.getNumVotes() - quota*seats);
        }
        return seatsAllocated;
    }

    // second allocation of seats, go in order of most votes remaining after initial allocation of seats
    public void secondSeatAlloc(int quota, int seatsAllocated){
        while (seatsAllocated < numSeats){
            int max = -1;
            CPLParty maxParty = null;
            CPLParty[] tieForMax = new CPLParty[numVoteables]; // tracks tied parties for maxvotes, in case of tie
            boolean isTie = false;
            for (CPLParty party : parties){ // loop through all parties to find the one with the most remaining votes
                if (party.getNumVotesAfterFirstAllocation() > max){ // new max found
                    max = party.getNumVotesAfterFirstAllocation();
                    maxParty = party;
                    clearArray(tieForMax); // clear tieformax, since we have a new max
                    isTie = false;
                    tieForMax[0] = maxParty;
                }
                else if (party.getNumVotesAfterFirstAllocation() == max){ // tie for max
                    isTie = true;
                    int i = 1;
                    while (i < numVoteables && tieForMax[i] != null){ // find first open slot in tieForMax
                        i++;
                    }
                    tieForMax[i] = party; // add party to tieForMax array
                }
            }

            if (isTie){ // if there is a tie for the max, then must break tie to determine winner of seat
                maxParty = tieForMax[breakTie(tieForMax)];
            }

            seatsAllocated ++;
            maxParty.setNumSeatsAllotedSecond(1); // allocate additional seat for maxparty
            maxParty.setNumVotesAfterFirstAllocation(-1); // so that it no longer is considered for remainding seats
        }
    }

    // since some parties got NumVotesAfterFirstAllocation changed to -1, we have to reset it to its original value
    public void resetNumVotesFirstAlloc(){
        for (CPLParty party : parties){
            int numSeatsAfterFirstAlloc = party.getNumVotes() - party.getNumSeatsAllotedFirst() * quota;
            party.setNumVotesAfterFirstAllocation(numSeatsAfterFirstAlloc);
        }
    }

    // all seats are now fully allocated. Now add to results[]
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

    // implements largest remainder algorithm
    public void assignSeats(){
        // quota is the amount of votes to automatically get a seat
        int quota = Math.round(numBallots / numSeats); // TODO:: is it correct to do rounding on it?
        int seatsAllocated = 0;

        // first allocation of seats that can be assigned solely by meeting quota
        seatsAllocated = firstSeatAlloc(quota, seatsAllocated);

        // second allocation of seats
        secondSeatAlloc(quota, seatsAllocated);

        // since some parties got NumVotesAfterFirstAllocation changed to -1, we have to reset it to its original value
        resetNumVotesFirstAlloc();

        // all seats are now fully allocated. Now add to results[]
        addToResults();
    }

    // executes the CPL largest remainder algorithm
    public void runElection(){
        CPLParty tempParty;
        for (CPLBallot ballot : (CPLBallot[]) ballots){ // tally votes and assign to each party
            tempParty = ballot.getPartyChoice();
            tempParty.incrementVotes(1);
        }
        assignSeats(); // executes largest remainder algorithm
    }

    // prints winners of seats in CPL election results to console. should only be called after runElection()
    public void printElectionResults(){
        System.out.println("The winners of seats are: ");
        for (String cand : results){ // loop through each candidate in results[] and print it
            System.out.println(cand);
        }
    }

    // helper function for produceAuditFile(), does brunt of formatting of output txt file
    public String produceAuditFileString(){
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
            int percVote = Math.round(100 * party.getNumVotes() / numBallots);
            String percString = String.valueOf(percVote) + "% / " + String.valueOf(percSeats) + "% \n";
            out += String.format("%-20s | %-10d | %-11d | %-9d | %-10d | %-5d | %-10s \n", party.getName(),
                    party.getNumVotes(), party.getNumSeatsAllotedFirst(), party.getNumVotesAfterFirstAllocation(),
                    party.getNumSeatsAllotedSecond(), partyTotalSeats, percString);
        }

        out += lineOfDashes;
        return out;
    }

    // produces and audit file storing election results and seat distribution (auditfile.txt)
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