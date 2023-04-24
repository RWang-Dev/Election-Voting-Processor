import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class POCandidateTest {
    private POCandidate candidate = new POCandidate("name", 0);
    private POCandidate other = new POCandidate("other", 1);

    // Tests that the constructor works properly for POCandidate
    @Test
    void POCandidateConstructorTest(){
        assertNotNull(other);
        assertNotNull(candidate);
    }

    // Tests that the POCandidate stores the isWinner boolean correctly
    @Test
    void getWinnerTest() {
        assertEquals(candidate.getIsWinner(), false);
        assertEquals(other.getIsWinner(), false);
        candidate.SetToWinner();
        assertEquals(candidate.getIsWinner(), true);
        assertEquals(other.getIsWinner(), false);
        other.SetToWinner();
        assertEquals(candidate.getIsWinner(), true);
        assertEquals(other.getIsWinner(), true);
    }

    // Tests the compareTo function of POCandidate in various different scenarios
    @Test
    void comparePOTest(){
        assertEquals(candidate.getIsWinner(), false);
        assertEquals(other.getIsWinner(), false);
        assertEquals(candidate.compareTo(other), 0);
        candidate.SetToWinner();
        assertEquals(candidate.compareTo(other), 1);
        assertEquals(other.compareTo(candidate), -1);
        other.SetToWinner();
        assertEquals(candidate.compareTo(other), 1);
        assertEquals(other.compareTo(candidate), 1);
        candidate.incrementVotes(1);
        assertEquals(candidate.compareTo(other), 1);
        assertEquals(other.compareTo(candidate), -1);
        other.incrementVotes(5);
        assertEquals(candidate.compareTo(other), -1);
        assertEquals(other.compareTo(candidate), 1);

    }
}
