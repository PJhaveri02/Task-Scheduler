# Project-1-4-the-oopsies
project-1-4-the-oopsies created by GitHub Classroom

Welcome to The Oopsies team github page.
Project by Leader Daniel Yeom, Lynette Nguyen, Josh Burton, Kenny Tang, Pranay Jkaveri, Wong Chong

<h3> Prerequisites </h3>
When running on an IDE, you need a working JDK and JavaFX library. The project was ran and tested with JDK 8 and Java 11. Please use the tested.

## Running on Command Line
In order for the command line arguments to work properly, please ensure the prerequisites above are met.
Download the schedule.jar file and place the file in a folder. Place the graph dot file (.dot) in the same folder as the schedule.jar.
The following is the structure of the command line, with optional arguments.
```
java -jar scheduler.jar INPUT.dot P [OPTION]
INPUT.dot a task graph with integer weights in dot format
P         number of processors to schedule the INPUT graph on
Optional:
-p N      use N cores for execution in parallel (default is sequential)
-v        visualise the search
-o OUTPUT output file is named OUTPUT (default is INPUT-output.dot)  
```


