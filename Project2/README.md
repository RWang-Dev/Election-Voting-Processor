This contains the files for the Trusty Vote Counter CPL and IR election vote counter system

The first folder (documentation) in the Project 2 contain the system generated Javadoc HTML files that document the functions inside of the Trusty Vote Counter program.

The src file will contain the main class and all the other parts that build up the whole system, as well as certain files that testing data about both
CPL and IR elections, which you can input the <filename>.csv file when running main to use that as the current election. When running the program, ensure that your .csv file with the election results is placed in the src folder.

The main class will output audit files to your current working directory, and past audit files will be overwritten by the last run of the CPL or IR Election.
Additional print statements have been added to both the IR and CPL Elections to better describe each step of the elections. 

In the testing folder outside of the src folder, there will be testing data as well as the log files and input files used for the unit and system tests.

The src file contains class files and test .csv files for the user to run and test the program if they wish.

IMPORTANT: the program assumes that the working directory is the src folder. If you are running the program with IntelliJ, make sure to change your working directory to repo-Team16\Project2\src before running. This applies to running Main, as well as any tests with JUnit. Otherwise, when entering the filename, you must enter the whole filepath(e.g. Project2/src/testCPL.csv).
