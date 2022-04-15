package ru.itmo.lab.command;

import ru.itmo.lab.collection.CollectionManager;

public class InsertCommand implements Command{
    private final CollectionManager collectionManager;

    public InsertCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public void execute(String[] params) {
        collectionManager.insert(params[0]);
    }
}
