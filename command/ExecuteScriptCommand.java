package ru.itmo.lab.command;

import ru.itmo.lab.collection.CollectionManager;

public class ExecuteScriptCommand implements Command{
    private final CollectionManager collectionManager;
    public ExecuteScriptCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public void execute(String[] params) {
        collectionManager.executeScript(params[0]);
    }
}
