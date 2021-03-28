package ru.vsu.cs;

import ru.vsu.cs.commands.*;

public class Main {

    public static void main(String[] args) {
        Command[] commands = new Command[]{
                new ChangeDirectoryCommand("cd"),
                new ConcatenateCommand("cat"),
                new EchoCommand("echo"),
                new ListFilesCommand("ls"),
                new MakeDirectoryCommand("mkdir"),
                new RemoveCommand("rm"),
                new TreeCommand("tree")};
        FileSystem fs = new FileSystem("mainDir", commands);
        fs.start();
    }
}
