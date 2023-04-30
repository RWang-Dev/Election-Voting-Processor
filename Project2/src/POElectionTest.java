import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class POElectionTest {
    POFileProcessor processor = new POFileProcessor();
    FileHandler fh_normal_election = new FileHandler("testPO.csv");
    FileHandler fh_two_tied = new FileHandler("testPOTwoTie.csv");
    FileHandler fh_one_candidate = new FileHandler("testPOOneCandidate.csv");
    FileHandler fh_no_ballots = new FileHandler("testPONoBallots.csv");


    @Test
    void runNormalElectionTest() {
        File[] normal_files = new File[] {fh_normal_election.openFile()};
        POElection normal_election = (POElection) processor.processFile(normal_files);
        normal_election.runElection();

        String[] correct = new String[normal_election.getNumVoteables()];
        double[] correctPercentages = {
                100 * ((double) 18 / 40),
                100 * ((double) 18 / 40),
                100 * ((double) 4 / 40),
                100 * ((double) 0 / 40),
                100 * ((double) 0 / 40),
                100 * ((double) 0 / 40),
        };
        POCandidate[] candidates = normal_election.getCandidates();
        for (int i = 0; i < correct.length; i++) {
            correct[i] = candidates[i].getName() + " with " + correctPercentages[i] + "% of the vote.";
        }


        String[] results = normal_election.getResults();
        for (int i = 0; i < results.length; i++) {
            assertTrue(correct[i].equals(results[i]));
        }
    }

    @Test
    void runTwoTiedElectionTest() {
        File[] two_tied_files = new File[] {fh_two_tied.openFile()};
        POElection two_tied_election = (POElection) processor.processFile(two_tied_files);
        two_tied_election.runElection();

        String[] correct = new String[two_tied_election.getNumVoteables()];
        double[] correctPercentages = {
                100 * ((double) 5 / 12),
                100 * ((double) 5 / 12),
                100 * ((double) 1 / 12),
                100 * ((double) 1 / 12),
                100 * ((double) 0 / 12),
                100 * ((double) 0 / 12),
        };
        POCandidate[] candidates = two_tied_election.getCandidates();
        for (int i = 0; i < correct.length; i++) {
            correct[i] = candidates[i].getName() + " with " + correctPercentages[i] + "% of the vote.";
        }


        String[] results = two_tied_election.getResults();
        for (int i = 0; i < results.length; i++) {
            assertTrue(correct[i].equals(results[i]));
        }
    }

    @Test
    void runOneCandidateElectionTest() {
        File[] one_candidate_files = new File[] {fh_one_candidate.openFile()};
        POElection one_candidate_election = (POElection) processor.processFile(one_candidate_files);
        one_candidate_election.runElection();

        String[] correct = new String[one_candidate_election.getNumVoteables()];
        double[] correctPercentages = {
                100 * ((double) 5 / 5),
        };
        POCandidate[] candidates = one_candidate_election.getCandidates();
        for (int i = 0; i < correct.length; i++) {
            correct[i] = candidates[i].getName() + " with " + correctPercentages[i] + "% of the vote.";
        }


        String[] results = one_candidate_election.getResults();
        for (int i = 0; i < results.length; i++) {
            assertTrue(correct[i].equals(results[i]));
        }
    }

    @Test
    void runNoBallotsElectionTest() {
        File[] no_ballots_files = new File[] {fh_no_ballots.openFile()};
        POElection no_ballots_election = (POElection) processor.processFile(no_ballots_files);
        no_ballots_election.runElection();

        String[] correct = new String[no_ballots_election.getNumVoteables()];
        double[] correctPercentages = {
                100 * ((double) 0 / 0),
                100 * ((double) 0 / 0),
                100 * ((double) 0 / 0),
                100 * ((double) 0 / 0),
                100 * ((double) 0 / 0),
        };
        POCandidate[] candidates = no_ballots_election.getCandidates();
        for (int i = 0; i < correct.length; i++) {
            correct[i] = candidates[i].getName() + " with " + correctPercentages[i] + "% of the vote.";
        }


        String[] results = no_ballots_election.getResults();
        for (int i = 0; i < results.length; i++) {
            assertTrue(correct[i].equals(results[i]));
        }
    }
}