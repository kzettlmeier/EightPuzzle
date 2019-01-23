package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Node {
    private int[][] state;
    private Node parentNode;
    private BookKeeping bookKeeping;

    public Node(int[][] state, Node parentNode, BookKeeping bookKeeping) {
        this.state = state;
        this.parentNode = parentNode;
        this.bookKeeping = bookKeeping;
    }

    public int[][] getState() {
        return this.state;
    }

    public Node getParentNode() {
        return this.parentNode;
    }

    public BookKeeping getBookKeeping() {
        return this.bookKeeping;
    }

    public List<Node> getSuccessors(Action lastMove) {
        ArrayList<Node> successors = new ArrayList<Node>();

        // Find 0 position
        for (int i = 0; i < this.state.length; i++) {
            for (int j = 0; j < this.state[i].length; j++) {
                if (this.state[i][j] == 0) {
                    // Make moves
                    // Check if can move left
                    if (!lastMove.equals(Action.LEFT) && j != 0) {
                        BookKeeping bookKeeping = new BookKeeping(Action.LEFT, this.bookKeeping.getDepth() + 1, this.state[i][j - 1]);
                        Node newChild = new Node(this.swap(i, j, i, j - 1), this, bookKeeping);
                        successors.add(newChild);
                    }

                    // Check if can move up
                    if (!lastMove.equals(Action.UP) && i != 0) {
                        BookKeeping bookKeeping = new BookKeeping(Action.UP, this.bookKeeping.getDepth() + 1, this.state[i - 1][j]);
                        Node newChild = new Node(this.swap(i, j, i - 1, j), this, bookKeeping);
                        successors.add(newChild);
                    }

                    // Check if can move right
                    if (!lastMove.equals(Action.RIGHT) && j != 2) {
                        BookKeeping bookKeeping = new BookKeeping(Action.RIGHT, this.bookKeeping.getDepth() + 1, this.state[i][j + 1]);
                        Node newChild = new Node(this.swap(i, j, i, j + 1), this, bookKeeping);
                        successors.add(newChild);
                    }

                    // Check if can move down
                    if (!lastMove.equals(Action.DOWN) && i != 2) {
                        BookKeeping bookKeeping = new BookKeeping(Action.DOWN, this.bookKeeping.getDepth() + 1, this.state[i + 1][j]);
                        Node newChild = new Node(this.swap(i, j, i + 1, j), this, bookKeeping);
                        successors.add(newChild);
                    }
                }
            }
        }

        return successors;
    }

    public int[][] swap(int originalRow, int originalColumn, int swapRow, int swapColumn) {
        int[][] newState = new int[3][3];
        for (int i = 0; i < newState.length; i++) {
            for (int j = 0; j < newState[i].length; j++) {
                newState[i][j] = this.state[i][j];
            }
        }
        int originalValue = newState[originalRow][originalColumn];
        newState[originalRow][originalColumn] = newState[swapRow][swapColumn];
        newState[swapRow][swapColumn] = originalValue;
        return newState;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Node node = (Node)obj;

        return Arrays.deepEquals(this.state, node.state);
    }

    public boolean checkIfStatesAreEqual(int[][] state) {
        return Arrays.deepEquals(this.state, state);
    }

    public void printOutStateInAGrid() {
        for (int i = 0; i < this.state.length; i++) {
            String row = "[";
            for (int j = 0; j < this.state[i].length; j++) {
                row += this.state[i][j];
                if (j < this.state[i].length - 1) {
                    row += "|";
                }
            }
            row += "]";
            System.out.println(row);
        }
    }

    public List<Node> getRouteToCurrentNode() {
        List<Node> route = new ArrayList<>();
        Node current = this;
        route.add(current);
        while (current.getParentNode() != null) {
            current = current.getParentNode();
            route.add(current);
        }

        Collections.reverse(route);
        return route;
    }
}
