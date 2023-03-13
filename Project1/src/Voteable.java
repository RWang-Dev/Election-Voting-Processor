// Abstract Voteable class that CPLParty and IRCandidate inherit from

abstract class Voteable{
    protected String name;
    protected int numVotes;
    protected int ID;

    public String getName() {
        return name;
    }

    public int getNumVotes() {
        return numVotes;
    }

    public int getID() {
        return ID;
    }

    public void incrementVotes(int amount){
        this.numVotes += amount;
    }
}
