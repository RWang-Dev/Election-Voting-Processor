import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void TestMainNoBallots() throws IOException {
        // Set System.in to custom stream
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        Scanner s = new Scanner(System.in);
        IRFileProcessor processor = new IRFileProcessor();
        IRCandidate[] winners = new IRCandidate[1000];
        IRCandidate[] candidates = null;

        for(int i = 0; i < 1000; i++){
            File[] files = new File[] {new FileHandler("testIRNoBallots.csv").openFile()};
            IRElection election = (IRElection) processor.processFile(files);
            election.runElection();
            winners[i] = (IRCandidate) election.getCandidates()[0];
            candidates = (IRCandidate[]) election.getCandidates();
        }

        int[] id_counts = new int[4];
        for (IRCandidate winner : winners){
            id_counts[winner.getID()]++;
        }
        System.out.println(Arrays.toString(id_counts));

        for (int id_count: id_counts) {
            assertTrue(id_count < 350 && id_count > 100);
        }

        // reset System.in to its original
        System.setIn(sysInBackup);
    }
}