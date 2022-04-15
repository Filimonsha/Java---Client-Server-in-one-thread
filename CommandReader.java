package ru.itmo.lab;
import lombok.Getter;
import ru.itmo.lab.command.CommandManager;
import ru.itmo.lab.userUtils.UserInput;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.*;

/**
 * Парсер комманд
 */
public class CommandReader {
    private final PrintInformation printInformation = new PrintInformation(System.out);
    @Getter
    private static DatagramSocket currentDatagramSocket;
    @Getter
    private static DatagramPacket currentDatagramPacket;
    private final UserInput userInput = new UserInput();
    //    private String[] arraysOfParams;
    public static boolean readerIsWorking = true;

    public CommandReader(DatagramPacket newDatagramPacket,DatagramSocket newDatagramSocket){
        currentDatagramPacket = newDatagramPacket;
        currentDatagramSocket =newDatagramSocket;
    }
    /**
     * Метод для запуска парсера
     */
    public void startCommandReader(String nameOfCommand, String[] params) {

        Invoker invoker = new Invoker();
        Scanner scanner = new Scanner(System.in);
        System.out.println("ИМЯ КОММАНДЫ + " + nameOfCommand + Arrays.toString(params) + params.length);
        if (params.length  != 0 && !Objects.equals(params[0], "")) {
            System.out.println("1");

            if ((CommandManager.isItCommandWithParams(nameOfCommand))) {
//                    params = new String[arraysOfParams.length - 1];
//                    System.arraycopy(arraysOfParams, 1, params, 0, arraysOfParams.length - 1);
                System.out.println("2");
                if (params.length == 1) {
                    System.out.println("3");
                    try {
                        invoker.findCommand(nameOfCommand, params);

                    } catch (Exception e) {
                        e.printStackTrace();
                        printInformation.printInStream(e + " {er:Except:1} Введите правильную команду");
                    }

                }
            } else printInformation.printInStream("Для этой комманды не должно быть параметров");

        } else if (!CommandManager.isItCommandWithParams(nameOfCommand)) {
            System.out.println("4");
            System.out.println(" ssss" +  nameOfCommand + " " + params);

            try {
                invoker.findCommand(nameOfCommand, params);

            } catch (Exception e) {
                e.getMessage();
                e.printStackTrace();
                printInformation.printInStream(" Введите правильную команду");

            }
        } else {
            printInformation.printInStream("Этой команде нужен аргумент");
        }

    }


}
