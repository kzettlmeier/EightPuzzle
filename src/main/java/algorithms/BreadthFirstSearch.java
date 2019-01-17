package algorithms;

import models.Node;

import java.util.*;

public class BreadthFirstSearch implements IAlgorithm {
    public List<Node> solve(Node startingNode, int[][] goalState) {
        List<Node> solution = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        List<Node> visitedNodes = new ArrayList<>();

        queue.add(startingNode);

        while (!queue.isEmpty()) {
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
