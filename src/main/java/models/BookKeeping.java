package models;

// Keep track of book keeping information about each node
public class BookKeeping {
    private Action action;
    private int depth;
    private int pathCost;
    private int totalCost;

    public BookKeeping(Action action, int depth, int pathCost) {
        this.action = action;
        this.depth = depth;
        this.pathCost = pathCost;
        this.totalCost = 0;
    }

    public Action getAction() {
        return this.action;
    }

    public int getDepth() {
        return this.depth;
    }

    public int getPathCost() {
        return this.pathCost;
    }

    public int getTotalCost() { return this.totalCost; }

    public void setTotalCost(int totalCost) { this.totalCost = totalCost; }
}
