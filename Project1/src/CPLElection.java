// inherits from abstract class Election
// represents a single CPL election and conducts the necessary algorithms

import java.lang.Math;
import java.io.File;
import java.io.FileWriter; // TODO:: may need to add to UML class diagram?

public class CPLElection extends Election {
//    private CPLParty[] parties; // Just realized this is just the same as voteables - TODO:: remove comment
    private int numSeats;
    private String[] results;

    // constructor assigns values to instance variables
    public CPLElection(CPLParty[] parties, Ballot[] ballots, int numballots, int numSeats){
        this.voteables = parties;
        this.numVoteables = parties.length;
        this.ballots = ballots;
        this.numBallots = numballots;
        this.numSeats = numSeats;
        this.results = "";
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

    // TODO:: Split this function into smaller method, potentially?
    // implements largest remainder algorithm
    public void assignSeats(){
        // quota is the amount of votes to automatically get a seat
        int quota = int(Math.ceil(numBallots / numSeats)); // is it correct to do ceiling on it?
        int seatsAllocated = 0;

        // first allocation of seats that can be assigned solely by meeting quota
        for (CPLParty party : voteables){
            int seats = int(Math.floor(party.getNumVotes() / quota)); // seats that can be assigned without further work
            seatsAllocated += seats;
            party.setNumSeatsAllotedFirst(seats);
            // subtract votes used towards seats already alloted to this party
            party.setNumVotesAfterFirstAllocation(party.getNumVotes() - quota*seats);
        }

        // second allocation of seats
        // go in order of most votes remaining after initial allocation of seats
        while (seatsAllocated < numSeats){
            int max = -1;
            CPLParty maxParty = null;
            CPLParty[] tieForMax = CPLParty[numVoteables]; // tracks tied parties for maxvotes, in case of tie
            boolean isTie = false;
            for (CPLParty party : voteables){ // loop through all parties to find the one with the most remaining votes
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
                maxParty = breakTie(tieForMax);
            }

            seatsAllocated ++;
            maxParty.setNumSeatsAllotedSecond(1); // allocate additional seat for maxparty
            maxParty.setNumVotesAfterFirstAllocation(-1); // so that it no longer is considered for more seats
        }

        // all seats are now fully allocated. Now add to results[]
        int k = 0;
        for (CPLParty party : voteables){ // loop through all parties
            String[] partycands = party.getPartyCandidates();
            int partySeats = party.getNumSeatsAllotedFirst() + party.getNumSeatsAllotedSecond();
            for (int i = 0; i < partySeats; i++){ // for each seat alloted to party, add candidate to results[]
                results[k] = partycands[i];
                k++;
            }
        }
    }

    // executes the CPL largest remainder algorithm
    public void runElection(){
        CPLParty tempParty;
        for (CPLBallot ballot : ballots){ // tally votes and assign to each party
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

    // creates audit file detailing the allocation of seats. should only be called after runElection()
    public void produceAuditFile(){
        File f = new File("auditfile.csv");
        f.createNewFile();

        FileWriter fp = new FileWriter("auditfile.csv", false); // clears data in file previously
        String out = "";
        out += "-,-,-,-,-,-,-\n";
        out += "Parties, Votes, First Allocation of Seats, Remaining Votes, Second Allocation of Seats, " +
                "Final Seat Total, % of vote to % of seats\n"; // headers of table columns
        out += "-,-,-,-,-,-,-\n";
        for (CPLParty party : voteables){ // loop through each party and add its info to auditfile on a single line
            out += party.getName() + ",";
            out += String.valueOf(party.getNumVotes()) + ",";
            out += String.valueOf(party.getNumSeatsAllotedFirst()) + ",";
            out += String.valueOf(party.getNumVotesAfterFirstAllocation()) + ",";
            out += String.valueOf(party.getNumSeatsAllotedSecond()) + ",";
            int partyTotalSeats = party.getNumSeatsAllotedFirst() + party.getNumSeatsAllotedSecond();
            out += String.valueOf(partyTotalSeats) + ",";
            int percSeats = Math.round(partyTotalSeats / numSeats);
            int percVote = Math.round(party.getNumVotes() / numBallots);
            out += String.valueOf(percVote) + "% / " + String.valueOf(percVote) + "% \n";
        }
        out += "-,-,-,-,-,-,-\n";

        fp.write(out); // write output string to file
        fp.close(); // close file
    }

}