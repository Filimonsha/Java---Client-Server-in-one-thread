package ru.itmo.lab.command;

import ru.itmo.lab.data.Vehicle;
import ru.itmo.lab.fileStream.FileReader;
import ru.itmo.lab.collection.CollectionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для регистрации команд
 */
public class CommandManager {

    private CollectionManager collectionManager;

    private static final Map<String, Command> commands = new HashMap<>();

    //    Для комманд с вводом элемента
    private static final Map<String, Command> commandsWithElement = new HashMap<>() {
    };

    private static void registerCommandWithElement(Command command) {
        commandsWithElement.put(command.getName(), command);
    }

    public static Command getCommands(String nameOfCommand) {
        return commands.get(nameOfCommand);
    }

    private void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public CommandManager(CollectionManager collectionManager, Vehicle newObject) {
        this.collectionManager = collectionManager;
        collectionManager.createdObj = newObject;
//        Свят сказал так можно (@_@)
        registerCommand(new HelpCommand(collectionManager));
        registerCommand(new InfoCommand(collectionManager));
        registerCommand(new ShowCommand(collectionManager));
        registerCommand(new InsertCommand(collectionManager));
        registerCommand(new RemoveCommand(collectionManager));
        registerCommand(new UpdateCommand(collectionManager));
        registerCommand(new ClearCommand(collectionManager));
        registerCommand(new SaveCommand(collectionManager));
        registerCommand(new ExitCommand());
        registerCommand(new SumOfEnginePowerCommand(collectionManager));
        registerCommand(new FilterContainsNameCommand(collectionManager));
        registerCommand(new PrintFieldDescendingFuelTypeCommand(collectionManager));
        registerCommand(new RemoveGreaterCommand(collectionManager));
        registerCommand(new RemoveLowerCommand(collectionManager));
        registerCommand(new ExecuteScriptCommand(collectionManager));

//        Команды с параметром
        registerCommandWithElement(new RemoveCommand(collectionManager));
        registerCommandWithElement(new UpdateCommand(collectionManager));
        registerCommandWithElement(new InsertCommand(collectionManager));
        registerCommandWithElement(new FilterContainsNameCommand(collectionManager));
        registerCommandWithElement(new ExecuteScriptCommand(collectionManager));
//        registerCommandWithElement(new );
    }

    public static boolean isItCommandWithParams(String name) {
        try {
            commandsWithElement.get(name).getName();
            return true;
        } catch (NullPointerException e) {
            return false;
        }

    }

    public static void printCommands() {
        for (String key : commands.keySet()
        ) {
            System.out.println(key + commands.get(key));
        }
//        System.out.println(commands );

    }


}
