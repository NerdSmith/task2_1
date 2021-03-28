package ru.vsu.cs.nodes;

public abstract class Node {
    private String name;
    private Directory parent;

    public Node(Directory parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }
}
