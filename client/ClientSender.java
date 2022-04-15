package ru.itmo.lab.client;

import lombok.Getter;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;

public class ClientSender {
    private ClientMessage clientMessage;
    @Getter
    private DatagramPacket receivedPacket;
    public ClientSender(ClientMessage clientMessage) {
        this.clientMessage = clientMessage;
    }


    public void sendMessage(InetSocketAddress inetSocketAddress, DatagramSocket clientSocket) {
        try
                (ByteArrayOutputStream byteStream = new
                        ByteArrayOutputStream(5000);
                 ObjectOutputStream os = new ObjectOutputStream(new
                         BufferedOutputStream(byteStream));) {
            os.flush();
            os.writeObject(clientMessage);
            os.close();
            byte[] sendBuf = byteStream.toByteArray();

            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, inetSocketAddress);
            System.out.println("Данные отправляются с клиента");

            clientSocket.send(packet);
            clientSocket.setSoTimeout(3000);

        } catch (SocketTimeoutException e){
            System.out.println("Сервер не отвечает, попробуйте позже!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
