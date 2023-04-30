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
        FileHandler fh = new FileHandler("testPONoBallots.csv");
        File[] no_ballot_files = new File[] {fh.openFile()} ;
        POElection election = (POElection) processor.processFile(no_ballot_files);
        assertNotNull(election);
        assertEquals(0, election.getNumBallots());
        assertEquals(4, election.getNumVoteables());

        for (Voteable candidate: election.getVoteables()){
            assertNotNull(candidate);
            assertNotNull(candidate.getName());
        }
    }

    // Test that multiple file functionality works
    @Test
    void processManyFilesTest(){
        File[] files = new File[] {(new FileHandler("testPO.csv")).openFile(),
                (new FileHandler("testPOAllTie.csv")).openFile(),
                (new FileHandler("testPOTwoTie.csv")).openFile()} ;
        POElection election = (POElection) processor.processFile(files);
        assertNotNull(election);
        assertEquals(64, election.getNumBallots());
        assertEquals(6, election.getNumVoteables());

        for (Voteable candidate: election.getVoteables()){
            assertNotNull(candidate);
            assertNotNull(candidate.getName());
        }

    }
}
