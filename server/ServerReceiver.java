package ru.itmo.lab.server;

import lombok.Getter;
import ru.itmo.lab.CommandReader;
import ru.itmo.lab.client.ClientMessage;
import ru.itmo.lab.command.CommandManager;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Arrays;

public class ServerReceiver {
    @Getter
    private static ServerSocket serverSock;
    @Getter
    private static DatagramPacket datagramPack;
    public void getMessage(DatagramSocket serverSocket, DatagramPacket datagramPacket) {
        byte[] buffer = new byte[1024];
        DatagramPacket newDatagramPacket = new DatagramPacket(buffer,buffer.length);
        try{

            serverSocket.receive(newDatagramPacket);
            System.out.println("Сервер принял сообщение + " + Arrays.toString(newDatagramPacket.getData()));
            datagramPack = newDatagramPacket;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(newDatagramPacket.getData())))
        {
            ClientMessage clientMessage = (ClientMessage) ois.readObject();
            System.out.println(clientMessage);
            CommandManager commandManager = new CommandManager(Server.collectionManager,clientMessage.obj);
            CommandReader commandReader = new CommandReader(datagramPacket,serverSocket);
            commandReader.startCommandReader(clientMessage.command.getName(),clientMessage.arg);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
