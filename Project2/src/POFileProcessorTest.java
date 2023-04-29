import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class POFileProcessorTest {
    POFileProcessor processor = new POFileProcessor();

    @Test
    void constructorTest() {
        assertNotNull(processor);
    }

    @Test
    void processGoodFileTest() {
        FileHandler fh = new FileHandler("testPO.csv");
        File[] good_files = new File[] {fh.openFile()};
        POElection election = (POElection) processor.processFile(good_files);
        assertEquals(40, election.getNumBallots());
        assertEquals(6, election.candidates.length);
    }

    @Test
    void processFileWithNoBallotsTest(){
//        FileHandler fh = new FileHandler("csvTestFiles/testIRNoBallots.csv");
//        File[] no_ballot_files = new File[] {fh.openFile()} ;
//        POElection election = (POElection) processor.processFile(no_ballot_files);
//        assertNotNull(election);
//        assertEquals(0, election.getNumBallots());
//        assertEquals(4, election.getNumCandidates());
//        for (Ballot ballot: election.getBallots()){
//            assertNotNull(ballot);
//            assertTrue(((IRBallot) ballot).getCandidatesQueue().size() > 0);
//        }
//        for (Voteable candidate: election.getCandidates()){
//            assertNotNull(candidate);
//            assertNotNull(candidate.getName());
//        }
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

    // Test that multiple file functionality works
    @Test
    void processManyFilesTest(){
//        File[] files = new File[] {(new FileHandler("testIR.csv")).openFile(),
//                (new FileHandler("testIR2.csv")).openFile(),
//                (new FileHandler("testIR3.csv")).openFile()} ;
//        POElection election = (POElection) processor.processFile(files);
//        assertNotNull(election);
//        assertEquals(28, election.getNumBallots());
//        assertEquals(4, election.getNumCandidates());
//        for (Ballot ballot: election.getBallots()){
//            assertNotNull(ballot);
//            assertTrue(((IRBallot) ballot).getCandidatesQueue().size() > 0);
//        }
//        for (Voteable candidate: election.getCandidates()){
//            assertNotNull(candidate);
//            assertNotNull(candidate.getName());
//        }

    }
}
