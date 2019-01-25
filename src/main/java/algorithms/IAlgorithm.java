package algorithms;

import models.Node;
import models.Solution;

import java.util.Date;
import java.util.concurrent.TimeoutException;

// Interface which each algorithm will implement so I can use the strategy pattern to execute the solver
public interface IAlgorithm {
    Solution solve(Node startingNode, int[][] goalState, Date startingTime) throws TimeoutException;
}
