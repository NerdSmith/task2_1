package ru.vsu.cs;

public class File extends Node{
    private String text;

    public File(String name, String text) {
        this.setName(name);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
