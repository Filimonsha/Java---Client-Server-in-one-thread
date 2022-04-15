package ru.itmo.lab.command;

import java.io.Serializable;

/**
 * Интерфейс от которого будут наследоваться все команды
 */
public interface Command extends Serializable {
    String getName();
    void execute(String[] params);
}
