// Inherits from abstract class Voteable
// Represents a party object in a CPL election

class CPLParty extends Voteable {
    private String[] rankedPartyCandidates;
    private int numPartyCandidates;
    private int numSeatsAllotedFirst;
    private int numSeatsAllotedSecond;
    private int numVotesAfterFirstAllocation;

    // initialize class variables
    public CPLParty(String name, int ID, String[] candidates){
        this.name = name;
        this.ID = ID;
        this.rankedPartyCandidates = candidates;
        this.numVotes = 0;
        this.numPartyCandidates = candidates.length;
        this.numSeatsAllotedFirst = 0;
        this.numSeatsAllotedSecond = 0;
        this.numVotesAfterFirstAllocation = 0;
    }

    public String[] getPartyCandidates() {
        return rankedPartyCandidates;
    }

    public int getNumPartyCandidates() {
        return numPartyCandidates;
    }

    // TODO:: do we actually use this function? Also add error handling
    public String[] getTopPartyCandidates(int numSeats){
        String[] out = new String[numSeats];
        for (int i = 0; i<numSeats; i++){
            out[i] = this.rankedPartyCandidates[i];
        }
        return out;
    }

    public int getNumSeatsAllotedFirst() {
        return numSeatsAllotedFirst;
    }

    public int getNumSeatsAllotedSecond() {
        return numSeatsAllotedSecond;
    }

    public int getNumVotesAfterFirstAllocation() {
        return numVotesAfterFirstAllocation;
    }

    public void setNumSeatsAllotedFirst(int seats) {
        this.numSeatsAllotedFirst = seats;
    }

    public void setNumSeatsAllotedSecond(int seats) {
        this.numSeatsAllotedSecond = seats;
    }

    public void setNumVotesAfterFirstAllocation(int num) {
        this.numVotesAfterFirstAllocation = num;
    }
}
