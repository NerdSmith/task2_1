package ru.vsu.cs.utils;

import ru.vsu.cs.Directory;
import ru.vsu.cs.File;

import java.util.ArrayList;
import java.util.Arrays;

public class FileSystemUtils {
    public static Directory getDirByPath(String path, Directory mainDir, Directory currDir) {
        if (path.equals("/")) {
            return mainDir;
        }
        ArrayList<String> pathToTargetDir = new ArrayList<>(Arrays.asList(path.split("/")));
        Directory targetDir = currDir;
        for (String dirName : pathToTargetDir) {
            if (dirName.equals("..")) {
                targetDir = targetDir.getParent();
            } else if (dirName.equals(".")) {
                return targetDir;
            } else if (targetDir.getChildren().containsKey(dirName)) {
                targetDir = (Directory) targetDir.getChild(dirName);
            } else {
                return null;
            }
        }
        return targetDir;
    }

    public static File getFileByPath(String path, Directory mainDir, Directory currDir) {
        ArrayList<String> pathToFile = new ArrayList<>(Arrays.asList(path.split("/")));
        String fileName = pathToFile.remove(pathToFile.size() - 1);
        if (fileName.matches("^.*\\.txt$")) {
            Directory targetDir = getDirByPath(String.join("/", pathToFile), mainDir, currDir);
            if (targetDir == null) {
                if (pathToFile.size() == 0) {
                    targetDir = currDir;
                } else {
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

    public static String getFiles(Directory dir, boolean recursively, String indent) {
        StringBuilder files = new StringBuilder(dir.getName());
        if (dir.getChildren().size() != 0) {
            files.append(" \\\n");
        } else {
            files.append(" \\");
        }
        ArrayList<String> keys = new ArrayList<>(dir.getChildren().keySet());
        for (int i = 0; i < keys.size(); i++) {
            String nodeName = keys.get(i);
            files.append(indent);
            if (dir.getChild(nodeName) instanceof Directory && recursively) {
                files.append(getFiles((Directory) dir.getChild(nodeName), true, indent + "\t"));
            } else {
                files.append(nodeName);
            }
            if (keys.size() - 1 > i) {
                files.append("\n");
            }
        }
        return files.toString();
    }

    public static ArrayList<String> processCommand(String command) {
        return new ArrayList<>(Arrays.asList(command.split(" ")));
    }


}
