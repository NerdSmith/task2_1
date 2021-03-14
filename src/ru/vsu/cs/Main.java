package ru.vsu.cs;

public class Main {

    public static void main(String[] args) {
        Directory mainDir = new Directory(null, "main dir");
        Directory currDir = mainDir;
        mainDir.addChild(new File("string 1", "file1.txt"));
        mainDir.addChild(new Directory(currDir, "new dir"));
        Node f = mainDir.getChild("new dir");
        if (f instanceof Directory) {
            currDir = (Directory) f;
        }
    }
}
