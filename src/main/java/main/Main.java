package main;

import algorithms.*;
import models.Action;
import models.BookKeeping;
import models.Node;

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
        pullArguments(args);
        if (!validateArguments()) {
            System.out.println("There was an issue parsing out the argument provided. Please try again.");
        }
        System.out.println("Welcome to the Eight Puzzle!");
        System.out.println();
        System.out.println("Running Eight Puzzle on " + difficulty + " with algorithm " + algorithm + "!");

        setStartingStateBasedOnDifficulty();

        Node startingNode = new Node(state, null, null, new BookKeeping(Action.NONE, 0, 0, false));

        System.out.println("Starting state:");
        startingNode.printOutStateInAGrid();
        System.out.println("---------------------");

        setAlgorithm();
        try {
            Date startingTime = new Date();
            List<Node> solution = algorithmSolver.solve(startingNode, goalState, startingTime);
            System.out.println("Found a solution:");
            printOutList(solution);
        } catch (TimeoutException ex) {
            System.out.println("Algorithm Exception: " + ex.getMessage());
        }
    }

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
}
