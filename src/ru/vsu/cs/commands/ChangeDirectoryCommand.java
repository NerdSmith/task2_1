package ru.vsu.cs.commands;

import ru.vsu.cs.nodes.Directory;

import java.util.ArrayList;

import static ru.vsu.cs.utils.FileSystemUtils.getDirByPath;

public class ChangeDirectoryCommand extends Command{

    public ChangeDirectoryCommand(String name) {
        super(name);
    }

    @Override
    public Directory exec(ArrayList<String> splittedCommand, Directory mainDir, Directory currDir) {
        Directory targetDir = getDirByPath(splittedCommand.get(1), mainDir, currDir);
        if (targetDir != null) {
            return targetDir;
        }
        return currDir;
    }
}
