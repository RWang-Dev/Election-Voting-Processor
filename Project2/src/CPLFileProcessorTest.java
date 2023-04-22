import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.File;

public class CPLFileProcessorTest {
    CPLFileProcessor processor = new CPLFileProcessor();
    File null_file = null;
    FileHandler fh_good = new FileHandler("Project2/testing/csvTestFiles/testCPL.csv");
    FileHandler fh_no_ballots = new
            FileHandler("Project2/testing/csvTestFiles/testCPLNoBallots.csv");


    @Test
    void CPLFileProcessorConstructorTest(){
        assertNotNull(processor);
    }


    // Tests that a good file will fill out the
    // appropriate fields without error
    @Test
    void processGoodFileTest(){
        File[] good_files = new File[] {fh_good.openFile()};
        CPLElection good_election = (CPLElection) processor.processFile(good_files);
        assertNotNull(good_election);
        assertEquals(5, good_election.getNumSeats());
        assertEquals(40, good_election.getNumBallots());
        assertEquals(6, good_election.getNumVoteables());
    }

    // Tests that a csv file with no ballots
    // will still be processed
    @Test
    void processNoBallotFileTest(){
        File[] no_ballot_files = new File[] {fh_no_ballots.openFile()} ;
        CPLElection no_ballots_election = (CPLElection) processor.processFile(no_ballot_files);
        //assertNotNull(no_ballots_election);
        assertEquals(5, no_ballots_election.getNumSeats());
        assertEquals(0, no_ballots_election.getNumBallots());
        assertEquals(6, no_ballots_election.getNumVoteables());
    }

    // If given a bad file, should throw an exception
    // If given a null file, should throw an exception
    @Test
    void processFileNonExistentTest(){
        File[] null_files = new File[] {null_file} ;
        assertThrows(NullPointerException.class, () -> processor.processFile(null_files));
        File[] nonexistent_files = new File[] { (new FileHandler("Nonexistentfile.csv")).openFile() };
        assertThrows(IllegalArgumentException.class,
                () -> processor.processFile(nonexistent_files));
    }

    // Test that multiple file functionality works
    @Test
    void processManyFilesTest(){
        File[] files = new File[] {(new FileHandler("Project2/src/testCPL.csv")).openFile(),
                (new FileHandler("Project2/src/testCPLAllTie.csv")).openFile(),
                (new FileHandler("Project2/src/testCPLAllTieV2.csv")).openFile()} ;
        CPLElection election = (CPLElection) processor.processFile(files);
        assertNotNull(election);
        assertEquals(5, election.getNumSeats());
        assertEquals(58, election.getNumBallots());
        assertEquals(6, election.getNumVoteables());

    }
}