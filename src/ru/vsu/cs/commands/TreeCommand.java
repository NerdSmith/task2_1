package ru.vsu.cs.commands;

import ru.vsu.cs.Directory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.vsu.cs.utils.FileSystemUtils.*;

public class TreeCommand extends Command {
    private final String mainDirConsoleRegex = "tree";
    private final String anyDirConsoleRegex = "tree\\s([0-9a-zA-Z_./]+)";
    private final String fileRegex = "tree\\s([0-9a-zA-Z_./]+)\\s(>{1,2})\\s(\\S+\\.txt)";

    public TreeCommand(String name) {
        super(name);
    }

    @Override
    public Directory exec(ArrayList<String> splittedCommand, Directory mainDir, Directory currDir) {
        String command = String.join(" ", splittedCommand);
        String text;
        if (command.matches(mainDirConsoleRegex)) {
            text = getText("/", mainDir, currDir);
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
            Pattern pattern = Pattern.compile(fileRegex);
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                text = getText(matcher.group(1), mainDir, currDir);
                command = String.format("echo \"%s\" %s %s",
                        text,
                        matcher.group(2), matcher.group(3));
                EchoCommand echoCommand = new EchoCommand("echo");
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
        return getFiles(targetDir, true, "\t");
    }
}

