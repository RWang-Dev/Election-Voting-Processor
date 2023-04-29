//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//import java.io.FileNotFoundException;
//import java.io.File;
//
//public class CPLFileProcessorTest {
//    CPLFileProcessor processor = new CPLFileProcessor();
//    File null_file = null;
//    FileHandler fh_good = new FileHandler("testCPL.csv");
//
//
//
//    @Test
//    void CPLFileProcessorConstructorTest(){
//        assertNotNull(processor);
//    }
//
//    @Test
//    void processGoodFileTest(){
//        CPLElection good_election = (CPLElection) processor.processFile(fh_good.openFile());
//        assertNotNull(good_election);
//        assertEquals(5, good_election.getNumSeats());
//        assertEquals(40, good_election.getNumBallots());
//        assertEquals(6, good_election.getNumVoteables());
//    }
//
//    @Test
//    void processFileNonExistentTest(){
//        assertThrows(NullPointerException.class, () -> processor.processFile(null_file));
//    }
//}
