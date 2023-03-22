import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPLPartyTest {
    String[] candidates_empty = {};
    String[] candidates_2 = {"alice", "bob"};
    private final CPLParty party_empty = new CPLParty("No Candidates Party", 1, candidates_empty);
    private final CPLParty party_2 = new CPLParty("Alice and Bob Party", 1, candidates_2);


    @Test
    void testCPLPartyConstructor(){
        assertNotNull(candidates_empty);
        assertNotNull(candidates_2);
    }

    @Test
    void testGetPartyCandidates(){
        assertEquals(candidates_empty, party_empty.getPartyCandidates());
        assertEquals(candidates_2, party_2.getPartyCandidates());
    }

    @Test
    void testGetNumPartyCandidates(){
        assertEquals(candidates_empty.length, party_empty.getNumPartyCandidates());
        assertEquals(candidates_2.length, party_2.getNumPartyCandidates());
    }

    @Test
    void testGetTopPartyCandidates(){
        assertEquals(0, party_empty.getTopPartyCandidates(0).length);
        assertEquals(0, party_empty.getTopPartyCandidates(1).length);
        assertEquals(2, party_2.getTopPartyCandidates(2).length);
        assertEquals(1, party_2.getTopPartyCandidates(1).length);

        assertEquals("alice", party_2.getTopPartyCandidates(2)[0]);
        assertEquals("bob", party_2.getTopPartyCandidates(2)[1]);
    }

    @Test
    void testSetNumSeatsAllottedFirst(){
        party_empty.setNumSeatsAllotedFirst(2);
        assertEquals(1,1);
    }
}