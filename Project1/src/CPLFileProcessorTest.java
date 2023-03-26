import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.File;

public class CPLFileProcessorTest {
    CPLFileProcessor processor = new CPLFileProcessor();
    File null_file = null;
    FileHandler fh_good = new FileHandler("Project1/testing/csvTestFiles/testCPL.csv");
    FileHandler fh_no_ballots = new
            FileHandler("Project1/testing/csvTestFiles/testCPLNoBallots.csv");


    @Test
    void CPLFileProcessorConstructorTest(){
        assertNotNull(processor);
    }


    // Tests that a good file will fill out the
    // appropriate fields without error
    @Test
    void processGoodFileTest(){
        CPLElection good_election = (CPLElection) processor.processFile(fh_good.openFile());
        assertNotNull(good_election);
        assertEquals(5, good_election.getNumSeats());
        assertEquals(40, good_election.getNumBallots());
        assertEquals(6, good_election.getNumVoteables());
    }

    // Tests that a csv file with no ballots
    // will still be processed
    @Test
    void processNoBallotFileTest(){
        CPLElection no_ballots_election = (CPLElection) processor.processFile(fh_no_ballots.openFile());
        //assertNotNull(no_ballots_election);
        assertEquals(5, no_ballots_election.getNumSeats());
        assertEquals(0, no_ballots_election.getNumBallots());
        assertEquals(6, no_ballots_election.getNumVoteables());
    }

    // If given a bad file, should throw an exception
    // If given a null file, should throw an exception
    @Test
    void processFileNonExistentTest(){
        assertThrows(NullPointerException.class, () -> processor.processFile(null_file));
        assertThrows(IllegalArgumentException.class,
                () -> processor.processFile((new FileHandler("Nonexistentfile.csv")).openFile()));
    }
}