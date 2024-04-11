# Interactive-Books-Analyser
There are some books in which you are the hero and you have to navigate between paragraphs, depending on your choices. The goal of this project is to provide a readable graph of the book, where the vertices are the paragraphs, and the edges are the links between these paragraphs. With 2 fellow students, we made a lot of researches about graph vizualisation, and manipulated a bunch of graph algorithms, such as Dijkstra, Floyd-Warshall, or Kamada-Kawai. Unfortunately we didn't success to implement the vizualisation by ourselves, and we finally fell back on the JUNG library, which provides a correct implementation of Kamada-Kawai algorithm. Nonetheless, we studied this difficult subject with passion. I encourage you to read the report of the project if you need more details.


To launch a vizualisation :

Go to src folder ->
$ cd src 

Compile ->
$ javac -d "../build" -cp ".:../lib/jung/algos.jar:../lib/jung/api.jar:../lib/jung/collections.jar:../lib/jung/graph_api.jar:../lib/jung/vizualization.jar" vue/*.java

Execute -> 
$ java -cp "../build:../lib/jung/algos.jar:../lib/jung/api.jar:../lib/jung/collections.jar:../lib/jung/graph_api.jar:../lib/jung/vizualization.jar" vue.Demo

![Alt text](image/demoAnalyse.png?raw=true "Title")
