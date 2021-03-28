package ru.vsu.cs.commands;

import ru.vsu.cs.nodes.Directory;
import ru.vsu.cs.nodes.File;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.vsu.cs.utils.FileSystemUtils.getFileByPath;

public class EchoCommand extends Command {
    private final String consoleRegex = "echo\\s\"((.|\\n|\\t)*)\"";
    private final String fileRegex = "echo\\s\"((.|\\n|\\t)*)\"\\s(>{1,2})\\s(\\S+\\.txt)";
    // echo\s((-.\s)*)"((.|\n|\t)*)"\s(>{1,2})\s(\S+\.txt) for flags support

    public EchoCommand(String name) {
        super(name);
    }

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
                File file = getFileByPath(matcher.group(4), mainDir, currDir);
                if (file == null) {
                    return null;
                }
                String text = matcher.group(1);
                if (text.equals("\\n")) {
                    text = "\n";
                }
                if (matcher.group(3).equals(">>")) {
                    file.setText(file.getText() + text);
                }
                else {
                    file.setText(text);
                }

            }
        }
        return currDir;
    }
}
