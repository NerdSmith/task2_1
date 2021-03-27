package ru.vsu.cs.commands;

import ru.vsu.cs.Directory;
import ru.vsu.cs.File;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.vsu.cs.utils.FileSystemUtils.getFileByPath;

public class EchoCommand implements Command {
    private final String consoleRegex = "echo\\s\"(.*)\"";
    private final String fileRegex = "echo\\s\"(.*)\"\\s(>{1,2})\\s(.+\\.txt)";

    @Override
    public Directory exec(ArrayList<String> splittedCommand, Directory mainDir, Directory currDir) {
        String command = String.join(" ", splittedCommand);

        if (command.matches(consoleRegex)) {
            Pattern pattern = Pattern.compile(consoleRegex);
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                System.out.println(matcher.group(1));
            }
        }
        else if (command.matches(fileRegex)) {
            Pattern pattern = Pattern.compile(fileRegex);
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                File file = getFileByPath(matcher.group(3), mainDir, currDir);
                if (file == null) {
                    return null;
                }
                if (matcher.group(2).equals(">>")) {
                    file.setText(file.getText() + matcher.group(1));
                }
                else {
                    file.setText(matcher.group(1));
                }
            }
        }
        return currDir;
    }
}
