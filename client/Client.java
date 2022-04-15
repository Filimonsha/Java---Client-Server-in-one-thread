package ru.itmo.lab.client;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Client {
    private static final int port = 9000;
    private static final String host = "localhost";
    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println(inetAddress);
        byte[] receivingBuffer = new byte[65536];
        byte[] sendingBuffer = new byte[65536];

        try{
            ClientCommandReader clientCommandReader = new ClientCommandReader();
            DatagramSocket clientSocket = new DatagramSocket();

            InetSocketAddress socketAddress = new InetSocketAddress(host,port);
            DatagramPacket clientReceivingPacket = new DatagramPacket(sendingBuffer,sendingBuffer.length,socketAddress);

            System.out.println("Начало работы клиента");
            clientCommandReader.startCommandReader(socketAddress,clientSocket);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
}
