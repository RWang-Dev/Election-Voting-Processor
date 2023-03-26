// Abstractly represents an Election type and contains its algorithms
// author: Alex Iliarski (iliar004(

import java.util.Random;
import java.lang.Math;
import java.util.Scanner;

/**
 * Abstractly represents an Election
 */
public abstract class Election{
    protected Voteable[] voteables;
    protected int numVoteables;
    protected Ballot[] ballots;
    protected int numBallots;

    /**
     * Ggets the list of all Voteable objects in an election
     * @return A Voteable[] of all votable objects in an election
     */
    public Voteable[] getVoteables() {
        return voteables;
    }

    /**
     * Gets the number of Vateable objects in an election
     * @return An int representing the number of Voteable objects in an election
     */
    public int getNumVoteables() {
        return numVoteables;
    }

    /**
     * gets the number of ballots cast in an election
     * @return An int representing the number of ballots cast in an election
     */
    public int getNumBallots() {
        return numBallots;
    }

    public Ballot[] getBallots() { return this.ballots; }

    /**
     * Runs the election algorithm, determining the winner(s)
     */
    public abstract void runElection();

    /**
     * Prints the results of the election to the console
     */
    public abstract void printElectionResults();

    /**
     * Creates the auditfile.txt file in the same directory, containing the results of the election
     */
    public abstract void produceAuditFile();

    /**
     * Takes in an array of Voteables and randomly returns an index of the winner/loser depending on scenario
     * @param voteable The list of voteables that needs to have a tie broken for
     * @return An int representing the index of the input array of the Voteable that is designated as the winner/loser in a tied scenario
     */
    public int breakTie(Voteable[] voteable){
        if(voteable.length < 1){
            return -1;
        }

        double rand = 0.0;
        Random r = new Random();

        // randomly generate 1000 doubles between 0.0 and 1.0 to ensure randomness
        for (int i = 0; i < 1000; i++){
            r.nextDouble();
        }
        rand = r.nextDouble(); // assign target value

        double[] rand_voteables = new double[voteable.length]; // store each Voteable's randomly generated double
        for (int i = 0; i < voteable.length; i++){ // randomly generate double for all non-null voteables
            if (voteable[i] != null){
                rand_voteables[i] = r.nextDouble();
            }
            else {
                rand_voteables[i] = 5; // large enough number that it won't interfere with 0.0 to 1.0
            }
        }

        // find minimum distance to target rand
        double minDist = 2.0;
        int minIdx = -1;
        for (int i = 0; i < voteable.length; i++){
            double dist = Math.abs(rand - rand_voteables[i]);
            if (dist < minDist || minIdx == -1){
                minDist = dist;
                minIdx = i;
            }
        }

        return minIdx; // return index of winner/loser
    }

    // TODO:: Do we actually use this function?
    /**
     * prompt user to input date into the console (can be used when creating audit file)
     * @return A String representing the date of the election in (mm/dd/yyyy) format
     */
    public String promptUserForDate(){
        Scanner in = new Scanner(System.in);
        // prompt user for input
        System.out.println("Enter the date of the election in the following format (mm/dd/yyyy): ");
        return in.nextLine();
    }
}
