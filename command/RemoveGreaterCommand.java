package ru.itmo.lab.command;

import ru.itmo.lab.collection.CollectionManager;

public class RemoveGreaterCommand implements Command{
    private final CollectionManager collectionManager;
    public RemoveGreaterCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public void execute(String[] params) {
        collectionManager.removeGreater();
    }
}
