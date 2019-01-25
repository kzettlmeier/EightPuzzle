package algorithms;

import helpers.Constants;
import models.Node;
import models.Solution;

import java.util.*;
import java.util.concurrent.TimeoutException;

public class AStarOneSearch implements IAlgorithm {
    public Solution solve(Node startingNode, int[][] goalState, Date startingTime) throws TimeoutException {
        // Create min heap where the element to be evaluated is the total cost + number of tiles not in correct position
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
                boolean shouldAdd = true;
                if (visitedNodes.contains(child)) {
                    // Visited node contains state, need to check if this is currently a better implementation of that visited node otherwise ignore
                    Object[] nodes = visitedNodes.toArray();
                    for (Object nodeObj : nodes) {
                        Node searchNode = (Node)nodeObj;
                        if (searchNode.equals(child)) {
                            int prevNodeEval = evaluate(searchNode, goalState) + searchNode.getBookKeeping().getTotalCost();
                            int newNodeEval = evaluate(child, goalState) + child.getBookKeeping().getTotalCost();
                            if (newNodeEval < prevNodeEval) {
                                visitedNodes.remove(searchNode);
                            } else {
                                shouldAdd = false;
                            }
                        }
                    }
                }
                if (queue.contains(child) && shouldAdd) {
                    // Check if there is any node in the queue that matches new successor, then check if the new successor has a better value than old node
                    Object[] nodes = queue.toArray();
                    for (Object nodeObj : nodes) {
                        Node searchNode = (Node)nodeObj;
                        if (searchNode.equals(child)) {
                            int prevNodeEval = evaluate(searchNode, goalState) + searchNode.getBookKeeping().getTotalCost();
                            int newNodeEval = evaluate(child, goalState) + child.getBookKeeping().getTotalCost();
                            if (newNodeEval < prevNodeEval) {
                                queue.remove(searchNode);
                            } else {
                                shouldAdd = false;
                            }
                        }
                    }
                }

                // After duplicate checks make sure it should still be added
                if (shouldAdd) {
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
        // Total cost + number of tiles not in correct position
        return node.getBookKeeping().getTotalCost() + numberOfTilesNotInCorrectPosition(node.getState(), goalState);
    }

    // Calculate the number of tiles that are not in the correct position
    private int numberOfTilesNotInCorrectPosition(int [][] currentState, int[][] goalState) {
        int num = 0;
        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState[i].length; j++) {
                if (currentState[i][j] != goalState[i][j]) {
                    num++;
                }
            }
        }
        return num;
    }
}
