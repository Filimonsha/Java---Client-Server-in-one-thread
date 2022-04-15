package ru.itmo.lab.command;

import ru.itmo.lab.collection.CollectionManager;

public class ClearCommand implements Command{
    private final CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager){

        System.out.println("АААААААААа очищение из конструктора");
        this.collectionManager = collectionManager;
    }
    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public void execute(String[] params) {
        System.out.println("АААААААААа очищение");
        collectionManager.clear();
    }
}
