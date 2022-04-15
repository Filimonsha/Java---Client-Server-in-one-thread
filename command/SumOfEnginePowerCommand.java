package ru.itmo.lab.command;

import ru.itmo.lab.collection.CollectionManager;

public class SumOfEnginePowerCommand implements Command {
    private final CollectionManager collectionManager;
    public SumOfEnginePowerCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "sum_of_engine_power";
    }

    @Override
    public void execute(String[] params) {
        collectionManager.sumOfEnginePower();
    }
}
