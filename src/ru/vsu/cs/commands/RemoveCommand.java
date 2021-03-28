package ru.vsu.cs.commands;

import ru.vsu.cs.Directory;

import java.util.ArrayList;

public class RemoveCommand extends Command {
    public RemoveCommand(String name) {
        super(name);
    }

    @Override
    public Directory exec(ArrayList<String> splittedCommand, Directory mainDir, Directory currDir) {
        String name = splittedCommand.get(1);
        currDir.getChildren().remove(name);
        return currDir;
    }
}
