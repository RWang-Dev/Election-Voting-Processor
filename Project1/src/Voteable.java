// Voteable.java is an abstract class representing an object that voters vote for in an election
// CPLParty and IRCandidate inherit from Voteable
// author: Alex Iliarski (iliar004)

/**
 * Abstract class representing an object that voters vote for in an election
 */
abstract class Voteable{
    protected String name;
    protected int numVotes;
    protected int ID;

    /**
     * Gets the name of the object for which a voter votes for
     * @return A string representing the name of a Voteable object
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the number of votes that a Voteable object receives
     * @return An int representing the number of votes a Voteable object receives
     */
    public int getNumVotes() {
        return numVotes;
    }

    /**
     * Gets the ID of a Voteable object
     * @return An int representing the ID of a Voteable object
     */
    public int getID() {
        return ID;
    }

    /**
     * Increments the number of votes a Voteable object has by a specified amount
     * @param amount An integer number of votes by which the Voteable object's number of votes should be changed
     */
    public void incrementVotes(int amount){
        this.numVotes += amount;
    }
}
