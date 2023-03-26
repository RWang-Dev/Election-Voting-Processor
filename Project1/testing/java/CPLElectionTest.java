import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CPLElectionTest {
    CPLFileProcessor processor = new CPLFileProcessor();
    CPLElection normal_election = (CPLElection) processor.processFile
            ((new FileHandler("testCPL.csv")).openFile());
    CPLElection noBallotsElection = (CPLElection) processor.processFile
            ((new FileHandler("testCPLNoBallots.csv")).openFile());
    CPLElection change_winners_election = (CPLElection) processor.processFile
            ((new FileHandler("testCPLChangeWinners.csv")).openFile());
    @Test
    void clearArrayTest() {
    }

    @Test
    void assignSeatsTest() {
    }

    @Test
    void runElectionTest() {
        // Test this when we do system testing
    }

    @Test
    void printElectionResultsTest() {
        // Test this when we do system testing
    }

    @Test
    void produceAuditFileTest() {
        // Test this when we do system testing
    }
}