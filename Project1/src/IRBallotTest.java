import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IRBallotTest {
    IRCandidate bob_cand = new IRCandidate("Bob", 0);
    IRCandidate alice_cand = new IRCandidate("Alice", 1);
    IRCandidate charles_cand = new IRCandidate("Charles", 2);
    LinkedList<IRCandidate> cands = new LinkedList<>();

    @BeforeAll
    void init(){
        cands.add(alice_cand);
        cands.add(bob_cand);
        cands.add(charles_cand);
    }

    @Test
    void constructorTest() {
        IRBallot constrTestBallot = new IRBallot(new LinkedList<IRCandidate>());
        assertNotNull(constrTestBallot);
    }

    @Test
    void redistributeVoteTest() {
        // Create eliminatedcandidatesqueue
        ArrayList<IRCandidate> eliminatedCandidatesQueue = new ArrayList<>();
        eliminatedCandidatesQueue.add(bob_cand);

        // Test calling when second candidate in candidatesqueue is "eliminated"
        // Expected that no changes are made within the ballot
        IRBallot ballot = new IRBallot(cands);
        ballot.redistributeVote(eliminatedCandidatesQueue);
        assertEquals(3, ballot.getCandidatesQueue().size());
        assertTrue(alice_cand.equals(cands.get(0)));
        assertTrue(bob_cand.equals(cands.get(0)));
        assertTrue(charles_cand.equals(cands.get(0)));

        // Eliminate the first candidate in the queue
        // Second candidate also gets eliminated because
        // it was eliminated previously
        eliminatedCandidatesQueue.add(alice_cand);
        ballot.redistributeVote(eliminatedCandidatesQueue);
        assertEquals(1, ballot.getCandidatesQueue().size());
        assertTrue(charles_cand.equals(cands.get(0)));

        // Test eliminating last candidate
        eliminatedCandidatesQueue.add(charles_cand);
        ballot.redistributeVote(eliminatedCandidatesQueue);
        assertEquals(0, ballot.getCandidatesQueue().size());

        // Test with an empty candidatesqueue
        ballot.redistributeVote(eliminatedCandidatesQueue);
        assertEquals(0, ballot.getCandidatesQueue().size());
    }
}