package ru.itmo.lab.client;

import ru.itmo.lab.server.ServerMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class ClientReceiver {

    public void getMessage(DatagramSocket clientSocket){
        byte[] buffer = new byte[1024];
        DatagramPacket getMessagePacket = new DatagramPacket(buffer,buffer.length);
        try{

            System.out.println("Клиент получает сообщение: ");
            clientSocket.receive(getMessagePacket);
            clientSocket.setSoTimeout(5000);
            System.out.println("Клиент принял сообщение :" + Arrays.toString(getMessagePacket.getData()));
        } catch (SocketTimeoutException e){
            System.out.println("Сервер не отвечает, попробуйте позже!");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(getMessagePacket.getData())))
        {
            ServerMessage serverMessage = (ServerMessage) ois.readObject();
            System.out.println(serverMessage.getMessageText());

        }catch (StreamCorruptedException e){

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
