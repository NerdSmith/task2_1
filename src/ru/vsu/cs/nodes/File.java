package ru.vsu.cs.nodes;

public class File extends Node {
    private String text;

    public File(Directory parent, String name) {
        super(parent, name);
        this.text = "";
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
