import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPLBallotTest {
    String[] candidates_empty = {};
    String[] candidates_2 = {"alice", "bob"};
    CPLParty party = new CPLParty("party1", 0, candidates_2);
    CPLParty party_empty = new CPLParty("party_empty", 1, candidates_empty);
    CPLBallot cplballot = new CPLBallot(party);

    @Test
    void CPLBallotConstructorTest(){
        assertNotNull(cplballot);
    }

    @Test
    void getPartyChoiceTest(){
        assertEquals(party, cplballot.getPartyChoice());
    }
}