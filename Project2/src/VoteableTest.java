import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoteableTest {
    Voteable voteable = new IRCandidate("Bob", 101);

    @Test
    void getName() {
        assertEquals("Bob", voteable.getName());
    }

    @Test
    void getNumVotes() {
        assertEquals(0,voteable.getNumVotes());
    }

    @Test
    void getID() {
        assertEquals(101, voteable.getID());
    }

    @Test
    void incrementVotes() {
        voteable.incrementVotes(1);
        assertEquals(1, voteable.getNumVotes());
        voteable.incrementVotes(10);
        assertEquals(11, voteable.getNumVotes());
    }
}