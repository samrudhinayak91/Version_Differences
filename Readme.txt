General Usage notes:

This program combines the APIs of the Understand software analysis tool and Jgrapht. The objective is to take 2 different versions of an application and print out the differences between them and display the graph that represents the transitive closure.
Input:
udb database files generated using the Understand tool. I have generated some udb files and placed them in the resources folder of the project.
A list of class names to compute the transitive closure.
Output:
The dependency, call and inheritance graphs are printed out before the isomorphism is calculated and the list of differences is displayed in the form of a string. The user inputs for the transitive closure is taken and checked in the inheritance graph for transitive dependencies. A graph with the original inheritance graph along with edges for the transitive closure is printed out as the final output.
 
Implementation notes:

I have used Directed Graphs for the call and dependency graphs and the Simple Graph for the inheritance graph. For the call graphs I have considered all functions and methods that call any other function or method. For the dependency graph I have considered all classes that couple with other classes, method and functions. For the inheritance graph, I have considered all classes and interfaces that implement or extend other interfaces or classes. The getisocall method calculates the graph isomorphism taking the second call graph as a sub set of the first graph, iterates over all possible mappings of it, takes the first mapping and checks for any nodes of the graph that have not been mapped. These would be the differences and would be added to a string ArrayList which is displayed as the list of differences in the call graphs. The getisodep does the same function but takes the dependency graphs into consideration instead f the call graphs. This also checks if no mapping exists between the two versions of the application and prints out a message that indicates that no mapping is present. If the user inputs a second application that is not a subgraph of the first application, the functions return a message indicating that the second graph is not a subgraph of the first since a null pointer exception is thrown. The gettransitiveclosure method uses the inheritance graph and a list of class names to find the transitive closure between the classes. If there exists one, it creates a new graph representing the original graph along with the new edges for the transitive closure. If there  aren’t any transitive dependencies, it returns the inheritance graph as it is.

The MainTest class is the JUnit class for unit testing and integration testing. It has 3 test cases, one for each of the function. If you are running from intelliJ, please replace the project path with the absolute path of the folder in which the udb files are placed in your local system in order to run the tests successfully. 

Compilation notes:

To execute the program, open a terminal and navigate to the folder in which the folder containing the assignment is stored. Then issue the command: 

sbt compile

Once compilation is successful, type the following to run the program:

sbt run

The build starts and it asks you to enter the path to the directory containing the first test application to be used. I have stored mine within my folder, within resources. Type the path at which this the first udb  file can be found and hit enter. The program then prompts for the path to the directory containing the second test application. I have stored the udb for the second application in the same folder. Type the path and hit enter.
The call and dependency graphs along with the differences in the call and dependency graphs are displayed.
The prompt for the class names for transitive closure appears. Type in the class names separated by commas and hit enter. The graph with the transitive closure is displayed.

For the testing, execute the command 

sbt test

This will run the junit class to do the comparison for different test cases for unit and integration testing.

Limitations of my implementation:

Since the sub graph isomorphism takes into account the topology of the mappings and not the actual names of the methods, if the topology is similar for multiple functions or classes, there are several mapping generated. My implementation takes the first mapping to calculate the differences. But with the different mappings, different differences could be found. The set of differences does not reflect the name of the function that is different in the newer version but rather the difference in the topology that is mapped. To get to the exact names, we can iterate through all the mappings but that takes a much longer time since there are n! mappings calculated if there are n nodes in the graph.