package ru.itmo.lab.command;

import ru.itmo.lab.collection.CollectionManager;

public class HelpCommand implements Command{
    private final CollectionManager collectionManager;
    public HelpCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void execute(String[] params) {
        collectionManager.help();
    }
}
