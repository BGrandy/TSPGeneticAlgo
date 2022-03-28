import javax.swing.*;
import java.io.*;
import java.util.*;

/*
This program was coded in java using Intellij IDE.
The main purpose of this program is to introduce the idea of coding genetic algorithms. Using a GA estimate the optimal route. This is the heart of the program and does all of the computations
This is piece 2/2 of the code, first being the handler which runs this class.
This file will output to a folder named DataDumpForReport located where ever this folder is located. You can delete the folder to rerun the program and get results. I have also modified the code to recieve
outputs easier to import into excel for the report portion of this assignment. These text files start with EXL for indication.

 */
public class GA {
    Random rand;
    FileWriter file;
    BufferedWriter output;


    //This method receives all the data from a file being read into. This constructor is called from another file to start the process of the GA.
    public GA(int POPSize, int generations, int k, int crossoverRate, int typeOfCrossover, int mutationRate, int seed, int elitism, double[][] startingTSP) throws IOException {

        //create a directory to save the outputs of the file to
        try {
            String directoryName = "./DataDumpForReport\\";
            String fileName = "TS" + k + "CR" + crossoverRate + "TOC" + typeOfCrossover + "MR" + mutationRate + "S" + seed + "E" + elitism + ".txt"; //name of created file
            new File(directoryName).mkdirs();
            file = new FileWriter(directoryName + fileName);
            output = new BufferedWriter(file);
            String writeThis = "POPSize: " + POPSize + '\n' + "Generations: " + generations + '\n' + "Tournament Selection amount: " + k + '\n' + "CrossoverRate: " + crossoverRate + '\n' + "type Of Crossover (1 is UOXwBM 2 is one point): " + typeOfCrossover + '\n' + "Mutation rate: " + mutationRate + '\n' + "Number of chromosomes carried over due to Elitism: " + elitism + '\n' + "Seed: " + seed + '\n';// description of the file at the beginning of the file
            output.write(writeThis);
            output.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


        rand = new Random(seed);
        double[][] TSP = startingTSP;
        int[][] order = initializeOrder(POPSize, TSP.length);
        for (int j = 0; j < POPSize; j++) {
            order[j] = randomizeArray(order[j], 20);
        }
        double averageFitness = 0;
        //calculate average fitness of the population
        for (int i = 1; i <= generations; i++) {
            for (int j = 0; j < POPSize; j++) {
                averageFitness += testFitness(TSP, order[j]);
            }
            averageFitness = averageFitness / POPSize;
            int index[] = new int[elitism];
            fillArray(index);
            for (int j = 0; j < index.length; j++) {
                //get a certain percentage of the top most fit chromosomes and save their indices.
                index[j] = elitism(POPSize, TSP, order, index);
            }
            int[][] nextOrder = new int[order.length][order[0].length];
            fillArray(nextOrder);
            for (int j = 0; j < index.length; j++) {
                nextOrder[j] = order[index[j]]; //pass our best chromosome onto the next gen. to ensure we get at least the same result at each generation
            }
            //write to file
            String writeThis = "Best Fitness: " + testFitness(TSP, nextOrder[0]) + " Average fitness: " + averageFitness;
            try {
                output.write(writeThis);
                output.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            nextOrder = selection(order, nextOrder, TSP, k, crossoverRate, typeOfCrossover, mutationRate);
            order = nextOrder;
        }

        output.newLine();

        //write to file
        String writeThis = "Best solution fitness: " + testFitness(TSP, order[0]) + " ";
        output.write(writeThis);
        output.write("Best Chromosome: [");
        for (int i = 0; i < order[0].length; i++) {
            int writeThisInt = order[0][i];
            writeThis = Integer.toString(writeThisInt);
            output.write(writeThis);
            if (i != (order[0].length - 1)) {
                output.write(",");
            }
        }
        output.write("]");


        output.flush();
    }


    //This method handles tournament selection then sends those parents to crossover if the random chance allows it. The children then are passed to the mutation function if the random chance allows it
    //If both fails return the parent chromosomes untouched.
    public int[][] selection(int[][] order, int[][] nextOrder, double[][] TSP, int k, int crossoverRate, int typeOfCrossover, int mutationRate) {
        while (arrayHasSpace(nextOrder)) {
            int[] tmpParent, parent1 = new int[order[0].length], parent2 = new int[order[0].length];
            int[][] children = new int[2][parent1.length];
            double parentF1 = Double.MAX_VALUE, parentF2 = Double.MAX_VALUE;
            //get best 2 parents out of k choices from population
            for (int i = 0; i < k; i++) {
                tmpParent = order[rand.nextInt(order.length)];
                if (parentF1 == Double.MAX_VALUE || parentF2 != Double.MAX_VALUE & testFitness(TSP, tmpParent) < parentF1) {
                    parent1 = tmpParent;
                    parentF1 = testFitness(TSP, tmpParent);
                } else if (parentF2 == Double.MAX_VALUE || testFitness(TSP, tmpParent) < parentF1) {
                    parent2 = tmpParent;
                    parentF2 = testFitness(TSP, tmpParent);
                }
            }
            //chance to send children to crossover depending on crossover rate
            if (rand.nextInt(100) < crossoverRate) {
                if (typeOfCrossover == 1) {
                    //uniform order crossover with bitmask index
                    children = UOXwBCrossover(parent1, parent2);
                } else {
                    //one point crossover
                    children = onePointCrossover(parent1, parent2);
                }
            } else {
                //if crossover fails the children copy the parents chromosomes
                children[0] = copyArray(parent1);
                children[1] = copyArray(parent2);
            }
            //if the random chance allows it to send the children to mutate.
            if (rand.nextInt(100) < mutationRate) {
                children[0] = scrambleMutation(children[0]);
            }
            if (rand.nextInt(100) < mutationRate) {
                children[1] = scrambleMutation(children[1]);
            }

            //Repair the children chromosomes.
            children[0] = repairChromosomes(children[0], parent2);
            children[1] = repairChromosomes(children[1], parent1);
            //Add the children to the next population
            nextOrder = addChildrenToArray(children, nextOrder);

        }

        return nextOrder;
    }

    //This method is our mutation function. Take a subset of the child and randomize it's order. Then place the subset back into the child and the same place it took it from
    public int[] scrambleMutation(int[] child) {
        boolean correctOrder;
        int subsetIndex1 = rand.nextInt(child.length);
        int subsetIndex2 = rand.nextInt(child.length);
        int[] tmpArray;
        int[] result = copyArray(child);
        if (subsetIndex1 == subsetIndex2) {
            return result;
        }

        //if the first random index is smaller than the first index it's in the correct order
        if (subsetIndex1 < subsetIndex2) {
            correctOrder = true;
        } else {
            correctOrder = false;
        }

        if (correctOrder) {
            tmpArray = copyArrayRange(child, subsetIndex1, subsetIndex2);
        } else {
            tmpArray = copyArrayRange(child, subsetIndex2, subsetIndex1);
        }

        tmpArray = randomizeArray(tmpArray, 20);

        int count = 0;
        //copy the randomized subset back into the child array
        do {
            if (correctOrder) {
                result[subsetIndex1] = tmpArray[count];
                subsetIndex1++;
            } else {
                result[subsetIndex2] = tmpArray[count];
                subsetIndex2++;
            }
            count++;
        } while (subsetIndex1 != subsetIndex2);


        //return the mutated child
        return result;
    }


    //This function works by taking a random integer and swapping parent 1 and parent 2's chromosomes after this number
    public int[][] onePointCrossover(int[] parent1, int[] parent2) {
        int[][] children = new int[2][parent1.length];
        int[] child1, child2;
        child1 = copyArray(parent1);
        child2 = copyArray(parent2);


        int point = rand.nextInt(parent1.length);
        //copy the opposites parents chromosome after the random index
        for (int i = point; i < parent1.length; i++) {
            child1[i] = parent2[i];
            child2[i] = parent1[i];
        }

        children[0] = copyArray(child1);
        children[1] = copyArray(child2);

        //return children that were crossed over
        return children;
    }

    //This function works by creating a bitmask. If the bitmask at any index is a 1 the child takes the opposites parents gene at that index. if it's a 0 and the city doesn't already exist
    //in the child array then add it.
    public int[][] UOXwBCrossover(int[] parent1, int[] parent2) {
        int[] bitmask = new int[parent1.length];
        for (int i = 0; i < parent1.length; i++) {
            bitmask[i] = rand.nextInt(2);
        }
        int[][] children = new int[2][parent1.length];
        int[] child1 = new int[parent1.length], child2 = new int[parent1.length];

        fillArray(child1);
        fillArray(child2);

        //take opposite parents gene at index i
        for (int i = 0; i < bitmask.length; i++) {
            if (bitmask[i] == 1) {
                child1[i] = parent2[i];
                child2[i] = parent1[i];
            }
        }
        //if bitmask is 0 and the city at the parent isn't already in the chromosome add it.
        for (int i = 0; i < bitmask.length; i++) {
            if (bitmask[i] == 0) {
                if (!existsInArray(child1, parent2[i])) {
                    child1[i] = parent1[i];
                }
                if (!existsInArray(child2, parent1[i])) {
                    child2[i] = parent2[i];
                }
            }
        }

        children[0] = child1;
        children[1] = child2;
        return children;
    }

    //This function adds both children into the next generations population. if only 1 slot if available the first child takes it and the second child's chromosome is discarded
    public int[][] addChildrenToArray(int[][] children, int[][] nextOrder) {
        int count = 0;
        for (int i = 0; i < nextOrder.length; i++) {
            if (nextOrder[i][0] == -1) {
                if (count == 0) {
                    nextOrder[i] = children[0];
                    count++;
                } else {
                    nextOrder[i] = children[1];
                    break;
                }
            }
        }
        return nextOrder;
    }


    //This function creates a new array and copies the value into it. Then returns the new array
    public int[] copyArray(int[] copyFrom) {
        int[] copyTo = new int[copyFrom.length];
        for (int i = 0; i < copyFrom.length; i++) {
            copyTo[i] = copyFrom[i];
        }
        return copyTo;
    }

    //Copies a subset of the array. Used in 1-point crossover
    public int[] copyArrayRange(int[] array, int from, int to) {
        int[] smallChild = new int[to - from];
        for (int i = 0; i < smallChild.length; i++) {
            smallChild[i] = array[i];
        }
        return smallChild;
    }

    //This function repairs the child's array. first checked if there's an empty slot and fills it. Then checks for duplicate citys and replaces the second one.
    public int[] repairChromosomes(int[] child1, int[] child2) {

        int[] fixedChild1 = copyArray(child1);
        //repair if the chromosome has an empty slot
        if (arrayHasSpace(fixedChild1)) {
            for (int i = 0; i < child1.length; i++) {
                outerloop:
                {
                    if (fixedChild1[i] == -1) {
                        for (int j = 0; j < child2.length; j++) {
                            if (!existsInArray(fixedChild1, child2[j])) {
                                fixedChild1[i] = child2[j];
                                break outerloop;
                            }
                        }
                    }
                }
            }
        }

        //repair if the chromosome has a duplicate
        int count = 0;
        for (int i = child1.length - 1; i >= 0; i--) {
            for (int j = child1.length - 1; j >= 0; j--) {
                outerloop:
                {
                    if (fixedChild1[i] == fixedChild1[j]) {
                        count++;
                        if (count > 1) {
                            for (int k = 0; k < child1.length; k++) {
                                if (!existsInArray(fixedChild1, child2[k])) {
                                    fixedChild1[j] = child2[k];
                                    break outerloop;
                                }
                            }
                        }
                    }
                }
            }
            count = 0;
        }
        return fixedChild1;
    }

    //this function checks if a number exists in an array. returns true if the number exists in the array and false otherwise
    public boolean existsInArray(int[] array, int thisNumb) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == thisNumb) {
                return true;
            }
        }
        return false;
    }

    //This function checks if there is space in a array. A space in a array is denoted by -1 Since new arrays are initalized with 0's and 0 is a city in our example.
    public boolean arrayHasSpace(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i][0] == -1) {
                return true;
            }
        }
        return false;
    }

    //checks if there's an empty slot in our array. empty slots are denoted by -1
    public boolean arrayHasSpace(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == -1) {
                return true;
            }
        }
        return false;
    }

    //This function works by getting the top fitness in the population and returns an array with it added. This function can be called multiple times to get a percentage of the
    //top fitness values. By ustilizing a function already implemented existsinarray to ensure we get multiple chromosomes
    public int elitism(int POPSize, double[][] TSP, int[][] order, int[] index) {
        int bestIndex = 0;
        double bestFitness = Double.MAX_VALUE;
        for (int i = 0; i < POPSize; i++) {
            double curFitness = testFitness(TSP, order[i]);
            if (curFitness < bestFitness & !existsInArray(index, i)) {
                bestFitness = curFitness; //if current fitness is better than all previous fitness's then the best fitness is our current fitness (For Elitism)
                bestIndex = i; //save the index of the bestFitness to ensure it gets passed onto the next gen.
            }
        }
        return bestIndex;
    }


    //this function randomizes the order of cities in an array. This is used for creating the inital population and for 1-point crossover
    public int[] randomizeArray(int[] array, int shuffleAmt) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < shuffleAmt; j++) {
                swap(array, i, rand.nextInt(array.length));
            }
        }
        return array;
    }

    //this method creates a feasible solution at the beginning of the ga so we can randomize it's order for the inital populaiton.
    public int[][] initializeOrder(int POPSize, int TSPLength) {
        int[][] order = new int[POPSize][TSPLength];
        for (int i = 0; i < POPSize; i++) {
            for (int j = 0; j < TSPLength; j++) {
                order[i][j] = j;
            }
        }
        return order;
    }

    //This function takes a chromosome and returns the fitness of that chromosome
    public double testFitness(double[][] TSP, int[] order) {
        double fitness = 0;
        for (int i = 0; i < order.length - 1; i++) {
            fitness += Math.sqrt(Math.pow(TSP[order[i + 1]][0] - TSP[order[i]][0], 2) + Math.pow(TSP[order[i + 1]][1] - TSP[order[i]][1], 2));
        }
        fitness += Math.sqrt(Math.pow(TSP[0][0] - TSP[order.length - 1][0], 2) + Math.pow(TSP[0][1] - TSP[order.length - 1][1], 2));
        return fitness;
    }


    //this function swaps two indices of an array
    public int[] swap(int[] order, int A, int B) {

        int temp = order[A];
        order[A] = order[B];
        order[B] = temp;
        return order;
    }


    //this function fills a 2d array with -1 to indicate it has empty slots
    public int[][] fillArray(int[][] nextOrder) {
        for (int i = 0; i < nextOrder.length; i++) {
            for (int j = 0; j < nextOrder[0].length; j++) {
                nextOrder[i][j] = -1;
            }
        }
        return nextOrder;
    }

    //this function fills a 1d array with -1 to indicate it has empty slots
    public int[] fillArray(int[] nextOrder) {
        for (int i = 0; i < nextOrder.length; i++) {
            nextOrder[i] = -1;
        }
        return nextOrder;
    }

}

