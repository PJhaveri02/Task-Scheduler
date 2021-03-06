# Project-1-4-the-oopsies

Welcome to The Oopsies team GitHub page.
Project Members: Daniel Yeom (Leader), Lynette Nguyen, Josh Burton, Kenny Tang, Pranay Jhaveri, and Wong Chong

## Project Description
The aim of this project was to create a program that determines an optimal schedule based on an input .dot file. The program needs to create an optimal schedule
in a reasonable amount of time. As a result, parallel processing is used to increase program efficiency. We used the DFS branch-and-bound algorithm to solve this 
problem. 

## Acknowledgements
Before continuing, we would like to thank Bruno Silva for allowing us to use JavaFXSmartGraph. JavaFXSmartGraph is a generic (Java FX) graph visualization library that can automatically arrange the vertices' in real-time. JavaFXSmartGraph was used in this project for graph visualisation.<br><br>
<b>For more information please go to the following link:</b> https://github.com/brunomnsilva/JavaFXSmartGraph

## Prerequisites
When running on the command line, you need a working JDK and JavaFX library. The project was run and tested with Java 8 and Java 11. Please use the tested libraries while running the program. In order to use the visualization feature, JavaFX 11 must be downloaded, can be found here: https://openjfx.io/.

## Running on Command Line
In order for the command line arguments to work properly, please ensure the prerequisites above are met.<br>

Download the schedule.jar, JavaFXSmartGraph-0.9.2.jar, smartgraph.css, and smartgraph.properties files and place the files in a folder. Place the graph dot file (.dot format) in the same folder as the schedule.jar, JavaFXSmartGraph-0.9.2.jar, smartgraph.css, and smartgraph.properties files.<br>

<b>If you are running the program with Java 11 then:</b> "--module-path C:\Path\To\javafx\javafx-sdk-11.0.2\lib --add-modules javafx.base,javafx.controls,javafx.media,javafx.graphics, javafx.fxml" is required (with no quotes) after the java command in order to run. Please also place the javaFX SDK into the folder with the corresponding scheduler.jar and .dot format.<br><br>
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
With the scheduler.jar, JavaFXSmartGraph-0.9.2.jar, smartgraph.css, smartgraph.properties, and graph.dot file in the same folder, the following runs the graph.dot file with 2 processors:<br>
<b>Note:</b> The first method is used if you have Java 11 and the second method is used if you have Java 8.
```
java --module-path C:\Path\To\javafx\javafx-sdk-11.0.2\lib --add-modules javafx.base,javafx.controls,javafx.media,javafx.graphics,javafx.fxml -jar scheduler.jar Nodes_8_Random.dot 3 -p 4 -v
java -jar scheduler.jar graph.dot 2

```
The same call as above, but with the -v optional argument:
```

java -jar scheduler.jar graph.dot 2 -v

```
The following windows are produced to visualise during the execution of the program:
![Schedule List](GUI_2.PNG)
Shows the schedule with the task under each processor.

![Visualise_Input_Graph](GUI_1.PNG)
Shows a visualisation of the graph, with nodes as circles and edges as lines.

