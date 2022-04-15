package ru.itmo.lab.client;

import ru.itmo.lab.PrintInformation;
import ru.itmo.lab.command.*;
import ru.itmo.lab.data.Coordinates;
import ru.itmo.lab.data.Vehicle;
import ru.itmo.lab.fileStream.FileChecker;
import ru.itmo.lab.fileStream.FileSaver;
import ru.itmo.lab.userUtils.UserInput;

import java.io.*;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExecuteScriptStarter {
    UserInput userInput = new UserInput();
    private final ArrayList<String> listOfCommand = new ArrayList<>();
    private final ArrayList<String> fileNameList = new ArrayList<>();
    FileSaver fileSaver = new FileSaver();
    FileChecker fileChecker = new FileChecker(fileSaver);
    public void read(String fileName){
        System.out.println(fileName);
        File file = new File(fileName);
        try {
            FileInputStream f = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(f));
            String a;
            while ((a = reader.readLine())!=null){
                System.out.println(a);
                listOfCommand.add(a);
            }
            f.close();
        }
        catch (IOException e){
            System.out.println("Invalid name of file.");
        }
        var argss = listOfCommand.toArray(new String[listOfCommand.size()]);

    }
    private Vehicle createObject() {
        boolean fUserInputAllValueUpdate = false;
        var newBuilderVehicleUpdate = Vehicle.builder();
        Vehicle newObjectUpdate = null;
        newBuilderVehicleUpdate.id((long) ((int) (Math.random() * 1000)));
        newBuilderVehicleUpdate.creationDate(LocalDateTime.now());
        while (!fUserInputAllValueUpdate) {
            newBuilderVehicleUpdate.name(userInput.getString("Введите имя объекта"));
            newBuilderVehicleUpdate.coordinates(new Coordinates(userInput.getInt("Введите координату X (int) :"), userInput.getInt("Введите координату Y (int) :")));
            newBuilderVehicleUpdate.enginePower(userInput.getInt("Введите engine power(int) :"));
            newBuilderVehicleUpdate.numberOfWheels(userInput.getInt("Введите количество колес (не наркотики) (int) :"));
            newBuilderVehicleUpdate.type(userInput.getVehicleEnum("Введите тип транспортного средства"));
            newBuilderVehicleUpdate.fuelType(userInput.getFuelType("Введите тип топлива"));
            newObjectUpdate = newBuilderVehicleUpdate.build();

            fUserInputAllValueUpdate = true;
        }
        return newObjectUpdate;
    }
    public void executeStart (InetSocketAddress inetSocketAddress, DatagramSocket clientSocket){
        listOfCommand.forEach(x->this.executeScriptStart(x.split("-"), inetSocketAddress, clientSocket));
        fileSaver.getFileNameList().clear();
    }
    public boolean checkFileInList(ArrayList<String> list, String file){
        for (String nameOfFile: list
        ) {

            if (Objects.equals(nameOfFile, file)){
                return false;
            }
        }
        return true;
    }
    private void executeScriptStart(String[] args , InetSocketAddress inetSocketAddress, DatagramSocket clientSocket) {
        if (args.length > 1) {
            ClientMessage clientMessage;
            ClientReceiver clientReceiver;
            ClientSender createClientMessageAndSend;
//            PrintInformation printInformation= new PrintInformation();
            var sendingArgs = args[1].split("-");
            switch (args[0]) {
                case "insert":
//                    printInformation.printInStream("Идет создание объекта! ");
                    Vehicle newObject = createObject();
                    InsertCommand insertCommand = new InsertCommand(null);
                    clientMessage = new ClientMessage(insertCommand, sendingArgs, newObject);
                    createClientMessageAndSend = new ClientSender(clientMessage);
                    createClientMessageAndSend.sendMessage(inetSocketAddress, clientSocket);
                    clientReceiver = new ClientReceiver();
                    clientReceiver.getMessage(clientSocket);
                    break;
                case "execute_script":
                    System.out.println("1");
                    System.out.println(fileNameList);
                    if (checkFileInList(fileNameList,sendingArgs[0])) {
                        System.out.println(fileSaver.getFileNameList());
                        fileNameList.add(sendingArgs[0]);
                        System.out.println("2");
                        System.out.println(fileNameList);
                        ExecuteScriptStarter executeScriptStarter = new ExecuteScriptStarter();
                        executeScriptStarter.executeStart(inetSocketAddress, clientSocket);
                    }else{
                        System.out.println("3");
                        System.out.println("рекурсия");
                    }
                    break;
                case "remove":
                    RemoveCommand removeCommand = new RemoveCommand(null);
                    clientMessage = new ClientMessage(removeCommand, sendingArgs, null);
                    createClientMessageAndSend = new ClientSender(clientMessage);
                    createClientMessageAndSend.sendMessage(inetSocketAddress, clientSocket);
                    clientReceiver = new ClientReceiver();
                    clientReceiver.getMessage(clientSocket);
                    break;
                case "update":
//                    printInformation.printInStream("Идет создание объекта в замену старого! ");
                    Vehicle newObjectUpdate = createObject();
                    UpdateCommand updateCommand = new UpdateCommand(null);
                    ClientMessage clientMessageUp = new ClientMessage(updateCommand, sendingArgs, newObjectUpdate);

                    ClientSender createClientMessageAndSend1 = new ClientSender(clientMessageUp);
                    createClientMessageAndSend1.sendMessage(inetSocketAddress, clientSocket);
                    break;

                case "remove_greater":
//                    printInformation.printInStream("Идет создание объекта в remove_greater! ");
                    Vehicle newObjectRG = createObject();
                    RemoveGreaterCommand removeGreaterCommand = new RemoveGreaterCommand(null);
                    ClientMessage clientMessageRG = new ClientMessage(removeGreaterCommand, sendingArgs, newObjectRG);

                    ClientSender createClientMessageAndSendRG = new ClientSender(clientMessageRG);
                    createClientMessageAndSendRG.sendMessage(inetSocketAddress, clientSocket);
                    break;
                case "remove_lower":
//                    printInformation.printInStream("Идет создание объекта в remove_lower! ");
                    Vehicle newObjectRL = createObject();
                    RemoveLowerCommand removeLowerCommand = new RemoveLowerCommand(null);
                    ClientMessage clientMessageRL = new ClientMessage(removeLowerCommand, sendingArgs, newObjectRL);

                    ClientSender createClientMessageAndSendRL = new ClientSender(clientMessageRL);
                    createClientMessageAndSendRL.sendMessage(inetSocketAddress, clientSocket);
                    break;
                case "filter_contains_name":
                    FilterContainsNameCommand filterContainsNameCommand = new FilterContainsNameCommand(null);
                    clientMessage = new ClientMessage(filterContainsNameCommand, sendingArgs, null);
                    createClientMessageAndSend = new ClientSender(clientMessage);
                    createClientMessageAndSend.sendMessage(inetSocketAddress, clientSocket);
                    clientReceiver = new ClientReceiver();
                    clientReceiver.getMessage(clientSocket);
                    break;
            }
        } else {
            ClientMessage clientMessage;
            ClientReceiver clientReceiver;
            ClientSender clientSender;
            switch (args[0]) {
                case ("help"):
                    HelpCommand helpCommand = new HelpCommand(null);
                    clientMessage = new ClientMessage(helpCommand, new String[0], null);
                    clientSender = new ClientSender(clientMessage);
                    clientSender.sendMessage(inetSocketAddress, clientSocket);
                    clientReceiver = new ClientReceiver();
                    clientReceiver.getMessage(clientSocket);
                    break;
                case ("info"):
                    InfoCommand infoCommand = new InfoCommand(null);
                    clientMessage = new ClientMessage(infoCommand, new String[0], null);
                    clientSender = new ClientSender(clientMessage);
                    clientSender.sendMessage(inetSocketAddress, clientSocket);
                    clientReceiver = new ClientReceiver();
                    clientReceiver.getMessage(clientSocket);
                    break;
                case ("show"):
                    ShowCommand showCommand = new ShowCommand(null);
                    clientMessage = new ClientMessage(showCommand, new String[0], null);
                    clientSender = new ClientSender(clientMessage);
                    clientSender.sendMessage(inetSocketAddress, clientSocket);
                    clientReceiver = new ClientReceiver();
                    clientReceiver.getMessage(clientSocket);
                    break;

                case ("sum_of_engine_power"):
                    SumOfEnginePowerCommand sumOfEnginePowerCommand = new SumOfEnginePowerCommand(null);
                    clientMessage = new ClientMessage(sumOfEnginePowerCommand, new String[0], null);
                    clientSender = new ClientSender(clientMessage);
                    clientSender.sendMessage(inetSocketAddress, clientSocket);
                    clientReceiver = new ClientReceiver();
                    clientReceiver.getMessage(clientSocket);
                    break;
                case ("print_field_descending_fuel_type"):
                    PrintFieldDescendingFuelTypeCommand printFieldDescendingFuelTypeCommand = new PrintFieldDescendingFuelTypeCommand(null);
                    clientMessage = new ClientMessage(printFieldDescendingFuelTypeCommand, new String[0], null);
                    clientSender = new ClientSender(clientMessage);
                    clientSender.sendMessage(inetSocketAddress, clientSocket);
                    clientReceiver = new ClientReceiver();
                    clientReceiver.getMessage(clientSocket);
                    break;

                case ("exit"):
                    SaveCommand saveCommand = new SaveCommand(null);
                    clientMessage = new ClientMessage(saveCommand, new String[0], null);
                    clientSender = new ClientSender(clientMessage);
                    clientSender.sendMessage(inetSocketAddress, clientSocket);
                    String[] arr = {
                            "Окончание работы",
                            "Всем спасибо всем пока",
                            "────────▓▓▓▓▓▓▓────────────▒▒▒▒▒▒",
                            "──────▓▓▒▒▒▒▒▒▒▓▓────────▒▒░░░░░░▒▒",
                            "────▓▓▒▒▒▒▒▒▒▒▒▒▒▓▓────▒▒░░░░░░░░░▒▒▒",
                            "▓▓▒▒▒▒▒▒░░░░░░░░░░░▒▒░░▒▒▒▒▒▒▒▒▒▒▒░░░░░░▒",
                            "▓▓▒▒▒▒▒▒▀▀▀▀▀███▄▄▒▒▒░░░▄▄▄██▀▀▀▀▀░░░░░░▒",
                            "▓▓▒▒▒▒▒▒▒▄▀████▀███▄▒░▄████▀████▄░░░░░░░▒",
                            "▓▓▒▒▒▒▒▒█──▀█████▀─▌▒░▐──▀█████▀─█░░░░░░▒",
                            "▓▓▒▒▒▒▒▒▒▀▄▄▄▄▄▄▄▄▀▒▒░░▀▄▄▄▄▄▄▄▄▀░░░░░░░▒",
                            "─────▓▓▒▒▒▒▒▒▒▒▒▒▄▄▄▄▄▄▄▄▄░░░░░░░░▒▒",
                            "──────▓▓▒▒▒▒▒▒▒▄▀▀▀▀▀▀▀▀▀▀▀▄░░░░░▒▒",
                            "───────▓▓▒▒▒▒▒▀▒▒▒▒▒▒░░░░░░░▀░░░▒▒",
                            "──────────────────▓▓▒░▒▒",
                            "───────────────────▓▒░▒",
                            "────────────────────▓▒",
                    };
            }
        }
    }
}