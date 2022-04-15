package ru.itmo.lab.command;

import ru.itmo.lab.collection.CollectionManager;

public class UpdateCommand implements Command{
    private final CollectionManager collectionManager;
    public UpdateCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;

    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public void execute(String[] params) {
        collectionManager.update(params[0]);
    }
}
