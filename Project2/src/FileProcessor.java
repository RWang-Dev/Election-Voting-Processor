// author: Alex Iliarski (iliar004)

import java.io.File;

/**
 * Abstract FileProcessor from which IRFileProcessor and CPLFileProcessor inherit
 */
public abstract class FileProcessor{
    Ballot[] ballots;

    /**
     * Abstract method that returns Election object for a given input CSV File containing election information
     * @param inputFile A File object for the input CSV file containing election information
     * @return An Election object containing all the information gathered from the input file
     */
    public abstract Election processFile(File inputFile);
}