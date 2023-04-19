import org.junit.jupiter.api.Test;

import java.io.File;

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
        File[] good_files = new File[] {fh.openFile()};
        IRElection election = (IRElection) processor.processFile(good_files);
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
        File[] no_ballot_files = new File[] {fh.openFile()} ;
        IRElection election = (IRElection) processor.processFile(no_ballot_files);
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
        File[] nonexistent_files = new File[] { (new FileHandler("Nonexistentfile.csv")).openFile() };
        assertThrows(IllegalArgumentException.class,
                () -> processor.processFile(nonexistent_files));
        assertThrows(NullPointerException.class, ()->processor.processFile(null));
    }
}