# Hashing-Algorithms
Optimized implementations of different hashing algorithms - Linear probing, Double hashing, Chained hashing, Cuckoo hashing 

The 4 hashing algorithms – Linear Probing, Chained Hashing, Cuckoo Hashing and Quadratic Probing have been implemented in the JAVA programming language.

All hashing algorithms implement the IHashingAlgorithm interface which include 4 methods – set, search, delete and hash.

The Entry class defines the structure of the object that is used for operations on the hashtable. It has two data members – an Integer key and an Integer value.

## Build instructions:
- Compile the files using the command: `javac *.java`
- To test the hashing algorithms use the following commands:

  `java hashingAlgorithms.Main dataPoints intervalSize numTrials hashingAlgorithm`

For example: 

`java hashingAlgorithms.Main 100 1000 100 1 `

`java hashingAlgorithms.Main 10 10 100 2`

where,

- dataPoints – No. of input sizes to run the algorithm for
- intervalSize – The step by which the input size increases 
- numTrials – All operations are averaged over a no. of trials 
- hashingAlgorithm -> \
1 – Chained Hashing \
2 – Linear Probing \
3 – Quadratic Probing  
4 – Cuckoo Hashing 
