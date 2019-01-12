package models;

import java.util.ArrayList;

public class Node {
    private int[][] state;
    private Node parentNode;
    private ArrayList<Node> children;
    private BookKeeping bookKeeping;

    public Node(int[][] state, Node parentNode, ArrayList<Node> children, BookKeeping bookKeeping) {
        this.state = state;
        this.parentNode = parentNode;
        this.children = children;
        this.bookKeeping = bookKeeping;
    }

    public int[][] getState() {
        return this.state;
    }

    public Node getParentNode() {
        return this.parentNode;
    }

    public ArrayList<Node> getChildren() {
        return this.children;
    }

    public BookKeeping getBookKeeping() {
        return this.bookKeeping;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }
}
