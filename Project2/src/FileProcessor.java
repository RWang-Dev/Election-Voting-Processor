// author: Alex Iliarski (iliar004)

import java.io.File;

/**
 * Abstract FileProcessor from which IRFileProcessor and CPLFileProcessor inherit
 */
public abstract class FileProcessor{
    Ballot[] ballots;

    /**
     * Abstract method that returns Election object for a given input CSV Files containing election information
     * @param inputFiles An array of File objects for the input CSV files containing election information
     * @return An Election object containing all the information gathered from the input files
     */
    public abstract Election processFile(File[] inputFiles);
}