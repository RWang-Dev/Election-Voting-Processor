// inherits from abstract class Ballot
// represents a single ballot in a CPL election

public class CPLBallot extends Ballot{
    private CPLParty partyChoice;

    public CPLBallot(CPLParty partyChoice){
        this.partyChoice = partyChoice;
    }

    public CPLParty getPartyChoice() {
        return partyChoice;
    }
}
