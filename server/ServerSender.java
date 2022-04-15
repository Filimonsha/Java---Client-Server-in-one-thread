package ru.itmo.lab.server;

import ru.itmo.lab.PrintInformation;
import ru.itmo.lab.data.Coordinates;
import ru.itmo.lab.data.Vehicle;
import ru.itmo.lab.userUtils.UserInput;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ServerSender implements Serializable {
    private static final int port = 9000;
    private ServerMessage serverMessage;
    private final PrintInformation printInformation = new PrintInformation(System.out);
    private final UserInput userInput = new UserInput();
    public ServerSender(ServerMessage serverMessage) {
        this.serverMessage = serverMessage;
    }


    public void sendMessage(DatagramSocket serverSocket, DatagramPacket datagramPacket) throws IOException {
        byte[] buffer = new byte[1024];

        try (ByteArrayOutputStream byteStream = new
                ByteArrayOutputStream(5000);
             ObjectOutputStream os = new ObjectOutputStream(new
                     BufferedOutputStream(byteStream));) {
            os.flush();
            os.writeObject(serverMessage);
            os.close();
            byte[] sendBuf = byteStream.toByteArray();
            DatagramPacket newDatagramPacket = new DatagramPacket(sendBuf, sendBuf.length,datagramPacket.getAddress(),datagramPacket.getPort());
            System.out.println("Данные отправляются с сервера! " + Arrays.toString(newDatagramPacket.getData()));
            serverSocket.send(newDatagramPacket);
        }

    }
    public Vehicle sendMessageOnCreateObject(){
        printInformation.printInStream("Идет создание объекта! ");

        boolean fUserInputAllValue = false;

        var newBuilderVehicle = Vehicle.builder();
        Vehicle newObject = null;

        newBuilderVehicle.id((long) ((int) (Math.random() * 1000)));
        newBuilderVehicle.creationDate(LocalDateTime.now());
        while (!fUserInputAllValue) {
            newBuilderVehicle.name(userInput.getString("Введите имя объекта"));
            newBuilderVehicle.coordinates(new Coordinates(userInput.getInt("Введите координату X (int) :"), userInput.getInt("Введите координату Y (int) :")));
            newBuilderVehicle.enginePower(userInput.getInt("Введите engine power(int) :"));
            newBuilderVehicle.numberOfWheels(userInput.getInt("Введите количество колес (не наркотики) (int) :"));
            newBuilderVehicle.type(userInput.getVehicleEnum("Введите тип транспортного средства"));
            newBuilderVehicle.fuelType(userInput.getFuelType("Введите тип топлива"));
            newObject = newBuilderVehicle.build();
            System.out.println(newObject);
            fUserInputAllValue = true;
        }
        return newObject;
    }
}
