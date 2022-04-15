package ru.itmo.lab.command;

import ru.itmo.lab.collection.CollectionManager;

public class PrintFieldDescendingFuelTypeCommand implements Command{
    private final CollectionManager collectionManager;
    public PrintFieldDescendingFuelTypeCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "print_field_descending_fuel_type";
    }

    @Override
    public void execute(String[] params) {
        collectionManager.printFieldDescendingFuelType();
    }
}
