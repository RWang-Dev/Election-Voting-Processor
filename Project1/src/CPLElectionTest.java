import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPLElectionTest {
    // Tested file handler already, so setup should work for reading in files
    CPLFileProcessor fileProcessor = new CPLFileProcessor();
    CPLElection normal_election = (CPLElection)
            fileProcessor.processFile((new FileHandler("testCPL.csv").openFile()));
    CPLElection no_ballots_election = (CPLElection)
            fileProcessor.processFile((new FileHandler("testCPLNoBallots.csv").openFile()));


    @BeforeAll
    public static void init(){
//        parties[0].incrementVotes(1);
//        parties[1].incrementVotes(2);
    }

    @BeforeEach
    public void re_init(){

    }

    @Test
    void CPLElectionConstructorTest(){

    }

//    @Test
//    void CPLElectionConstructorBadValuesTest(){
//
//    }

    @Test
    void CPLGetNumSeatsTest(){

    }

    @Test
    void CPLGetResultsTest(){

    }



    @Test
    void firstSeatAllocWithNiceInputTest(){

    }

    @Test
    void secondSeatAllocWithNiceInputTest(){

    }


    @Test
    void clearArrayTest(){
//        good_election.clearArray( (CPLParty[]) good_election.getVoteables());
//        assertArrayEquals(new CPLParty[3], good_election.getVoteables());
//        good_election.clearArray( (CPLParty[]) good_election.getVoteables());
//        assertArrayEquals(new CPLParty[3], good_election.getVoteables());

    }
}