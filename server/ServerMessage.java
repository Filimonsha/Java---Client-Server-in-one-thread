package ru.itmo.lab.server;

import lombok.Getter;
import ru.itmo.lab.data.Vehicle;

import java.io.Serializable;
import java.util.Hashtable;

public class ServerMessage implements Serializable {
//    @Getter
//    private final Hashtable<String, Vehicle> sendingData;

    @Getter
    private String messageText;

    public ServerMessage( String messageText) {
//        this.sendingData = sendingData;
        this.messageText = messageText;
    }

    @Override
    public String toString() {
        return "ServerMessage{" +
//                "sendingData=" + sendingData +
                ", messageText='" + messageText + '\'' +
                '}';
    }
}
