package ru.vsu.cs.commands;

import ru.vsu.cs.nodes.Directory;

import java.util.ArrayList;

public abstract class Command {
    private String name;

    public Command(String name) {
        this.name = name;
    }

    public abstract Directory exec(ArrayList<String> splittedCommand, Directory mainDir, Directory currDir);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
