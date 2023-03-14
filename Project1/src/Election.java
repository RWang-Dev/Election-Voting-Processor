// Abstract Election class that IRElection and CPLElection inherit from

import java.util.Random;
import java.land.Math;
import java.util.Scanner;

public abstract class Election{
    protected Voteable[] voteables;
    protected int numVoteables;
    protected Ballot[] ballots;
    protected int numBallots;

    public Voteable[] getVoteables() {
        return voteables;
    }

    public int getNumVoteables() {
        return numVoteables;
    }

    public int getNumBallots() {
        return numBallots;
    }

    public abstract void runElection();

    public abstract void printElectionResults();

    public abstract void produceAuditFile();

    // takes in an array of Voteables and randomly returns a winner/loser depending on scenario
    public Voteable breakTie(Voteable[] voteable){
        float rand = 0.0;
        Random r = new Random();

        // randomly generate 1000 floats between 0.0 and 1.0 to ensure randomness
        for (int i = 0; i < 1000; i++){
            r.nextFloat();
        }
        rand = r.nextFloat(); // assign target value

        int[] rand_voteables = new int[voteable.length]; // store each Voteable's randomly generated float
        for (int i = 0; i < voteable.length; i++){ // randomly generate float for all non-null voteables
            if (voteable[i] != null){
                rand_voteables[i] = r.nextFloat();
            }
            else {
                rand_voteables[i] = 5; // large enough number that it won't interfere with 0.0 to 1.0
            }
        }

        // find minimum distance to target rand
        float minDist = 2.0;
        int minIdx = -1;
        for (int i = 0; i < voteable.length; i++){
            float dist = Math.abs(rand - rand_voteables[i]);
            if (dist < minDist){
                minDist = dist;
                minIdx = i;
            }
        }

        return voteable[minIdx]; // return winner/loser
    }

    // prompt user to input date into the console (can be used when creating audit file)
    public String promptUserForDate(){
        Scanner in = new Scanner(System.in);
        // prompt user for input
        System.out.println("Enter the date of the election in the following format (mm/dd/yyyy): ");
        return in.nextLine();
    }
}
