package ru.vsu.cs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FileSystem {

    private Directory mainDir;
    private Directory currDir;

    public FileSystem(String name) {
        this.mainDir = new Directory(null, name);
        this.currDir = this.mainDir;

        currDir.addChild(new Directory(currDir, "dir2"));
        currDir = (Directory) currDir.getChild("dir2");
    }

    private void printDirPath() {
        Directory tmpCurrDir = this.currDir;
        StringBuilder path = new StringBuilder(tmpCurrDir.getName());
        while (tmpCurrDir.getParent() != null) {
            tmpCurrDir = tmpCurrDir.getParent();
            path.insert(0, "\\");
            path.insert(0, tmpCurrDir.getName());
        }
        path.append("> ");
        System.out.print(path.toString());
    }

    private String readCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private ArrayList<String> processCommand(String command) {
        return new ArrayList<>(Arrays.asList(command.split(" ")));
    }

    private void execCommand(ArrayList<String> splittedCommand) {
        switch (splittedCommand.get(0)) {
            case "cd":
                changeDirectory(splittedCommand);
                break;
            case "ls":
                listFiles(splittedCommand);
                break;
            case "mkdir":
                makeDirectory(splittedCommand);
                break;
            case "rm":
                remove(splittedCommand);
                break;
            case "cat":
                break;
            case "echo":
                break;
            case "tree":
                break;
        }
    }

    private void changeDirectory(ArrayList<String> splittedCommand) {
        Node targetDir = this.currDir.getChild(splittedCommand.get(1));
        if (targetDir instanceof Directory) {
            this.currDir = (Directory) targetDir;
        }
        else if (splittedCommand.get(1).equals("..")) {
            this.currDir = this.currDir.getParent();
        }
    }

    private void listFiles(ArrayList<String> splittedCommand) {
        StringBuilder files = new StringBuilder(this.currDir.getName());
        files.append(" \\\n");

        for (String nodeName : this.currDir.getChildren().keySet()) {
            files.append(nodeName);
            files.append("\n");
        }
        System.out.println(files.toString());
    }

    private void makeDirectory(ArrayList<String> splittedCommand) {
        String dirName = splittedCommand.get(1);
        if (dirName.matches("[0-9a-zA-Z]+")) {
            this.currDir.addChild(new Directory(this.currDir, dirName));
        }
    }

    private void remove(ArrayList<String> splittedCommand) {
        String name = splittedCommand.get(1);
        this.currDir.getChildren().remove(name);
    }

    public void start() {
        printDirPath();
        ArrayList<String> splittedCommand =  processCommand(readCommand());
        while (!splittedCommand.get(0).equals("exit")) {
            execCommand(splittedCommand);
            printDirPath();
            splittedCommand =  processCommand(readCommand());
        }
    }

    public static void main(String [] args) {
        FileSystem fs = new FileSystem("mainDir");
        fs.start();
    }
}
