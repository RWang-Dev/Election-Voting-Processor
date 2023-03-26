import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IRFileProcessorTest {
    IRFileProcessor processor = new IRFileProcessor();

    @Test
    void constructorTest() {
        assertNotNull(processor);
    }

    @Test
    void processGoodFileTest() {
        FileHandler fh = new FileHandler("Project1/testing/csvTestFiles/testIR.csv");
        IRElection election = (IRElection) processor.processFile(fh.openFile());
        assertEquals(15, election.getNumBallots());
        assertEquals(4, election.getNumCandidates());
        for (Ballot ballot: election.getBallots()){
            assertNotNull(ballot);
            assertTrue(((IRBallot) ballot).getCandidatesQueue().size() > 0);
        }
        for (Voteable candidate: election.getCandidates()){
            assertNotNull(candidate);
            assertNotNull(candidate.getName());
        }
    }

    @Test
    void processFileWithNoBallotsTest(){
        FileHandler fh = new FileHandler("Project1/testing/csvTestFiles/testIRNoBallots.csv");
        IRElection election = (IRElection) processor.processFile(fh.openFile());
        assertNotNull(election);
        assertEquals(0, election.getNumBallots());
        assertEquals(4, election.getNumCandidates());
        for (Ballot ballot: election.getBallots()){
            assertNotNull(ballot);
            assertTrue(((IRBallot) ballot).getCandidatesQueue().size() > 0);
        }
        for (Voteable candidate: election.getCandidates()){
            assertNotNull(candidate);
            assertNotNull(candidate.getName());
        }
    }

    // Tests processFile() with null input and with
    // files that aren't found to ensure clean handling.
    @Test
    void processBadFileTest() {
        assertThrows(IllegalArgumentException.class,
                () -> processor.processFile((new FileHandler("NonexistentFile")).openFile()));
        assertThrows(NullPointerException.class, ()->processor.processFile(null));
    }
}