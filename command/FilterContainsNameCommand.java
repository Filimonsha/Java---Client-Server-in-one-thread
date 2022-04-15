package ru.itmo.lab.command;

import ru.itmo.lab.collection.CollectionManager;

public class FilterContainsNameCommand implements Command {
    private final CollectionManager collectionManager;
    public FilterContainsNameCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "filter_contains_name";
    }

    @Override
    public void execute(String[] params) {
        collectionManager.filterContainsName(params[0]);
    }
}
