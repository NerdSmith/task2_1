package ru.vsu.cs.commands;

import ru.vsu.cs.Directory;

import java.util.ArrayList;

public interface Command {
    Directory exec(ArrayList<String> splittedCommand, Directory mainDir, Directory currDir);
}
