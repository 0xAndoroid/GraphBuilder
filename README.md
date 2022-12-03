# GraphBuilder
An application to create graphs in a visual manner, those graphs can be exported into other representations. It has been primarily used to create tests for competitive programming problems involving graphs.  
## Execution
You would need a JVM installed on your machine to run this project, as it is written in Java.  
Simply [download](https://github.com/AndoroidX/GraphBuilder/releases/tag/1.0.0) the latest release from github in .jar or .exe format and run. The application does not require installation.

## Creating a graph
Here's application's GUI with a simple graph built.  
![GUI](https://i.imgur.com/zUvjhEn.png)
Upon running the application, you would need to select "Vertices" in the bottom left corner. And then you can click on any part of the screet to create a vertex.
![selecting vertices](https://i.imgur.com/SgkreUb.png)
To create an edge, you would need to select "Edges" in the bottom left corner. Then click on two vertices that would be connected by the edge.
![edging](https://i.imgur.com/QdOUYB4.png)
![edging2](https://i.imgur.com/AFhQFAL.png)

## Exporting
After you create a graph, you can export it into a file. The options are
- List of edges
- Adjacency matrix
- Adjacency list

Select the method from the dropdown menu, and click Export. The graph data will be saved into a graph.txt file located in the same folder as the application or runtime environment.
