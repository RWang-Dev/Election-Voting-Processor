import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CPLElectionTest {
    CPLFileProcessor processor = new CPLFileProcessor();
    FileHandler fh_normal_election = new FileHandler("testCPL.csv");
    FileHandler fh_two_tied = new FileHandler("testCPLTwoTie.csv");


    @Test
    void runNormalElectionTest() {
        File[] normal_files = new File[] {fh_normal_election.openFile()};
        CPLElection normal_election = (CPLElection) processor.processFile(normal_files);
        normal_election.runElection();
        String[] correct = {"Dem1", "Dem2", "Rep1", "Rep2", "NW1",};
        String[] results = normal_election.getResults();
        for (int i = 0; i < results.length; i++) {
            assertTrue(correct[i].equals(results[i]));
        }
    }

    @Test
    void runTwoTiedElectionTest() {
        File[] two_tie_files = new File[] {fh_two_tied.openFile()};
        CPLElection two_tie = (CPLElection) processor.processFile(two_tie_files);
        two_tie.runElection();
        String[] correct = {"Dem1", "Dem2", "Dem3", "Rep1", "Rep2",};
        String[] results = two_tie.getResults();
        for (int i = 0; i < results.length; i++) {
            assertTrue(correct[i].equals(results[i]));
        }
    }

    @Test
    void runManyFilesElectionTest() {

    }

}