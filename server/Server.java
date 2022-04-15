package ru.itmo.lab.server;

import ru.itmo.lab.collection.CollectionManager;
import ru.itmo.lab.fileStream.FileReader;

import java.io.IOException;
import java.net.*;

import static java.lang.Thread.sleep;

public class Server {
    private static final int port = 9000;
    private static final FileReader fileReader = new FileReader();
    public static CollectionManager collectionManager = new CollectionManager(fileReader.getLoadedCollectionFromFile());

    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(port);
        while (true) {

            byte[] receivingBuff = new byte[5];
            ServerReceiver serverReceiver = new ServerReceiver();
            System.out.println("Ждем данных с клиента: ");
            serverReceiver.getMessage(serverSocket,new DatagramPacket(receivingBuff, receivingBuff.length));
        }


    }

}
