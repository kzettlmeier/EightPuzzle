package algorithms;

import helpers.Constants;
import models.Node;
import models.Solution;

import java.util.*;
import java.util.concurrent.TimeoutException;

public class AStarTwoSearch implements IAlgorithm {
    public Solution solve(Node startingNode, int[][] goalState, Date startingTime) throws TimeoutException {
        PriorityQueue<Node> queue = new PriorityQueue<>(Constants.MAX_POSSIBLE_STATES,
                Comparator.comparingInt(x -> evaluate(x, goalState)));
        List<Node> visitedNodes = new ArrayList<>();
        int nodesPopped = 0;
        int maxSizeOfQueue = 0;

        queue.add(startingNode);

        while (!queue.isEmpty()) {
            if ((new Date()).getTime() - startingTime.getTime() >= Constants.MAX_TIME_TO_SOLVE) {
                throw new TimeoutException("Algorithm exceeded time");
            }
            // Dequeue
            Node node = queue.poll();
            nodesPopped++;
            if (node.getParentNode() != null) {
                node.getBookKeeping().setTotalCost(node.getParentNode().getBookKeeping().getTotalCost() + node.getBookKeeping().getPathCost());
            } else {
                node.getBookKeeping().setTotalCost(node.getBookKeeping().getPathCost());
            }

            // Mark node as visited
            node.getBookKeeping().setExpanded(true);
            visitedNodes.add(node);
            // Check if node equals goalState
            if (node.checkIfStatesAreEqual(goalState)) {
                return new Solution(node.getRouteToCurrentNode(), node.getBookKeeping().getTotalCost(), nodesPopped, maxSizeOfQueue);
            }

            List<Node> children = node.getSuccessors(node.getBookKeeping().getAction());
            for (Node child : children) {
                if (!child.getBookKeeping().isExpanded() && !queue.contains(child) && !visitedNodes.contains(child)) {
                    queue.add(child);
                }
            }
            if (queue.size() > maxSizeOfQueue) {
                maxSizeOfQueue = queue.size();
            }
        }

        return null;
    }

    private int evaluate(Node node, int[][] goalState) {
        return node.getBookKeeping().getTotalCost() + sumOfManhattanDistanceForState(node.getState(), goalState);
    }

    private int sumOfManhattanDistanceForState(int [][] currentState, int[][] goalState) {
        int num = 0;
        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState[i].length; j++) {
                if (currentState[i][j] != goalState[i][j]) {
                    for (int m = 0; m < goalState.length; m++) {
                        for (int n = 0; n < goalState[m].length; n++) {
                            if (currentState[i][j] == goalState[m][n]) {
                                num += Math.abs(i - m) + Math.abs(j - n);
                            }
                        }
                    }
                }
            }
        }
        return num;
    }
}
