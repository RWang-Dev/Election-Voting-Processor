import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.File;

public class CPLFileProcessorTest {
    CPLFileProcessor processor = new CPLFileProcessor();
    File null_file = null;
    FileHandler fh_good = new FileHandler("Project1/src/testCPL.csv");
    FileHandler fh_no_ballots = new FileHandler("Project1/src/testCPLNoBallots.csv");



    @Test
    void CPLFileProcessorConstructorTest(){
        assertNotNull(processor);
    }

    @Test
    void processGoodFileTest(){
        CPLElection good_election = (CPLElection) processor.processFile(fh_good.openFile());
        assertNotNull(good_election);
        assertEquals(5, good_election.getNumSeats());
        assertEquals(40, good_election.getNumBallots());
        assertEquals(6, good_election.getNumVoteables());
    }

    @Test
    void processNoBallotFileTest(){
        // Todo: decide whether to keep this test.
        // A file can have no votes, should we test for this?

        CPLElection no_ballots_election = (CPLElection) processor.processFile(fh_no_ballots.openFile());
        assertNotNull(no_ballots_election);
        assertEquals(5, no_ballots_election.getNumSeats());
        assertEquals(0, no_ballots_election.getNumBallots());
        assertEquals(6, no_ballots_election.getNumVoteables());
    }

    @Test
    void processEmptyPartyFileTest(){
        // Test inputting a file where a party has no candidates
        // TODO: Decide whether to implement this and implement it if needed
    }

    @Test
    void processFileNonExistentTest(){
        assertThrows(NullPointerException.class, () -> processor.processFile(null_file));
    }
}
