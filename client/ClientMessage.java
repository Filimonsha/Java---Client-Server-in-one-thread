package ru.itmo.lab.client;

import ru.itmo.lab.command.Command;
import ru.itmo.lab.data.Vehicle;

import java.io.Serializable;
import java.util.Arrays;

public class ClientMessage implements Serializable {
    public Command command;
    public String[] arg;
    public Vehicle obj;

    public ClientMessage(Command command, String[] arg, Vehicle obj) {
        this.command = command;
        this.arg = arg;
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "ClientMessage{" +
                "command=" + command +
                ", arg=" + Arrays.toString(arg) +
                '}';
    }
}
