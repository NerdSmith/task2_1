package ru.vsu.cs.commands;

import ru.vsu.cs.Directory;
import ru.vsu.cs.File;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.vsu.cs.utils.FileSystemUtils.getFileByPath;
import static ru.vsu.cs.utils.FileSystemUtils.processCommand;

public class ConcatenateCommand extends Command {
    private final String consoleRegex = "cat\\s(\\S+\\.txt)";
    private final String fileRegex = "cat\\s(\\S+\\.txt)\\s(>{1,2})\\s(\\S+\\.txt)";

    public ConcatenateCommand(String name) {
        super(name);
    }

    @Override
    public Directory exec(ArrayList<String> splittedCommand, Directory mainDir, Directory currDir) {
        String command = String.join(" ", splittedCommand);

        if (command.matches(consoleRegex)) {
            Pattern pattern = Pattern.compile(consoleRegex);
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                File inputFile = (File) currDir.getChild(matcher.group(1));
                if (inputFile == null) {
                    return null;
                }
                System.out.println(inputFile.getText());
            }
        }
        else if (command.matches(fileRegex)) {
            Pattern pattern = Pattern.compile(fileRegex);
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                File inputFile = (File) currDir.getChild(matcher.group(1));
                if (inputFile == null) {
                    return null;
                }
                String text = inputFile.getText();
                command = String.format("echo \"%s\" %s %s",
                        text,
                        matcher.group(2), matcher.group(3));
                EchoCommand echoCommand = new EchoCommand("echo");
                currDir = echoCommand.exec(processCommand(command), mainDir, currDir);
            }
        }
        return currDir;
    }

}
