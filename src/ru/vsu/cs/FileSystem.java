package ru.vsu.cs;

import ru.vsu.cs.commands.*;
import ru.vsu.cs.nodes.Directory;

import java.util.*;

import static ru.vsu.cs.utils.FileSystemUtils.*;

public class FileSystem {
    private final Directory mainDir;
    private Directory currDir;
    private HashMap<String, Command> commands = new HashMap<>();

    public FileSystem(String name, Command[] commands) {
        this.mainDir = new Directory(null, name);
        this.currDir = this.mainDir;
        for (Command command : commands) {
            this.commands.put(command.getName(), command);
        }
    }

    public void start() {
        Directory resultDir;
        printDirPath(currDir);
        ArrayList<String> splittedCommand = processCommand(readCommand());
        while (!splittedCommand.get(0).equals("exit")) {
            Command command = commands.get(splittedCommand.get(0));
            if (command != null) {
                resultDir = command.exec(splittedCommand, mainDir, currDir);
                if (resultDir != null) {
                    currDir = resultDir;
                }
            }
            printDirPath(currDir);
            splittedCommand = processCommand(readCommand());
        }
    }
}
