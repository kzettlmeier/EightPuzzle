package algorithms;

import models.Node;

import java.util.List;

public interface IAlgorithm {
    List<Node> solve(Node startingNode, int[][] goalState);
}
