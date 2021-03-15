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
    private static void execCommand(ArrayList<String> splittedCommand, Directory currDir) {

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
