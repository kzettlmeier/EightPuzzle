package algorithms;

import helpers.Constants;
import models.Node;

import java.util.*;
import java.util.concurrent.TimeoutException;

public class AStarTwoSearch implements IAlgorithm {
    public List<Node> solve(Node startingNode, int[][] goalState, Date startingTime) throws TimeoutException {
        PriorityQueue<Node> queue = new PriorityQueue<>(Constants.MAX_POSSIBLE_STATES,
                Comparator.comparingInt(x -> evaluate(x, goalState)));
        List<Node> visitedNodes = new ArrayList<>();

        queue.add(startingNode);

        while (!queue.isEmpty()) {
            if ((new Date()).getTime() - startingTime.getTime() >= Constants.MAX_TIME_TO_SOLVE) {
                throw new TimeoutException("Algorithm exceeded time");
            }
            // Dequeue
            Node node = queue.poll();

            // Mark node as visited
            node.getBookKeeping().setExpanded(true);
            visitedNodes.add(node);
            // Check if starting state equals goalState
            if (node.checkIfStatesAreEqual(goalState)) {
                return node.getRouteToCurrentNode();
            }

            List<Node> children = node.getSuccessors(node.getBookKeeping().getAction());
            for (Node child : children) {
                if (!child.getBookKeeping().isExpanded() && !visitedNodes.contains(child)) {
                    queue.add(child);
                }
            }
        }

        return null;
    }

    private int evaluate(Node node, int[][] goalState) {
        return node.getBookKeeping().getPathCost() + sumOfManhattanDistanceForState(node.getState(), goalState);
    }

    private int sumOfManhattanDistanceForState(int [][] currentState, int[][] goalState) {
        int num = 0;
        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState[i].length; j++) {
                if (currentState[i][j] != goalState[i][j]) {
                    for (int m = 0; m < goalState.length; m++) {
                        for (int n = 0; n < goalState[m].length; n++) {
                            num += Math.abs(i - m) + Math.abs(j - n);
                        }
                    }
                }
            }
        }
        return num;
    }
}
