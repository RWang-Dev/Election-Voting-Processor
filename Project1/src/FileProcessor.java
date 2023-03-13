// abstract FileProcessor class

import java.io.File;

public abstract class FileProcessor{
    Ballot[] ballots;

    public abstract Election processFile(File inputFile);
}