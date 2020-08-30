# Project-1-4-the-oopsies

Welcome to The Oopsies team GitHub page.
Project Members: Daniel Yeom (Leader), Lynette Nguyen, Josh Burton, Kenny Tang, Pranay Jhaveri, and Wong Chong

## Project Description
The aim of this project was to create a program that determines an optimal schedule based on an input .dot file. The program needs to create an optimal schedule
in a reasonable amount of time. As a result, parallel processing is used to increase program efficiency. We used the DFS branch-and-bound algorithm to solve this 
problem. 

## Prerequisites
When running on the command line, you need a working JDK and JavaFX library. The project was run and tested with JDK 8 and Java 11. Please use the tested libraries while running the program.

## Running on Command Line
In order for the command line arguments to work properly, please ensure the prerequisites above are met.
Download the schedule.jar file and place the file in a folder. Place the graph dot file (.dot format) in the same folder as the schedule.jar.
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
### Example on using command Line
With the scheduler.jar and graph.dot file in the same folder, the following runs the graph.dot file with 2 Processors:
```

java -jar scheduler.jar graph.dot 2

```
The following is with the -v optional argument:

![Schedule List] (GUI_1.PNG)

