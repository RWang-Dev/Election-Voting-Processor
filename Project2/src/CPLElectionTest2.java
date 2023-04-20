import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CPLElectionTest2 {
    CPLFileProcessor processor = new CPLFileProcessor();
    FileHandler fh_normal_election = new FileHandler("Project2/src/testCPL.csv");
    FileHandler fh_two_tied = new FileHandler("Project2/src/testCPLTwoTie.csv");

    FileHandler fh_all_tied = new FileHandler("Project2/src/testCPLAllTie.csv");





    @Test
    void runNormalElectionTest() {
        CPLElection normal_election = (CPLElection) processor.processFile(fh_normal_election.openFile());
        normal_election.runElection();
        String[] correct = {"Dem1", "Dem2", "Rep1", "Rep2", "NW1",};
        String[] results = normal_election.getResults();
        for (int i = 0; i < results.length; i++) {
            assertEquals(correct[i], results[i]);
        }

    }
    @Test
    void runAllTieElectionTest() {
        int dem = 0;
        int rep = 0;
        int nw = 0;
        int reform = 0;
        int green = 0;
        int ind = 0;
        for (int j = 0; j<600; j++){
            CPLElection normal_election = (CPLElection) processor.processFile(fh_all_tied.openFile());
            normal_election.runElection();
            String[] correct = {"Dem1", "Rep1", "NW1", "Reform1", "Green1", "Ind1"};
            String[] results = normal_election.getResults();
            for (int i = 0; i < results.length; i++) {
                if (results[i].equals("Dem1")){
                    dem ++;
                }
                else if (results[i].equals("Rep1")){
                    rep ++;
                }
                else if (results[i].equals("NW1")){
                    nw ++;
                }
                else if (results[i].equals("Reform1")){
                    reform ++;
                }
                else if (results[i].equals("Green1")){
                    green ++;
                }
                else if (results[i].equals("Ind1")){
                    ind ++;
                }
            }


        }
        assertTrue(dem < 550 && dem > 450);
        assertTrue(rep < 550 && rep > 450);
        assertTrue(nw < 550 && nw > 450);
        assertTrue(reform < 550 && reform > 450);
        assertTrue(green < 550 && green > 450);
        assertTrue(ind < 550 && ind > 450);

    }

    @Test
    void runTwoTiedElectionTest() {
        int dem = 0;
        int rep = 0;
        for (int k = 0; k < 100; k++){
            CPLElection two_tie = (CPLElection) processor.processFile(fh_two_tied.openFile());
            two_tie.runElection();


            String[] results = two_tie.getResults();
            for (int i = 0; i < results.length; i++) {
                if (results[i].startsWith("Dem")){
                    dem ++;
                }
                else if(results[i].startsWith("Rep")){
                    rep ++;
                }
            }
        }
        assertTrue(dem < 300 && dem > 200);
        assertTrue(rep < 300 && rep > 200);

    }
}
