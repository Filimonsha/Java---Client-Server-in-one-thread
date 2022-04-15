package ru.itmo.lab.command;

import ru.itmo.lab.collection.CollectionManager;

public class RemoveCommand implements Command{
    private final CollectionManager collectionManager;
    public RemoveCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public void execute(String[] params) {
        collectionManager.remove_key(params[0]);
    }
}
