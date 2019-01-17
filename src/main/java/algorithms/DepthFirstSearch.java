package algorithms;

import models.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class DepthFirstSearch implements IAlgorithm {
    private Node goalNode;
    private int iterations = 0;
    public List<Node> solve(Node startingNode, int[][] goalState, int maxIterations) throws TimeoutException {
        List<Node> visitedNodes = new ArrayList<>();
        this.dfs(startingNode, visitedNodes, goalState, maxIterations);
        return goalNode.getRouteToCurrentNode();
    }

    private void dfs(Node node, List<Node> visitedNodes, int[][] goalState, int maxIterations) throws TimeoutException {
        if (iterations++ > maxIterations) {
            throw new TimeoutException("Algorithm exceeded max number of iterations");
        }
        // Mark node as visited
        node.getBookKeeping().setExpanded(true);
        visitedNodes.add(node);
        // Check if starting state equals goalState
        if (node.checkIfStatesAreEqual(goalState)) {
            this.goalNode = node;
        }

        System.out.println(node.getBookKeeping().getDepth());
        List<Node> children = node.getSuccessors(node.getBookKeeping().getAction());
        for (Node child : children) {
            if (!child.getBookKeeping().isExpanded() && !visitedNodes.contains(child)) {
                this.dfs(child, visitedNodes, goalState, maxIterations);
            }
        }
    }
}
