package ru.vsu.cs.commands;

import ru.vsu.cs.Directory;

import java.util.ArrayList;

public class MakeDirectoryCommand extends Command {
    public MakeDirectoryCommand(String name) {
        super(name);
    }

    @Override
    public Directory exec(ArrayList<String> splittedCommand, Directory mainDir, Directory currDir) {
        String dirName = splittedCommand.get(1);
        if (dirName.matches("[0-9a-zA-Z_]+")) {
            currDir.addChild(new Directory(currDir, dirName));
        }
        return currDir;
    }
}
