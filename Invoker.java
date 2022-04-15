package ru.itmo.lab;

import ru.itmo.lab.command.CommandManager;

/**
 * Клсс для вызова у команд метода активации
 */
public class Invoker {
    public void findCommand(String name, String[] params){
        CommandManager.getCommands(name).execute(params);


    }

}
