package ru.vsu.cs;

import java.util.HashMap;

public class Directory extends Node{
    private HashMap<String, Node> children = new HashMap<>();

    public Directory(Directory parent, String name) {
        this.setParent(parent);
        this.setName(name);
    }

    public Node getChild(String name) {
        return children.get(name);
    }

    public HashMap<String, Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        if (children.containsKey(child.getName())) {
            return;
        }
        children.put(child.getName(), child);
    }
}
