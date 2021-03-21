package ru.vsu.cs;

public class File extends Node{
    private String text;

    public File(Directory parent, String name, String text) {
        this.setParent(parent);
        this.setName(name);
        this.text = text;
    }

    public File(Directory parent, String name) {
        this.setParent(parent);
        this.setName(name);
        this.text = "";
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
