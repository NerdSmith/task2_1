package ru.vsu.cs;

import java.util.HashMap;

public class Directory extends Node{
    private final Directory parent;
    private HashMap<String, Node> children = new HashMap<>();

    public Directory(Directory parent, String name) {
        this.parent = parent;
        this.setName(name);
    }

    public Directory getParent() {
        return parent;
    }

    public Node getChild(String name) {
        return children.get(name);
    }

    public HashMap<String, Node> getChildren() {
        return children;
    }

    public boolean addChild(Node child) {
        if (children.containsKey(child.getName())) {
            return false;
        }
        children.put(child.getName(), child);
        return true;
    }
}
