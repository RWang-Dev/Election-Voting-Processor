import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.LinkedList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class IRElectionTest {
    IRCandidate Rosen = new IRCandidate("Rosen (D)", 0);

    IRCandidate Kleinberg = new IRCandidate("Kleinberg (R)", 1);
    IRCandidate Chou = new IRCandidate("Chou (I)", 2);
    IRCandidate Royce = new IRCandidate("Royce (L)", 3);

    LinkedList<IRCandidate> b1 = new LinkedList<>(Arrays.asList(Rosen, Kleinberg, Chou, Royce));
    LinkedList<IRCandidate> b2 = new LinkedList<>(Arrays.asList(Rosen, Kleinberg, Chou, Royce));
    LinkedList<IRCandidate> b3 = new LinkedList<>(Arrays.asList(Rosen, Kleinberg, Chou, Royce));
    LinkedList<IRCandidate> b4 = new LinkedList<>(Arrays.asList(Kleinberg, Rosen, Chou, Royce));
    LinkedList<IRCandidate> b5 = new LinkedList<>(Arrays.asList(Kleinberg, Rosen, Chou, Royce));
    LinkedList<IRCandidate> b6 = new LinkedList<>(Arrays.asList(Chou, Rosen, Kleinberg, Royce));
    IRBallot B1 = new IRBallot(b1);
    IRBallot B2 = new IRBallot(b2);
    IRBallot B3 = new IRBallot(b3);
    IRBallot B4 = new IRBallot(b4);
    IRBallot B5 = new IRBallot(b5);
    IRBallot B6 = new IRBallot(b6);


    IRBallot[] testBallot = new IRBallot[]{B1, B2, B3, B4, B5, B6};
    IRCandidate[] candidates = new IRCandidate[]{Rosen, Kleinberg, Chou, Royce};
    IRElection testElection = new IRElection(candidates, testBallot);
    @BeforeAll
    void init(){
        Rosen.incrementVotes(3);
        Kleinberg.incrementVotes(2);
        Chou.incrementVotes(1);
    }
    @Test
    void getNumCandidates() {
        int numCandidates = testElection.getNumCandidates();
        assertEquals(4, numCandidates);
    }

    @Test
    void getNumBallots() {
        int numBallots = testElection.getNumBallots();
        assertEquals(6, numBallots);
    }

    @Test
    void getCandidates() {
        Voteable[] resCandidates = testElection.getCandidates();
        IRCandidate[] expCandidates = new IRCandidate[]{Rosen, Kleinberg, Chou, Royce};
        for(int i = 0; i<expCandidates.length; i++){
            assertEquals(expCandidates[i], resCandidates[i]);
        }


    }

    @Test
    void eliminateCandidate() {



        testElection.eliminateCandidate();
        IRCandidate[] expCandidates = new IRCandidate[]{Rosen, Kleinberg, Chou, Royce};

        for(int i = 0; i < 4; i++){

            assertEquals(expCandidates[i], testElection.getCandidates()[i]);
        }

        assertEquals(-1, testElection.getCandidates()[testElection.getNumCandidates()-1].getNumVotes());
        assertEquals(-1, testElection.getCandidates()[testElection.getNumCandidates()-1].getNumVotes());
        testElection.eliminateCandidate();
        for(int i = 0; i < 4; i++){
            assertEquals(expCandidates[i], testElection.getCandidates()[i]);
        }

        assertEquals(4, testElection.getCandidates()[0].getNumVotes());

    }

    @Test
    void runElection() {
        testElection.runElection();
        assertEquals(Rosen, testElection.getCandidates()[0]);
    }

    @Test
    void printElectionResults() {

    }

    @Test
    void produceAuditFile() {
    }
}