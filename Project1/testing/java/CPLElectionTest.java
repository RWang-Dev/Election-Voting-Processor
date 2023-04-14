import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CPLElectionTest {
    CPLFileProcessor processor = new CPLFileProcessor();
    CPLElection normal_election = (CPLElection) processor.processFile
            ((new FileHandler("Project1/testing/csvTestFiles/testCPL.csv")).openFile());
    CPLElection noBallotsElection = (CPLElection) processor.processFile
            ((new FileHandler("Project1/testing/csvTestFiles/testCPLNoBallots.csv")).openFile());
    CPLElection twoTiedElection = (CPLElection) processor.processFile
            ((new FileHandler("Project1/testing/csvTestFiles/testCPLTwoTie.csv")).openFile());
    CPLElection threeTiedElection = (CPLElection) processor.processFile
            ((new FileHandler("Project1/testing/csvTestFiles/testCPLThreeTie.csv")).openFile());
    @Test
    void clearArrayTest() {
    }

    @Test
    void assignSeatsTest() {
    }

    @Test
    void runElectionTest() {
        // Test this when we do system testing
        normal_election.runElection();
        noBallotsElection.runElection();
        twoTiedElection.runElection();
        threeTiedElection.runElection();
    }

    @Test
    void printElectionResultsTest() {
        // Test this when we do system testing
        normal_election.runElection();
    }

    @Test
    void produceAuditFileTest() {
        // Test this when we do system testing
    }
}