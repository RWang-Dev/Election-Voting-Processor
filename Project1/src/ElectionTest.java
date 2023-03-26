import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

import static java.util.Arrays.fill;
import static org.junit.jupiter.api.Assertions.*;
public class ElectionTest {
    CPLElection election = new CPLElection(new CPLParty[10], new CPLBallot[2], 2,2);

    // Runs breakTie 100 times on input party. Returns a bool
    // array -- each value in the bool array is whether or not
    // two consecutive tests are equal. If breakTie is implemented
    // correctly it should return all or mostly false.
    boolean[] generateRandomIntsFromBreakTie(CPLParty[] parties){
        int[] random_results = new int[100];
        boolean[] boolsequal = new boolean[99];
        for(int i = 0; i < 100; i++){
            random_results[i] = election.breakTie(parties);
            if(i > 0){
                boolsequal[i - 1] = (random_results[i] == random_results[i - 1]);
            }
        }
        return boolsequal;
    }

    boolean[] generateRandomIntsFromBreakTie(IRCandidate[] candidates){
        int[] random_results = new int[100];
        boolean[] boolsequal = new boolean[99];
        for(int i = 0; i < 100; i++){
            random_results[i] = election.breakTie(candidates);
            if(i > 0){
                boolsequal[i - 1] = (random_results[i] == random_results[i - 1]);
            }
        }
        return boolsequal;
    }

    @Test
    void breakTieTestWithCPLParties(){
        CPLParty[] parties = new CPLParty[1000];
        for (int i = 0; i < 1000; i++){
            parties[i] = new CPLParty("party_" + i, i, new String[]{"Bob_" + i});
        }
        boolean[] boolsequal = generateRandomIntsFromBreakTie(parties);

        // Control -- All true
        boolean[] allTrueBool = new boolean[99];
        fill(allTrueBool, true);

        // If breakTie is random, this test should pass
        assertFalse(Arrays.equals(allTrueBool, boolsequal));
    }

    @Test
    void BreakTieTestWithIRCandidates(){
        IRCandidate[] candidates = new IRCandidate[1000];
        for (int i = 0; i < 1000; i++){
            candidates[i] = new IRCandidate("party_" + i, i);
        }
        boolean[] boolsequal = generateRandomIntsFromBreakTie(candidates);

        // Control -- All true
        boolean[] allTrueBool = new boolean[99];
        fill(allTrueBool, true);

        // If breakTie is random, this test should pass
        assertFalse(Arrays.equals(allTrueBool, boolsequal));
    }

    // Test when input is length 0 and length 1
    // Should return -1 for length 0 and 0 for length 1
    @Test
    void breakTieTestEdgeCases(){
        IRCandidate[] candidates = new IRCandidate[0];
        assertEquals(-1, election.breakTie(candidates));

        IRCandidate[] candidates_1 = new IRCandidate[1];
        candidates_1[0] = new IRCandidate("",0);
        assertEquals(0, election.breakTie(candidates_1));
    }

//    @Test
//    void PromptUserForDataTest(){
//
//        // Set System.in to custom stream
//        InputStream sysInBackup = System.in; // backup System.in to restore it later
//        ByteArrayInputStream in = new ByteArrayInputStream("03/25/2023".getBytes());
//        System.setIn(in);
//
//        assertEquals("03/25/2023", election.promptUserForDate());
//
//
//        // reset System.in to its original
//        System.setIn(sysInBackup);
//    }
}