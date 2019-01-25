package algorithms;

import helpers.Constants;
import models.Node;
import models.Solution;

import java.util.*;
import java.util.concurrent.TimeoutException;

public class UniformCostSearch implements IAlgorithm {
    public Solution solve(Node startingNode, int[][] goalState, Date startingTime) throws TimeoutException {
        // Create min heap where the element to be evaluated is the total cost plus the current node cost
        PriorityQueue<Node> queue = new PriorityQueue<>(Constants.MAX_POSSIBLE_STATES, Comparator.comparingInt(x -> x.getBookKeeping().getTotalCost() + x.getBookKeeping().getPathCost()));
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

            // Mark node as visited
            visitedNodes.add(node);
            // Check if node equals goalState
            if (node.checkIfStatesAreEqual(goalState)) {
                return new Solution(node.getRouteToCurrentNode(), node.getBookKeeping().getTotalCost(), nodesPopped, maxSizeOfQueue);
            }

            List<Node> children = node.getSuccessors(node.getBookKeeping().getAction());
            for (Node child : children) {
                // Set the total cost of the successor
                child.getBookKeeping().setTotalCost(node.getBookKeeping().getTotalCost() + child.getBookKeeping().getPathCost());
                if (!queue.contains(child) && !visitedNodes.contains(child)) {
                    queue.add(child);
                }
            }
            if (queue.size() > maxSizeOfQueue) {
                maxSizeOfQueue = queue.size();
            }
        }

        return null;
    }
}
