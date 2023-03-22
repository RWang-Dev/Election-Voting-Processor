import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;

import static org.junit.jupiter.api.Assertions.*;

public class CPLElectionTest {
    static CPLElection good_election;
    static CPLElection no_seats_election;
    static CPLElection no_ballots_election;
    static String[] candidates_empty = {};
    static String[] candidates_1 = {"solo candidate"};
    static String[] candidates_2 = {"Alice", "Bob"};
    static CPLParty[] parties = {new CPLParty("party_1", 1, candidates_1), new CPLParty("party_2", 2, candidates_2),
                    new CPLParty("party_empty", 3, candidates_empty)};
    static CPLParty[] parties_to_empty = {new CPLParty("party_1", 1, candidates_1), new CPLParty("party_2", 2, candidates_2),
            new CPLParty("party_empty", 3, candidates_empty)};
    static CPLBallot[] ballots = {new CPLBallot(parties[0]), new CPLBallot(parties[1]), new CPLBallot(parties[1])};
    static CPLBallot[] ballots_empty = {};

    @BeforeAll
    public static void init(){
        parties[0].incrementVotes(1);
        parties[1].incrementVotes(2);
    }

    @BeforeEach
    public void re_init(){
        good_election = new CPLElection(parties, ballots, ballots.length, 2);
        no_seats_election = new CPLElection(parties, ballots, ballots.length, 0);
        no_ballots_election = new CPLElection(parties, ballots_empty, ballots_empty.length, 2);
    }

    @Test
    void CPLElectionConstructorTest(){
        assertNotNull(good_election);
    }

//    @Test
//    void CPLElectionConstructorBadValuesTest(){
//
//    }

    @Test
    void CPLGetNumSeatsTest(){
        assertEquals(2, good_election.getNumSeats());
        assertEquals(0, no_seats_election.getNumSeats());
    }

    @Test
    void CPLGetResultsTest(){
        assertArrayEquals(new String[2], good_election.getResults());
        assertArrayEquals(new String[0], no_seats_election.getResults());
    }

    @Test
    void clearArrayTest(){
        good_election.clearArray( (CPLParty[]) good_election.getVoteables());
        assertArrayEquals(new CPLParty[3], good_election.getVoteables());
        good_election.clearArray( (CPLParty[]) good_election.getVoteables());
        assertArrayEquals(new CPLParty[3], good_election.getVoteables());
    }

    @Test
    void firstSeatAllocWithNiceInputTest(){
        int ret_val = good_election.firstSeatAlloc(2, 0);
        assertEquals(1, ret_val);
    }

    @Test
    void secondSeatAllocWithNiceInputTest(){

    }
}
