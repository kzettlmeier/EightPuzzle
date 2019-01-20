package algorithms;

import models.Node;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

public interface IAlgorithm {
    List<Node> solve(Node startingNode, int[][] goalState, Date startingTime) throws TimeoutException;
}
