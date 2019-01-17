package algorithms;

import models.Node;

import java.util.*;
import java.util.concurrent.TimeoutException;

public class BreadthFirstSearch implements IAlgorithm {
    public List<Node> solve(Node startingNode, int[][] goalState, int maxIterations) throws TimeoutException {
        Queue<Node> queue = new LinkedList<>();
        List<Node> visitedNodes = new ArrayList<>();

        queue.add(startingNode);

        int iterations = 0;
        while (!queue.isEmpty()) {
            if (iterations++ > maxIterations) {
                throw new TimeoutException("Algorithm exceeded max number of iterations");
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
}
