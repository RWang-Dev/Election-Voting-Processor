// CPLBallot.java contains class CPLBallot, which inherits from Ballot
// A CPLBallot represents a single ballot in a CPL election.
// author: Alex Iliarski (iliar004)

/**
 * Represents a single ballot in a CPL election (inherits from abstract class Ballot)
 */
public class CPLBallot extends Ballot{
    private CPLParty partyChoice;

    /**
     * Creates a CPLBallot with the given party choice
     * @param partyChoice A CPLParty representing the particular voter's choice on their ballot
     */
    public CPLBallot(CPLParty partyChoice){
        this.partyChoice = partyChoice;
    }

    /**
     * Gets the ballot's party choice, i.e. which party the voter whose ballot this is voted for
     * @return A CPLParty representing the party which this ballot voted for
     */
    public CPLParty getPartyChoice() {
        return partyChoice;
    }
}
