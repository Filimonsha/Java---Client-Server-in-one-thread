package ru.itmo.lab.client;

import ru.itmo.lab.PrintInformation;
import ru.itmo.lab.command.*;

import ru.itmo.lab.data.Coordinates;
import ru.itmo.lab.data.Vehicle;
import ru.itmo.lab.userUtils.UserInput;

import java.net.*;
import java.time.LocalDateTime;
import java.util.*;

public class ClientCommandReader {
    private boolean readerIsWorking = true;
    private final PrintInformation printInformation = new PrintInformation(System.out);
    private final UserInput userInput = new UserInput();
    private String[] arraysOfParams;

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

    public void startCommandReader(InetSocketAddress inetSocketAddress, DatagramSocket clientSocket) throws SocketException {

//        Invoker invoker = new Invoker();
        Scanner scanner = new Scanner(System.in);
        while (readerIsWorking) {
            System.out.println("День добрый !");
            try {
                arraysOfParams = scanner.nextLine().split(" +");

            } catch (NoSuchElementException e) {
                System.out.println("Поток был сломан злыми втшниками");
                System.exit(1);
            }
            System.out.println(Arrays.toString(arraysOfParams) + arraysOfParams.length);
            String[] params = {""};
            if (arraysOfParams.length > 1) {
                params = new String[arraysOfParams.length - 1];
                System.arraycopy(arraysOfParams, 1, params, 0, arraysOfParams.length - 1);
                System.out.println(params);
                if (params.length == 1) {

                    try {
                        ClientMessage clientMessage;
                        ClientReceiver clientReceiver;
                        ClientSender createClientMessageAndSend;

                        switch (arraysOfParams[0]) {
                            case "insert":
                                printInformation.printInStream("Идет создание объекта! ");
                                Vehicle newObject = createObject();
                                InsertCommand insertCommand = new InsertCommand(null);
                                clientMessage = new ClientMessage(insertCommand, params, newObject);
                                createClientMessageAndSend = new ClientSender(clientMessage);
                                createClientMessageAndSend.sendMessage(inetSocketAddress, clientSocket);
                                clientReceiver = new ClientReceiver();
                                clientReceiver.getMessage(clientSocket);
                                break;
                            case "remove":
                                RemoveCommand removeCommand = new RemoveCommand(null);
                                clientMessage = new ClientMessage(removeCommand, params, null);
                                createClientMessageAndSend = new ClientSender(clientMessage);
                                createClientMessageAndSend.sendMessage(inetSocketAddress, clientSocket);
                                clientReceiver = new ClientReceiver();
                                clientReceiver.getMessage(clientSocket);
                                break;
                            case "update":
                                printInformation.printInStream("Идет создание объекта в замену старого! ");
                                Vehicle newObjectUpdate = createObject();
                                UpdateCommand updateCommand = new UpdateCommand(null);
                                ClientMessage clientMessageUp = new ClientMessage(updateCommand, params, newObjectUpdate);

                                ClientSender createClientMessageAndSend1 = new ClientSender(clientMessageUp);
                                createClientMessageAndSend1.sendMessage(inetSocketAddress, clientSocket);
                                break;
                            case "execute_script":
                                ExecuteScriptStarter executeScriptStarter = new ExecuteScriptStarter();
                                executeScriptStarter.read(arraysOfParams[1]);
                                executeScriptStarter.executeStart(inetSocketAddress,clientSocket);
                                break;
                            case "remove_greater":
                                printInformation.printInStream("Идет создание объекта в remove_greater! ");
                                Vehicle newObjectRG = createObject();
                                RemoveGreaterCommand removeGreaterCommand = new RemoveGreaterCommand(null);
                                ClientMessage clientMessageRG = new ClientMessage(removeGreaterCommand, params, newObjectRG);

                                ClientSender createClientMessageAndSendRG = new ClientSender(clientMessageRG);
                                createClientMessageAndSendRG.sendMessage(inetSocketAddress, clientSocket);
                                break;
                            case "remove_lower":
                                printInformation.printInStream("Идет создание объекта в remove_lower! ");
                                Vehicle newObjectRL = createObject();
                                RemoveLowerCommand removeLowerCommand = new RemoveLowerCommand(null);
                                ClientMessage clientMessageRL = new ClientMessage(removeLowerCommand, params, newObjectRL);

                                ClientSender createClientMessageAndSendRL = new ClientSender(clientMessageRL);
                                createClientMessageAndSendRL.sendMessage(inetSocketAddress, clientSocket);
                                break;
                            case "filter_contains_name":
                                FilterContainsNameCommand filterContainsNameCommand = new FilterContainsNameCommand(null);
                                clientMessage = new ClientMessage(filterContainsNameCommand, params, null);
                                createClientMessageAndSend = new ClientSender(clientMessage);
                                createClientMessageAndSend.sendMessage(inetSocketAddress, clientSocket);
                                clientReceiver = new ClientReceiver();
                                clientReceiver.getMessage(clientSocket);
                                break;
                            default:
//                                System.out.println("Неверная команда с пармаетрами");
                        }
                    } catch (Exception e) {

                        printInformation.printInStream(e + " {er:Except:1} Введите правильную команду");
                    }

                }
//                } else printInformation.printInStream("Для этой комманды не должно быть параметров");

            } else if (!CommandManager.isItCommandWithParams(arraysOfParams[0])) {
                ClientMessage clientMessage;
                ClientReceiver clientReceiver;
                ClientSender clientSender;
                switch (arraysOfParams[0]) {
                    case ("help"):
                        HelpCommand helpCommand = new HelpCommand(null);
                        clientMessage = new ClientMessage(helpCommand, params, null);
                        clientSender = new ClientSender(clientMessage);
                        clientSender.sendMessage(inetSocketAddress, clientSocket);
                        clientReceiver = new ClientReceiver();
                        clientReceiver.getMessage(clientSocket);
                        break;
                    case ("info"):
                        InfoCommand infoCommand = new InfoCommand(null);
                        clientMessage = new ClientMessage(infoCommand, params, null);
                        clientSender = new ClientSender(clientMessage);
                        clientSender.sendMessage(inetSocketAddress, clientSocket);
                        clientReceiver = new ClientReceiver();
                        clientReceiver.getMessage(clientSocket);
                        break;
                    case ("show"):
                        ShowCommand showCommand = new ShowCommand(null);
                        clientMessage = new ClientMessage(showCommand, params, null);
                        clientSender = new ClientSender(clientMessage);
                        clientSender.sendMessage(inetSocketAddress, clientSocket);
                        clientReceiver = new ClientReceiver();
                        clientReceiver.getMessage(clientSocket);
                        break;

                    case ("sum_of_engine_power"):
                        SumOfEnginePowerCommand sumOfEnginePowerCommand = new SumOfEnginePowerCommand(null);
                        clientMessage = new ClientMessage(sumOfEnginePowerCommand, params, null);
                        clientSender = new ClientSender(clientMessage);
                        clientSender.sendMessage(inetSocketAddress, clientSocket);
                        clientReceiver = new ClientReceiver();
                        clientReceiver.getMessage(clientSocket);
                        break;
                    case ("print_field_descending_fuel_type"):
                        PrintFieldDescendingFuelTypeCommand printFieldDescendingFuelTypeCommand = new PrintFieldDescendingFuelTypeCommand(null);
                        clientMessage = new ClientMessage(printFieldDescendingFuelTypeCommand, params, null);
                        clientSender = new ClientSender(clientMessage);
                        clientSender.sendMessage(inetSocketAddress, clientSocket);
                        clientReceiver = new ClientReceiver();
                        clientReceiver.getMessage(clientSocket);
                        break;

                    case ("exit"):
                        SaveCommand saveCommand = new SaveCommand(null);
                        clientMessage = new ClientMessage(saveCommand, params, null);
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

                        for (String s : arr) {
                            printInformation.printInStream(s);
                        }
                        readerIsWorking = false;
                        break;

//                    case ("save"):
//                        SaveCommand saveCommand = new SaveCommand(null);
//                        clientMessage = new ClientMessage(saveCommand, params);
//                        createClientMessageAndSend = new CreateClientMessageAndSend(clientMessage);
//                        createClientMessageAndSend.sendMessage(inetSocketAddress, clientSocket);
//                        break;
                    case ("clear"):
                        ClearCommand clearCommand = new ClearCommand(null);
                        clientMessage = new ClientMessage(clearCommand, params, null);
                        clientSender = new ClientSender(clientMessage);
                        clientSender.sendMessage(inetSocketAddress, clientSocket);
                        clientReceiver = new ClientReceiver();
                        clientReceiver.getMessage(clientSocket);
                        break;

                    default:
                        System.out.println("Вы ввели неправильую комманду");
                        break;
                }


            } else {
                printInformation.printInStream("Этой команде нужен аргумент");
            }

        }

    }

}

