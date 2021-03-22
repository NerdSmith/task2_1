package ru.vsu.cs;

import java.util.*;

public class FileSystem {

    private final Directory mainDir;
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
                concatenate(splittedCommand);
                break;
            case "echo":
                echo(splittedCommand);
                break;
            case "tree":
                printThree(splittedCommand);
                break;
        }
    }

    private Directory getDirByPath(String path) {
        if (path.equals("/")) {
            return this.mainDir;
        }
        ArrayList<String> pathToTargetDir = new ArrayList<>(Arrays.asList(path.split("/")));
        Directory targetDir = this.currDir;
        for (String dirName : pathToTargetDir) {
            if (dirName.equals("..")) {
                targetDir = targetDir.getParent();
            }
            else if (dirName.equals(".")) {
                return targetDir;
            }
            else if (targetDir.getChildren().containsKey(dirName)) {
                targetDir = (Directory) targetDir.getChild(dirName);
            }
            else {
                return null;
            }
        }
        return targetDir;
    }

    private File getFileByPath(String path) {
        ArrayList<String> pathToFile = new ArrayList<>(Arrays.asList(path.split("/")));
        String fileName = pathToFile.remove(pathToFile.size() - 1);
        if (fileName.matches("^.*\\.txt$")) {
            Directory targetDir = getDirByPath(String.join("/", pathToFile));
            if (targetDir == null) {
                if (pathToFile.size() == 0) {
                    targetDir = this.currDir;
                }
                else {
                    return null;
                }
            }
            if (!targetDir.getChildren().containsKey(fileName)) {
                targetDir.addChild(new File(targetDir, fileName));
            }
            return (File) targetDir.getChild(fileName);
        }
        return null;
    }

    private void changeDirectory(ArrayList<String> splittedCommand) {
        Directory targetDir = getDirByPath(splittedCommand.get(1));
        if (targetDir != null) {
            this.currDir = targetDir;
        }
    }

    private void listFiles(ArrayList<String> splittedCommand) {
        String text;
        switch (splittedCommand.size()) {
            case 1:
                text = getFiles(this.currDir, false, "\t");
                System.out.println(text);
                break;
            case 2:
            case 4:
                Directory targetDir = getDirByPath(splittedCommand.get(1));
                if (targetDir == null) {
                    break;
                }
                text = getFiles(targetDir, false, "\t");
                if (splittedCommand.size() == 2) {
                    System.out.println(text);
                }
                else {
                    String command = String.format("echo %s %s",
                            text,
                            String.join(" ", splittedCommand.subList(2, splittedCommand.size())));
                    echo(processCommand(command));
                }
                break;
        }
    }

    private void printThree(ArrayList<String> splittedCommand) {
        Directory targetDir;
        if (splittedCommand.size() == 1) {
            targetDir = this.mainDir;
        }
        else {
            targetDir = getDirByPath(splittedCommand.get(1));
        }
        if (targetDir != null) {
            String text = getFiles(targetDir, true, "\t");
            System.out.println(text);
        }
    }

    private String getFiles(Directory dir, boolean recursively, String indent) {
        StringBuilder files = new StringBuilder(dir.getName());
        if (dir.getChildren().size() != 0) {
            files.append(" \\\n");
        }
        else {
            files.append(" \\");
        }
        ArrayList<String> keys = new ArrayList<>(dir.getChildren().keySet());
        for (int i = 0; i < keys.size(); i++) {
            String nodeName = keys.get(i);
            files.append(indent);
            if (dir.getChild(nodeName) instanceof Directory && recursively) {
                files.append(getFiles((Directory) dir.getChild(nodeName), true, indent + "\t"));
            }
            else {
                files.append(nodeName);
            }
            if (keys.size() - 1 > i) {
                files.append("\n");
            }
        }
        return files.toString();
    }

    private void makeDirectory(ArrayList<String> splittedCommand) {
        String dirName = splittedCommand.get(1);
        if (dirName.matches("[0-9a-zA-Z_]+")) {
            this.currDir.addChild(new Directory(this.currDir, dirName));
        }
    }

    private void remove(ArrayList<String> splittedCommand) {
        String name = splittedCommand.get(1);
        this.currDir.getChildren().remove(name);
    }

    private void concatenate(ArrayList<String> splittedCommand) {
        File inputFile = (File) this.currDir.getChild(splittedCommand.get(1));
        if (inputFile == null) {
            return;
        }
        if (splittedCommand.size() == 2) {
            System.out.println(inputFile.getText());
        }
        else if (splittedCommand.size() == 4){
            File outputFile = getFileByPath(splittedCommand.get(splittedCommand.size() - 1));
            if (outputFile == null) {
                return;
            }
            if (splittedCommand.get(splittedCommand.size() - 2).equals(">")) {
                outputFile.setText(inputFile.getText());
            }
            else if (splittedCommand.get(splittedCommand.size() - 2).equals(">>")) {
                outputFile.setText(outputFile.getText() + inputFile.getText());
            }
        }
    }

    private void echo(ArrayList<String> splittedCommand) {
        String text = String.join(" ", splittedCommand.subList(1, splittedCommand.size() - 2));
        if (splittedCommand.size() >= 4) {
            File file = getFileByPath(splittedCommand.get(splittedCommand.size() - 1));
            if (file == null) {
                return;
            }
            if (splittedCommand.get(splittedCommand.size() - 2).equals(">")) {
                file.setText(text);
            }
            else if (splittedCommand.get(splittedCommand.size() - 2).equals(">>")) {
                file.setText(file.getText() + text);
            }
        }
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
