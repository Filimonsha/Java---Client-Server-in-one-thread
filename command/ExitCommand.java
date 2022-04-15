package ru.itmo.lab.command;

import ru.itmo.lab.CommandReader;

public class ExitCommand implements Command{

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public void execute(String[] params) {
        CommandReader.readerIsWorking=false;
    }
}
