package ru.itmo.lab.command;

import ru.itmo.lab.collection.CollectionManager;

public class ShowCommand implements Command {
    private final CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager){

        System.out.println("АААААААААа плказать из конструктора");
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public void execute(String[] params) {
        System.out.println("АААААААААа показать");
        collectionManager.show();
    }
}
