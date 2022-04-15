package ru.itmo.lab.command;

import ru.itmo.lab.collection.CollectionManager;

public class SaveCommand implements Command{
    private final CollectionManager collectionManager;
    public SaveCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public void execute(String[] params) {
        collectionManager.save();
    }
}
