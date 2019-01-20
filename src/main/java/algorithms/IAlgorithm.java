package algorithms;

import models.Node;
import models.Solution;

import java.util.Date;
import java.util.concurrent.TimeoutException;

public interface IAlgorithm {
    Solution solve(Node startingNode, int[][] goalState, Date startingTime) throws TimeoutException;
}
