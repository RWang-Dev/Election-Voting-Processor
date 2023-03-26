import org.junit.jupiter.api.Assertions;
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

    // Tests get method. Tests that method
    // returns only up to the length of party.candidates
    @Test
    void testGetTopPartyCandidates(){
        assertEquals(0, party_empty.getTopPartyCandidates(0).length);
        assertEquals(0, party_empty.getTopPartyCandidates(1).length);
        assertEquals(2, party_2.getTopPartyCandidates(2).length);
        assertEquals(1, party_2.getTopPartyCandidates(1).length);

        assertEquals("alice", party_2.getTopPartyCandidates(2)[0]);
        assertEquals("bob", party_2.getTopPartyCandidates(2)[1]);
    }

    // Tests just setting numSeatsAllottedFirst.
    // Should throw exception for invalid input
    @Test
    void testSetNumSeatsAllottedFirst() {
        party_empty.setNumSeatsAllotedFirst(2);
        /*
         * This test was created when bug B_001 was still affecting the
         * program. It might need to change depending on the implementation
         * of the bug fix --Ethan
         * */
        assertEquals(2, party_empty.getNumSeatsAllotedFirst());

        party_empty.setNumSeatsAllotedFirst(0);
        assertEquals(0, party_empty.getNumSeatsAllotedFirst());

        //Test bad input
        assertThrows(IllegalArgumentException.class, () -> party_empty.setNumSeatsAllotedFirst(-1));
    }

    // Tests just setting numSeatsAllottedSecond.
    // Should throw exception for invalid input
    @Test
    void testSetNumSeatsAllottedSecond() {
        party_empty.setNumSeatsAllotedSecond(0);
        assertEquals(0, party_empty.getNumSeatsAllotedSecond());

        party_empty.setNumSeatsAllotedSecond(1);
        assertEquals(1, party_empty.getNumSeatsAllotedSecond());
        assertThrows(IllegalArgumentException.class, () -> party_empty.setNumSeatsAllotedSecond(-1));
    }
}