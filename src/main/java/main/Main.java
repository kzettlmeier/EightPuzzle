package main;

import algorithms.*;
import helpers.Constants;
import models.Action;
import models.BookKeeping;
import models.Node;
import models.Solution;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Main {
    private static final int[][] startingStateEasy = {
        {1, 3, 4},
        {8, 6, 2},
        {7, 0, 5}
    };
    private static final int[][] startingStateMedium = {
        {2, 8, 1},
        {0, 4, 3},
        {7, 6, 5}
    };
    private static final int[][] startingStateHard = {
        {5, 6, 7},
        {4, 0, 8},
        {3, 2, 1}
    };
    private static final int[][] goalState = {
        {1, 2, 3},
        {8, 0, 4},
        {7, 6, 5}
    };

    private static String difficulty;
    private static String algorithm;
    private static int[][] state;
    private static IAlgorithm algorithmSolver;

    public static void main(String[] args) {
        // Grab arguments to determine algorithm and which difficulty for code
        pullArguments(args);
        if (!validateArguments()) {
            System.out.println("There was an issue parsing out the argument provided. Please try again.");
        }
        System.out.println("Welcome to the Eight Puzzle!");
        System.out.println();
        System.out.println("Running Eight Puzzle on " + difficulty + " with algorithm " + algorithm + "!");

        // Set which starting state to use
        setStartingStateBasedOnDifficulty();

        Node startingNode = new Node(state, null, new BookKeeping(Action.NONE, 0, 0));

        System.out.println("Starting state:");
        startingNode.printOutStateInAGrid();
        System.out.println("---------------------");

        // Set the algorithm to be used
        setAlgorithm();
        try {
            // Set the starting time to be used to keep track of max run time
            Date startingTime = new Date();

            // Solve the 8-puzzle
            Solution solution = algorithmSolver.solve(startingNode, goalState, startingTime);

            // Print out solution along with stats
            System.out.println("Found a solution:");
            printOutList(solution.getSolutionList());
            long timeToSolveInMs = (new Date()).getTime() - startingTime.getTime();
            long timeToSolveMin = (timeToSolveInMs / 1000) / 60;
            int timeToSolveSec = (int)((timeToSolveInMs / 1000) % 60);
            System.out.println("Time to Solve: " + timeToSolveMin + " minutes and " + timeToSolveSec + " seconds.");
            System.out.println("Length of solution path: " + solution.getSolutionList().size());
            System.out.println("Cost of solution path: " + solution.getTotalCost());
            System.out.println("Time: " + solution.getNumberOfNodesPopped());
            System.out.println("Space: " + solution.getTotalSpaceAtMax());
        } catch (TimeoutException ex) {
            // Algorithm took longer than max alotted time
            System.out.println("Algorithm Exception: " + ex.getMessage());
            System.out.println("Algorithm took longer than " + (Constants.MAX_TIME_TO_SOLVE / 1000) / 60 + " minutes.");
        }
    }

    // Print out current path in a pretty way
    private static void printOutList(List<Node> list) {
        if (list != null) {
            int totalCost = 0;
            for (Node node : list) {
                BookKeeping bk = node.getBookKeeping();
                totalCost += bk.getPathCost();
                System.out.println(bk.getAction().toString() + ", cost = " + bk.getPathCost() + ", total cost = " + totalCost);
                node.printOutStateInAGrid();
                System.out.println("---------------------");
            }
        }
    }

    // Set the implementation of the solver
    private static void setAlgorithm() {
        if (algorithm.equals("breadth-first")) {
            algorithmSolver = new BreadthFirstSearch();
        }
        else if (algorithm.equals("depth-first")) {
            algorithmSolver = new DepthFirstSearch();
        }
        else if (algorithm.equals("iterative-deepening")) {
            algorithmSolver = new IterativeDeepeningSearch();
        }
        else if (algorithm.equals("uniform-cost")) {
            algorithmSolver = new UniformCostSearch();
        }
        else if (algorithm.equals("best-first")) {
            algorithmSolver = new BestFirstSearch();
        }
        else if (algorithm.equals("a*1")) {
            algorithmSolver = new AStarOneSearch();
        }
        else if (algorithm.equals("a*2")) {
            algorithmSolver = new AStarTwoSearch();
        }
        else if (algorithm.equals("a*3")) {
            algorithmSolver = new AStarThreeSearch();
        }
    }

    // Set the starting state based on difficulty
    private static void setStartingStateBasedOnDifficulty() {
        if ("easy".equals(difficulty)) {
            state = startingStateEasy;
        }
        else if ("medium".equals(difficulty)) {
            state = startingStateMedium;
        }
        else if ("hard".equals(difficulty)) {
            state = startingStateHard;
        }
    }

    // Grab the Java command line arguments
    private static void pullArguments(String[] args) {
        if (args.length != 2) {
            System.out.println("Must provide arguments for difficulty and algorithm!");
            System.exit(-1);
        }

        // First argument will be difficulty
        difficulty = args[0].toLowerCase();

        // Second argument will be the algorithm to run
        algorithm = args[1].toLowerCase();
    }

    // Make sure arguments are valid
    private static boolean validateArguments() {
        if (!difficulty.equals("easy") && !difficulty.equals("medium") && !difficulty.equals("hard")) {
            return false;
        }

        if (!algorithm.equals("breadth-first") && !algorithm.equals("depth-first") && !algorithm.equals("iterative-deepening") &&
            !algorithm.equals("uniform-cost") && !algorithm.equals("best-first") && !algorithm.equals("a*1") &&
            !algorithm.equals("a*2") && !algorithm.equals("a*3")) {
            return false;
        }

        return true;
    }
}
