package main;

import algorithms.*;

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
        pullArguments(args);
        if (!validateArguments()) {
            System.out.println("There was an issue parsing out the argument provided. Please try again.");
        }
        System.out.println("Welcome to the Eight Puzzle!");
        System.out.println();
        System.out.println("Running Eight Puzzle on " + difficulty + " with algorithm " + algorithm + "!");

        setStartingStateBasedOnDifficulty();
        System.out.println("Starting state:");
        printOutStateInGrid(state);

        setAlgorithm();
        algorithmSolver.solve(state, goalState);
    }

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

    private static void printOutStateInGrid(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            String row = "[";
            for (int j = 0; j < state[i].length; j++) {
                row += state[i][j];
                if (j < state[i].length - 1) {
                    row += "|";
                }
            }
            row += "]";
            System.out.println(row);
        }
    }
}
