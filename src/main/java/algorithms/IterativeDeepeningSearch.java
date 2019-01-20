package algorithms;

import helpers.Constants;
import models.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class IterativeDeepeningSearch implements IAlgorithm {
    public List<Node> solve(Node startingNode, int[][] goalState, Date startingTime) throws TimeoutException {
        List<Node> solution = null;
        int depth = 0;
        while (solution == null) {
            solution = this.runDepthFirstSearchWithDepth(startingNode, goalState, startingTime, depth);
            depth++;
        }

        return solution;
    }

    private List<Node> runDepthFirstSearchWithDepth(Node startingNode, int[][] goalState, Date startingTime, int maxDepth) throws TimeoutException {
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

            // Mark node as visited
            node.getBookKeeping().setExpanded(true);
            visitedNodes.add(node);
            // Check if node equals goalState
            if (node.checkIfStatesAreEqual(goalState)) {
                return node.getRouteToCurrentNode();
            }

            if (currentDepth < maxDepth) {
                List<Node> children = node.getSuccessors(node.getBookKeeping().getAction());
                for (Node child : children) {
                    if (!child.getBookKeeping().isExpanded() && !visitedNodes.contains(child)) {
                        queue.add(child);
                    }
                }
            }

            currentDepth++;
        }

        return null;
    }
}
