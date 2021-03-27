package ru.vsu.cs.commands;

import ru.vsu.cs.Directory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.vsu.cs.utils.FileSystemUtils.*;

public class ListFilesCommand implements Command {
    private final String currDirConsoleRegex = "ls";
    private final String anyDirConsoleRegex = "ls\\s([0-9a-zA-Z_/]+)";
    private final String fileRegex = "ls\\s([0-9a-zA-Z_/]+)\\s(>{1,2})\\s(.+\\.txt)";

    @Override
    public Directory exec(ArrayList<String> splittedCommand, Directory mainDir, Directory currDir) {
        String command = String.join(" ", splittedCommand);
        String text;
        if (command.matches(currDirConsoleRegex)) {
            text = getFiles(currDir, false, "\t");
            System.out.println(text);
        }
        else if (command.matches(anyDirConsoleRegex)) {
            Pattern pattern = Pattern.compile(anyDirConsoleRegex);
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                System.out.println(getText(matcher.group(1), mainDir, currDir));
            }
        }
        else if (command.matches(fileRegex)) {
            Pattern pattern = Pattern.compile(anyDirConsoleRegex);
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                text = getText(matcher.group(1), mainDir, currDir);
                command = String.format("echo %s %s",
                        text,
                        String.join(" ", matcher.group(2) + matcher.group(3)));
                EchoCommand echoCommand = new EchoCommand();
                currDir = echoCommand.exec(processCommand(command), mainDir, currDir);
            }
        }
        return currDir;
    }

    private String getText(String path, Directory mainDir, Directory currDir) {
        Directory targetDir = getDirByPath(path, mainDir, currDir);
        if (targetDir == null) {
            return null;
        }
        return getFiles(targetDir, false, "\t");
    }
}
