import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.util.LinkedList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;



class IRElectionTest {
    IRFileProcessor processor = new IRFileProcessor();

    File[] regular_election_files = new File[] {new FileHandler("testIRMain.csv").openFile()} ;
    File[] no_majority_files = new File[] {new FileHandler("testIRNoMajorityWinner.csv").openFile()} ;
    File[] one_ballot_files = new File[] {new FileHandler("testIROneBallot.csv").openFile()} ;
    File[] one_cand_files = new File[] {new FileHandler("testIROneCandidate.csv").openFile()} ;
    File[] tied_cands_files = new File[] {new FileHandler("testIRTiedCandidates.csv").openFile()} ;


    File[] all_tied_files = new File[] {new FileHandler("testIRAllTied.csv").openFile()} ;
    File[] all_tied_mult = new File[] {new FileHandler("testIRTied_file1.csv").openFile(), new FileHandler("testIRTied_file2.csv").openFile(),
            new FileHandler("testIRTied_file3.csv").openFile(),new FileHandler("testIRTied_file4.csv").openFile()} ;

    File[] test_IR = new File[] {new FileHandler("testIR.csv").openFile()};
    File[] testIR_mult = new File[] {new FileHandler("testIR_file1.csv").openFile(), new FileHandler("testIR_file2.csv").openFile(),
            new FileHandler("testIR_file3.csv").openFile()} ;



    IRElection testElection = (IRElection) processor.processFile(regular_election_files);
    IRElection testIRNoMajorityWinner = (IRElection) processor.processFile(no_majority_files);
    IRElection testIROneBallot = (IRElection) processor.processFile(one_ballot_files);
    IRElection testIROneCandidate = (IRElection) processor.processFile(one_cand_files);
    IRElection testIRTiedCandidates = (IRElection) processor.processFile(tied_cands_files);

    IRElection testIRAllTied = (IRElection) processor.processFile(all_tied_files);
    IRElection testIRAllTiedMult = (IRElection) processor.processFile(all_tied_mult);


    IRElection testIRMult = (IRElection) processor.processFile(testIR_mult);
    IRElection testIR = (IRElection) processor.processFile(test_IR);



    IRCandidate Rosen = new IRCandidate("Rosen (D)",0);
    IRCandidate Kleinberg = new IRCandidate("Kleinberg (R)",0);
    IRCandidate Chou = new IRCandidate("Chou (I)",0);
    IRCandidate Royce = new IRCandidate("Royce (L)",0);

    @Test
    void getNumCandidates() {
        int numCandidates = testElection.getNumCandidates();
        assertEquals(4, numCandidates);
        numCandidates = testIRTiedCandidates.getNumCandidates();
        assertEquals(4, numCandidates);
        numCandidates = testIRNoMajorityWinner.getNumCandidates();
        assertEquals(4, numCandidates);
        numCandidates = testIROneCandidate.getNumCandidates();
        assertEquals(1, numCandidates);
        numCandidates = testIROneBallot.getNumCandidates();
        assertEquals(2, numCandidates);


    }

    @Test
    void getNumBallots() {
        int numBallots = testElection.getNumBallots();
        assertEquals(6, numBallots);
        numBallots = testIROneCandidate.getNumBallots();
        assertEquals(5, numBallots);
        numBallots = testIROneBallot.getNumBallots();
        assertEquals(1, numBallots);
        numBallots = testIRNoMajorityWinner.getNumBallots();
        assertEquals(10, numBallots);
    }

    @Test
    void getCandidates() {
        Voteable[] resCandidates = testElection.getCandidates();
        IRCandidate[] expCandidates = new IRCandidate[]{Rosen, Kleinberg, Chou, Royce};
        for(int i = 0; i<expCandidates.length; i++){
            assertEquals(expCandidates[i].getName(), resCandidates[i].getName());
        }
        resCandidates = testIROneCandidate.getCandidates();
        expCandidates = new IRCandidate[]{Rosen};
        for(int i = 0; i<expCandidates.length; i++){
            assertEquals(expCandidates[i].getName(), resCandidates[i].getName());
        }
        resCandidates = testIRTiedCandidates.getCandidates();
        expCandidates = new IRCandidate[]{Rosen, Royce};
        for(int i = 0; i<expCandidates.length; i++){
            assertEquals(expCandidates[i].getName(), resCandidates[i].getName());
        }



    }

    @Test
    void eliminateCandidate() {
        testElection.eliminateCandidate();
        IRCandidate[] expCandidates = new IRCandidate[]{Rosen, Kleinberg, Chou, Royce};
        for(int i = 0; i < 4; i++){

            assertEquals(expCandidates[i].getName(), testElection.getCandidates()[i].getName());
        }
        assertEquals(-1, testElection.getCandidates()[testElection.getNumCandidates()-1].getNumVotes());
        assertEquals(-1, testElection.getCandidates()[testElection.getNumCandidates()-1].getNumVotes());
        testElection.eliminateCandidate();
        for(int i = 0; i < 4; i++){
            assertEquals(expCandidates[i].getName(), testElection.getCandidates()[i].getName());
        }
        assertEquals(4, testElection.getCandidates()[0].getNumVotes());
        assertThrows(IllegalCallerException.class, () -> testIROneCandidate.eliminateCandidate());

        expCandidates = new IRCandidate[]{Rosen};
        for(int i = 0; i < testIROneCandidate.getNumCandidates(); i++){

            assertEquals(expCandidates[i].getName(), testElection.getCandidates()[i].getName());
        }

    }

    @Test
    void runElection() {
        testElection.runElection();
        assertEquals(Rosen.getName(), testElection.getCandidates()[0].getName());

        testIROneCandidate.runElection();
        assertEquals(Rosen.getName(), testElection.getCandidates()[0].getName());

        testIROneBallot.runElection();
        assertEquals(Rosen.getName(), testElection.getCandidates()[0].getName());

        // Testing multiple file input
        testIRMult.runElection();
        assertEquals(Royce.getName(), testIRMult.getCandidates()[0].getName());

        testIR.runElection();
        assertEquals(Royce.getName(), testIR.getCandidates()[0].getName());

        int ctRosen = 0;
        int ctKleinberg = 0;
        int ctChou = 0;
        int ctRoyce = 0;


        for(int i = 0; i<1000; i ++){
            File[] test_tied_cands_files = new File[] {new FileHandler("testIRTiedCandidates.csv").openFile()};
            testIRTiedCandidates = (IRElection) processor.processFile(test_tied_cands_files);

            testIRTiedCandidates.runElection();


            if(testIRTiedCandidates.getCandidates()[0].getName().equals(Rosen.getName())){
                ctRosen ++;
            }else if(testIRTiedCandidates.getCandidates()[0].getName().equals(Kleinberg.getName())) {
                ctKleinberg ++;
            }else if(testIRTiedCandidates.getCandidates()[0].getName().equals(Chou.getName())) {
                ctChou ++;
            }else if(testIRTiedCandidates.getCandidates()[0].getName().equals(Royce.getName())) {
                ctRoyce ++;
            }
        }

        assertTrue(ctRosen < 650 && ctRosen > 400);
        assertTrue(ctRoyce < 650 && ctRoyce > 400);

        ctRosen = 0;
        ctKleinberg = 0;
        ctChou = 0;
        ctRoyce = 0;
        for(int i = 0; i<1000; i++){
            File[] test_all_tied_files = new File[] {new FileHandler("testIRAllTied.csv").openFile()};
            testIRAllTied = (IRElection) processor.processFile(test_all_tied_files);
            testIRAllTied.runElection();
            if(testIRAllTied.getCandidates()[0].getName().equals(Rosen.getName())){
                ctRosen ++;
            }else if(testIRAllTied.getCandidates()[0].getName().equals(Kleinberg.getName())) {
                ctKleinberg ++;
            }else if(testIRAllTied.getCandidates()[0].getName().equals(Chou.getName())) {
                ctChou ++;
            }else if(testIRAllTied.getCandidates()[0].getName().equals(Royce.getName())) {
                ctRoyce ++;
            }
        }

        System.out.println(ctRosen);
        System.out.println(ctKleinberg);
        System.out.println(ctChou);
        System.out.println(ctRoyce);
        assertTrue(ctRosen < 400 && ctRosen > 200);
        assertTrue(ctKleinberg < 400 && ctKleinberg > 200);
        assertTrue(ctChou < 400 && ctChou > 200);
        assertTrue(ctRoyce < 400 && ctRoyce > 200);

        ctRosen = 0;
        ctKleinberg = 0;
        ctChou = 0;
        ctRoyce = 0;
        for(int i = 0; i<1000; i++){
            File[] all_tied_mult = new File[] {new FileHandler("testIRTied_file1.csv").openFile(), new FileHandler("testIRTied_file2.csv").openFile(),
                    new FileHandler("testIRTied_file3.csv").openFile(),new FileHandler("testIRTied_file4.csv").openFile()} ;

            testIRAllTied = (IRElection) processor.processFile(all_tied_mult);
            testIRAllTied.runElection();
            if(testIRAllTied.getCandidates()[0].getName().equals(Rosen.getName())){
                ctRosen ++;
            }else if(testIRAllTied.getCandidates()[0].getName().equals(Kleinberg.getName())) {
                ctKleinberg ++;
            }else if(testIRAllTied.getCandidates()[0].getName().equals(Chou.getName())) {
                ctChou ++;
            }else if(testIRAllTied.getCandidates()[0].getName().equals(Royce.getName())) {
                ctRoyce ++;
            }
        }

        System.out.println(ctRosen);
        System.out.println(ctKleinberg);
        System.out.println(ctChou);
        System.out.println(ctRoyce);
        assertTrue(ctRosen < 400 && ctRosen > 200);
        assertTrue(ctKleinberg < 400 && ctKleinberg > 200);
        assertTrue(ctChou < 400 && ctChou > 200);
        assertTrue(ctRoyce < 400 && ctRoyce > 200);


    }

    @Test
    void printElectionResults() {

    }

    @Test
    void produceAuditFile() {
    }
}