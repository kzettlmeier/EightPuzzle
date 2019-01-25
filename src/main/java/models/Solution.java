package models;

import java.util.List;

// This class will keep track of the solution and use its information to print out stats
public class Solution {
    private List<Node> solutionList;
    private int totalCost;
    private int numberOfNodesPopped;
    private int totalSpaceAtMax;

    public Solution(List<Node> solutionList, int totalCost, int numberOfNodesPopped, int totalSpaceAtMax) {
        this.solutionList = solutionList;
        this.totalCost = totalCost;
        this.numberOfNodesPopped = numberOfNodesPopped;
        this.totalSpaceAtMax = totalSpaceAtMax;
    }

    public List<Node> getSolutionList() {
        return this.solutionList;
    }

    public int getTotalCost() {
        return this.totalCost;
    }

    public int getNumberOfNodesPopped() {
        return this.numberOfNodesPopped;
    }

    public int getTotalSpaceAtMax() {
        return this.totalSpaceAtMax;
    }
}
