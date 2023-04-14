// author: Alex Iliarski (iliar004)

/**
 * Represents a party object that voters vote for in a CPL election
 */
public class CPLParty extends Voteable {
    private String[] rankedPartyCandidates;
    private int numPartyCandidates;
    private int numSeatsAllotedFirst;
    private int numSeatsAllotedSecond;
    private int numVotesAfterFirstAllocation;

    /**
     * Creates a CPLParty object and initializes its attributes
     * @param name A String representing the name of the party
     * @param ID An int representing the unique ID of the party
     * @param candidates A String[] representing the names of the party's candidates, in ranked order from first to last
     */
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

    /**
     * Gets the party's candidates ranked in order
     * @return A String[] representing the names of the party's candidates, in ranked order
     */
    public String[] getPartyCandidates() {
        return rankedPartyCandidates;
    }

    /**
     * Gets the number of candidates the party has listed
     * @return An int representing the number of candidates the party has listed
     */
    public int getNumPartyCandidates() {
        return numPartyCandidates;
    }

    // TODO:: do we actually use this function? Also add error handling
    /**
     * Gets the top numSeats candidates listed by a party
     * @param numSeats An int representing how many of the top candidates requested to be retrieved
     * @return A String[] of the top numSeats candidates listed by a party
     */
    public String[] getTopPartyCandidates(int numSeats){
        if(numSeats > this.rankedPartyCandidates.length){
            numSeats = this.rankedPartyCandidates.length;
        }

        String[] out = new String[numSeats];
        for (int i = 0; i<numSeats; i++){
            out[i] = this.rankedPartyCandidates[i];
        }
        return out;
    }

    /**
     * Gets the number of seats that are allocated to a party after the initial allocation of seats
     * @return An int representing the number of seats allocated to a party during the initial allocation
     */
    public int getNumSeatsAllotedFirst() {
        return numSeatsAllotedFirst;
    }

    /**
     * Gets the number of seats that are allocated to a party after the second allocation of seats based on largest
     * remainder
     * @return An int representing the number of seats allocated to a party during the second allocation
     */
    public int getNumSeatsAllotedSecond() {
        return numSeatsAllotedSecond;
    }

    /**
     * Gets the number of votes that a party has, subtracting those used to allocate seats in the initial allocation
     * @return An int representing the number of votes that a party has, subtracting those used to allocate seats in the initial allocation
     */
    public int getNumVotesAfterFirstAllocation() {
        return numVotesAfterFirstAllocation;
    }

    /**
     * Sets the number of seats that are allocated to a party during the initial allocation of seats
     * @param seats An int representing the number of seats allocated to a party after the initial allocation of seats
     */
    public void setNumSeatsAllotedFirst(int seats) {
        if(seats < 0){
            throw new IllegalArgumentException();
        }
        this.numSeatsAllotedFirst = seats;
    }

    /**
     * Sets the number of seats that are allocated to a party during the second allocation of seats
     * @param seats An int representing the number of seats allocated to a party during the second allocation of seats
     */
    public void setNumSeatsAllotedSecond(int seats) {
        if(seats < 0){
            throw new IllegalArgumentException();
        }
        this.numSeatsAllotedSecond = seats;
    }

    /**
     * Sets the number of votes that a party has, subtracting those used to allocate seats in the initial allocation
     * @param num An int representing the number of votes a party has, subtracting those used to allocate seats in the initial allocation
     */
    public void setNumVotesAfterFirstAllocation(int num) {
        this.numVotesAfterFirstAllocation = num;
    }
}
