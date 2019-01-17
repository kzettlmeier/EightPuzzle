package models;

public class BookKeeping {
    private Action action;
    private int depth;
    private int pathCost;
    private boolean expanded;

    public BookKeeping(Action action, int depth, int pathCost, boolean expanded) {
        this.action = action;
        this.depth = depth;
        this.pathCost = pathCost;
        this.expanded = expanded;
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

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
