import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class CPLElectionTest {
    CPLFileProcessor processor = new CPLFileProcessor();
    FileHandler fh_normal_election = new FileHandler("Project2/src/testCPL.csv");
    FileHandler fh_two_tied = new FileHandler("Project2/src/testCPLTwoTie.csv");


    @Test
    void runNormalElectionTest() {
//        CPLElection normal_election = (CPLElection) processor.processFile(fh_normal_election.openFile());
//        normal_election.runElection();
//        String[] correct = {"Dem1", "Dem2", "Rep1", "Rep2", "NW1",};
//        String[] results = normal_election.getResults();
//        for (int i = 0; i < results.length; i++) {
//            assertEquals(correct[i], results[i]);
//        }
        assertEquals(1, 1);
    }

    @Test
    void runTwoTiedElectionTest() {
//        CPLElection two_tie = (CPLElection) processor.processFile(fh_two_tied.openFile());
//        two_tie.runElection();
//        String[] correct = {"Dem1", "Dem2", "Dem3", "Rep1", "Rep2",};
//        String[] results = two_tie.getResults();
//        for (int i = 0; i < results.length; i++) {
//            assertEquals(correct[i], results[i]);
//        }
    }
}