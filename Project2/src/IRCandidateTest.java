import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IRCandidateTest {

    @Test
    void IRCandidateConstructorTest(){
        IRCandidate candidate = new IRCandidate("naem", 0);
        assertNotNull(candidate);
    }

    // Tests the method
    @Test
    void updateVoteCountHistoryTest() {
        IRCandidate candidate = new IRCandidate("name", 0);
        candidate.incrementVotes(2);
        candidate.updateVoteCountHistory();
        assertEquals(2, candidate.getVoteCountHistory().get(0));

        candidate.incrementVotes(0);
        candidate.updateVoteCountHistory();
        assertEquals(2, candidate.getVoteCountHistory().get(1));
    }
}