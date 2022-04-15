package ru.itmo.lab.command;

import ru.itmo.lab.collection.CollectionManager;

public class RemoveLowerCommand implements Command{
    private final CollectionManager collectionManager;
    public RemoveLowerCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public String getName() {
        return "remove_lower";
    }

    @Override
    public void execute(String[] params) {
        collectionManager.removeLower();
    }
}
