package ru.vsu.cs;

public class File extends Node{
    private String text;

    public File(String text, String name) {
        this.text = text;
        this.setName(name);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
