package ru.itmo.lab.command;

import ru.itmo.lab.collection.CollectionManager;

public class InfoCommand implements Command {
    private final CollectionManager collectionManager;
    public InfoCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public void execute(String[] params) {
        collectionManager.info();
    }
}
