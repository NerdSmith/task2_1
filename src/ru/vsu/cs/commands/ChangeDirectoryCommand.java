package ru.vsu.cs.commands;

import ru.vsu.cs.Directory;

import java.util.ArrayList;

import static ru.vsu.cs.utils.FileSystemUtils.getDirByPath;

public class ChangeDirectoryCommand implements Command{

    @Override
    public Directory exec(ArrayList<String> splittedCommand, Directory mainDir, Directory currDir) {
        Directory targetDir = getDirByPath(splittedCommand.get(1), mainDir, currDir);
        if (targetDir != null) {
            return targetDir;
        }
        return currDir;
    }
}
