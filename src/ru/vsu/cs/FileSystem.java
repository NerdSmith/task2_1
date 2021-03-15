package ru.vsu.cs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FileSystem {
    private static void printDirPath(Directory currDir) {
        Directory tmpCurrDir = currDir;
        StringBuilder path = new StringBuilder(tmpCurrDir.getName());
        while (tmpCurrDir.getParent() != null) {
            tmpCurrDir = tmpCurrDir.getParent();
            path.insert(0, "\\");
            path.insert(0, tmpCurrDir.getName());
        }
        path.append("> ");
        System.out.println(path.toString());
    }

    private static String readCommand(Directory currDir) {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static ArrayList<String> processCommand(String command) {
        return new ArrayList<>(Arrays.asList(command.split(" ")));
    }

    private static void printError(String phrase) {
        System.out.printf("An error occurred: \n\t%s\n", phrase);
    }

    private static void execCommand(ArrayList<String> splittedCommand, Directory currDir) {
        switch (splittedCommand.get(0)) {
            case "cd":
                boolean execStatus = changeDirectory(splittedCommand, currDir);
                if (!execStatus) {
                    printError("can't find direcrory");
                }
                break;
            case "ls":
                break;
            case "mkdir":
                break;
            case "rm":
                break;
            case "cat":
                break;
            case "tree":
                break;
        }
    }

    private static boolean changeDirectory(ArrayList<String> splittedCommand, Directory currDir) {
        Directory targetDir = currDir.getChild(splittedCommand.get(1));
        if (targetDir != null && targetDir instanceof Directory) {
            currDir = currDir.getChild(splittedCommand.get(0);
            return true;
        }
        else if (splittedCommand.get(1).equals("..")) {
            currDir = currDir.getParent();
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean listFiles(ArrayList<String> splittedCommand, Directory currDir) {
        StringBuilder files = new StringBuilder(currDir.getName());
        files.append(" \\\n");




        for (String nodeName : currDir.getChildren().keySet()) {

        }
    }

    public static void main(String[] args) {
        Directory mainDir = new Directory(null, "main dir");
        Directory currDir = mainDir;
        currDir.addChild(new Directory(currDir, "dir2"));
        currDir = (Directory) currDir.getChild("dir2");
        printDirPath(currDir);
        String command = readCommand(currDir);
        System.out.println(processCommand(command));

    }
}
