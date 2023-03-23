import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static java.util.Arrays.fill;
import static org.junit.jupiter.api.Assertions.*;
public class ElectionTest {


    // Runs breakTie 100 times on input party. Returns a bool
    // array -- each value in the bool array is whether or not
    // two consecutive tests are equal. If breakTie is implemented
    // correctly it should return all or mostly false.
    boolean[] generateRandomIntsFromBreakTie(CPLParty[] parties){
        CPLElection election = new CPLElection(new CPLParty[10], new CPLBallot[2], 2,2);
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

    @Test
    void breakTieTest(){
        CPLParty[] parties = new CPLParty[1000];
        boolean[] boolsequal = generateRandomIntsFromBreakTie(parties);

        // Control -- All true
        boolean[] allTrueBool = new boolean[99];
        fill(allTrueBool, true);

        // If breakTie is random, this test should pass
        assertFalse(Arrays.equals(allTrueBool, boolsequal));
    }
}
