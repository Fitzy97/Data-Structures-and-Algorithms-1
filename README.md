# Data-Structures-and-Algorithms-1
Work done for Amherst College COSC-201.  Project 1 and 2 deal with sorting, Project 3 with dynamic programming.

# Project 1
Simple exercise in various sorting algorithms (InsertionSort, Mergesort, In-place Mergesort, Iterative Mergesort, Quicksort, Randomized Quicksort, End Quicksort) and their runtimes.
Compile: javac Sort.java
Run: java Sort N
Where N is the number of elements in the array to be sorted.

# Project 2
Comparison of HeapSort with InsertionSort and SelectionSort.  Also an inquiry into the effect that the branching factor of the heap used in heapsort affects the runtime.  See the Project2_Report.docx for more detail.
Compile: javac Heapsort.java
Run: java Heapsort N B
Where N is the number of elements and B is the number of branches for the heap in Heapsort

# Project 3
Dynamic programming exercise that computes the optimal strategy for a game of Pig (see rules: https://www.dicegamedepot.com/dice-n-games-blog/pig-dice-game-rules/), given the current score and the target score (the score needed to win).
Compile: javac PigSolver.java
Run: java PigSolver T x y
Where T is the target score, x is player 1's score, and y is player 2's score.
In the results, number of iterations is a measure of how long the program took to reach it's results.  
