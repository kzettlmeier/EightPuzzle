package algorithms;

import helpers.Constants;
import models.Node;
import models.Solution;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class IterativeDeepeningSearch implements IAlgorithm {
    private int nodesPopped = 0;
    private int maxSizeOfQueue = 0;
    public Solution solve(Node startingNode, int[][] goalState, Date startingTime) throws TimeoutException {
        Solution solution = null;
        int depth = 0;
        while (solution == null) {
            solution = this.runDepthFirstSearchWithDepth(startingNode, goalState, startingTime, depth);
            depth++;
        }

        return solution;
    }

    private Solution runDepthFirstSearchWithDepth(Node startingNode, int[][] goalState, Date startingTime, int maxDepth) throws TimeoutException {
        LinkedList<Node> queue = new LinkedList<>();
        List<Node> visitedNodes = new ArrayList<>();
        int currentDepth = 0;

        queue.add(startingNode);

        while (!queue.isEmpty()) {
            if ((new Date()).getTime() - startingTime.getTime() >= Constants.MAX_TIME_TO_SOLVE) {
                throw new TimeoutException("Algorithm exceeded time");
            }
            // Dequeue
            Node node = queue.pollLast();
            nodesPopped++;

            // Mark node as visited
            visitedNodes.add(node);
            // Check if node equals goalState
            if (node.checkIfStatesAreEqual(goalState)) {
                return new Solution(node.getRouteToCurrentNode(), node.getBookKeeping().getTotalCost(), nodesPopped, maxSizeOfQueue);
            }

            if (currentDepth < maxDepth) {
                List<Node> children = node.getSuccessors(node.getBookKeeping().getAction());
                for (Node child : children) {
                    child.getBookKeeping().setTotalCost(node.getBookKeeping().getTotalCost() + child.getBookKeeping().getPathCost());
                    if (!queue.contains(child) && !visitedNodes.contains(child)) {
                        queue.add(child);
                    }
                }
                if (queue.size() > maxSizeOfQueue) {
                    maxSizeOfQueue = queue.size();
                }
            }

            currentDepth++;
        }

        return null;
    }
}
