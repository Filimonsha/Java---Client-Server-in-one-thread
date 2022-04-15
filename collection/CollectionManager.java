package ru.itmo.lab.collection;

import lombok.Getter;
import ru.itmo.lab.Invoker;
import ru.itmo.lab.PrintInformation;
import ru.itmo.lab.data.Coordinates;
import ru.itmo.lab.data.Vehicle;
import ru.itmo.lab.fileStream.*;
import ru.itmo.lab.server.*;
import ru.itmo.lab.userUtils.UserInput;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Класс управления коллекцией, в нее подгружаетяс коллекця из файла
 */

//Data access object
public class CollectionManager implements Serializable {
    public Vehicle createdObj;
    @Getter
    private final TimedCollection<Hashtable<String, Vehicle>> hashtable = new TimedCollection<>(new Hashtable<>());
    private final PrintInformation printInformation = new PrintInformation(System.out);
    private final UserInput userInput = new UserInput();
    private final WriteIntoFile writeIntoFile = new WriteIntoFile();
    private final Hashtable<String, Vehicle> copyOfCollection = new Hashtable<>();
    private final FileSaver fileSaver = new FileSaver();

    /**
     * Конструктор для подгрузки коллекции из файла
     *
     * @param loadedCollection
     */
    public CollectionManager(Map<String, Vehicle> loadedCollection) {
        hashtable.getCollection().putAll(loadedCollection);
    }


    public String prepareCollectionToOutput() {
        StringBuilder stringFormatOfCollection = new StringBuilder();
        var collection = this.hashtable.getCollection();
        for (String key : collection.keySet()
        ) {
            stringFormatOfCollection.append(collection.get(key)).append("\n");
        }
        return stringFormatOfCollection.toString();
    }


    //    Команды
    //    Help
    public void help() {
        ServerMessage serverMessage = new ServerMessage("""
                help
                info
                show
            """);
        System.out.println("отпавляем данные с help");
        ServerSender serverSender = new ServerSender(serverMessage);
        try {
            serverSender.sendMessage(ru.itmo.lab.CommandReader.getCurrentDatagramSocket(), ServerReceiver.getDatagramPack());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Отправлено!");

    }

    //    Info
    public void info() {
        String[] listik = hashtable.getClass().getName().split("\\.");
        System.out.println();

        ServerMessage serverMessage = new ServerMessage("Collection information:\n" +
                "Type: " + listik[listik.length - 1] + ".\n" +
                "Initialize date: " + hashtable.getInitializationTime() + ".\n" +
                "Element count: " + hashtable.getCollection().size());
        System.out.println("отпавляем данные с info: ");
        ServerSender serverSender = new ServerSender(serverMessage);
        try {
            serverSender.sendMessage(ru.itmo.lab.CommandReader.getCurrentDatagramSocket(), ServerReceiver.getDatagramPack());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Отправлено!");
    }

    //    Show
    public void show() {
//        System.out.println();
        ServerMessage serverMessage = new ServerMessage(hashtable.getCollection().values().stream().map(x -> "\n" + x.toString()).collect(Collectors.toList()).toString());
        System.out.println("отпавляем данные с show");
        ServerSender serverSender = new ServerSender(serverMessage);
        try {
            serverSender.sendMessage(ru.itmo.lab.CommandReader.getCurrentDatagramSocket(), ServerReceiver.getDatagramPack());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Отправлено!");
    }

    //    Insert
    public void insert(String key) {
        ServerMessage serverMessage = new ServerMessage("Элемент добавлен !");
        ServerSender serverSender = new ServerSender(serverMessage);
        try {
            serverSender.sendMessage(ru.itmo.lab.CommandReader.getCurrentDatagramSocket(), ServerReceiver.getDatagramPack());

        } catch (IOException eio) {
            System.out.println("Хуево все сделали");

            eio.printStackTrace();
        }
        hashtable.getCollection().put(key, createdObj);

    }

    //   Update
    public void update(String id) {
//        Находим элемент коллекции, id которого равен параметру
        boolean objectWasFinded = false;
        String keyOfThisObj = null;
        Vehicle thisObj = null;
        try {
            for (String key : hashtable.getCollection().keySet()
            ) {

                if (!objectWasFinded) {
                    var iterableObj = hashtable.getCollection().get(key);

                    if (iterableObj.getId() == Long.parseLong(id)) {
                        thisObj = iterableObj;
                        printInformation.printInStream("Вы обновили элемент! ");
                        objectWasFinded = true;
                        keyOfThisObj = key;
                    }
                } else break;
            }
        }catch (NumberFormatException e){
            printInformation.printInStream("Неверный формат ID");
            ServerMessage serverMessage = new ServerMessage("ID должен быть числом!");
            ServerSender serverSender = new ServerSender(serverMessage);
            try {
                serverSender.sendMessage(ru.itmo.lab.CommandReader.getCurrentDatagramSocket(), ServerReceiver.getDatagramPack());

            } catch (IOException eio) {
                System.out.println("Хуево все сделали");

                eio.printStackTrace();
            }
        }

        if (objectWasFinded) {
            hashtable.getCollection().replace(keyOfThisObj, thisObj, createdObj);
            ServerMessage serverMessage = new ServerMessage("Элемент успешно был обновлен!");
            System.out.println("отпавляем данные с update");
            ServerSender serverSender = new ServerSender(serverMessage);
            try {
                serverSender.sendMessage(ru.itmo.lab.CommandReader.getCurrentDatagramSocket(), ServerReceiver.getDatagramPack());

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Отправлено!");
        }
        else {
            printInformation.printInStream("Элемента с таким ID не найдено");
            ServerMessage serverMessage = new ServerMessage("Вы неверно ввели элемент ID! ");
            ServerSender serverSender = new ServerSender(serverMessage);
            try {
                serverSender.sendMessage(ru.itmo.lab.CommandReader.getCurrentDatagramSocket(), ServerReceiver.getDatagramPack());

            } catch (IOException e) {
                System.out.println("Хуево все сделали");

                e.printStackTrace();
            }
        }
    }

    //    Remove
    public void remove_key(String key) {
        hashtable.getCollection().remove(key);
        System.out.println("отпавляем данные с remove");
        ServerMessage serverMessage = new ServerMessage("Элемент удален !");
        ServerSender serverSender = new ServerSender(serverMessage);
        try {
            serverSender.sendMessage(ru.itmo.lab.CommandReader.getCurrentDatagramSocket(), ServerReceiver.getDatagramPack());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Отправлено!");
    }

    //  Clear
    public void clear() {
        hashtable.getCollection().clear();
        System.out.println("отпавляем данные с clear");
        ServerMessage serverMessage = new ServerMessage("Коллекция очищена!");
        ServerSender serverSender = new ServerSender(serverMessage);
        try {
            serverSender.sendMessage(ru.itmo.lab.CommandReader.getCurrentDatagramSocket(), ServerReceiver.getDatagramPack());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Отправлено!");
    }

    //    Save
    public void save() {
        writeIntoFile.saveCollectionInFile(this);
    }

    public void executeScript1(String line){
    }

    //    Execute
    public void executeScript(String fileName) {
        String[] arrayOfParams;
        CommandReader commandReader = new CommandReader();
        FileChecker fileChecker = new FileChecker(fileSaver);
        if (fileChecker.checkFileInList(new ArrayList<>(),fileName)) {
            fileSaver.getFileNameList().add(fileName);
            commandReader.read(fileName);
        this.fileSaver.save(fileName);
        Invoker invoker = new Invoker();
        for (String command : commandReader.getListOfCommand()) {
            String[] params = {""};
            arrayOfParams = command.split(" +");
            if (arrayOfParams.length > 1) {
                params = new String[arrayOfParams.length - 1];
                System.arraycopy(arrayOfParams, 1, params, 0, arrayOfParams.length - 1);
            }
            if (params.length <= 1) {
                try {
                    invoker.findCommand(arrayOfParams[0], params);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Too many parameters. Use \"help\" to get a list of available commands.");
            }
        }
        }
        fileSaver.getFileNameList().clear();
    }


    //    remove_greater
    public void removeGreater() {
//        var createdElement = createNewVehicle("Укажите элемент !: ");
//        copyOfCollection.putAll(hashtable.getCollection());
//        System.out.println("але" + copyOfCollection);
//        copyOfCollection.forEach((key, obj) -> {
//            if (createdElement.compareTo(obj) < 0) {
//                System.out.println(createdElement + " меньше " + obj);
//                hashtable.getCollection().remove(key);
//            } else {
//                System.out.println("ыы");
//            }
//        });
    }

    //    remove_lower
    public void removeLower() {
//        var createdElement = createNewVehicle("Укажите элемент !: ");
//        copyOfCollection.forEach((key, obj) -> {
//            if (createdElement.compareTo(obj) > 0) hashtable.getCollection().remove(key);
//        });
    }

    //    sum_of_engine_power
    public void sumOfEnginePower() {
        AtomicInteger sumOfEnginePower = new AtomicInteger();
//        for (String key : hashtable.getCollection().keySet()
//        ) {
//            sumOfEnginePower += hashtable.getCollection().get(key).getEnginePower();
//        }

//        printInformation.printInStream("Сумма значений поля EnginePower:  " + String.valueOf(sumOfEnginePower));
        hashtable.getCollection().values().forEach((x) -> sumOfEnginePower.addAndGet(x.getEnginePower()));
        ServerMessage serverMessage = new ServerMessage(sumOfEnginePower.toString());
        System.out.println("отпавляем данные с sumOfEnginePower");
        ServerSender serverSender = new ServerSender(serverMessage);
        try {
            serverSender.sendMessage(ru.itmo.lab.CommandReader.getCurrentDatagramSocket(), ServerReceiver.getDatagramPack());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Отправлено!");
    }

    //    filter_contains_name
    public void filterContainsName(String name) {
        var result = hashtable.getCollection().values().stream()
                .filter((x) -> x.getName().contains(name))
                .map(Vehicle::toString)
                .collect(Collectors.joining("\n"));

        ServerMessage serverMessage = new ServerMessage(result);
        System.out.println("отпавляем данные с filterContainsName");
        ServerSender serverSender = new ServerSender(serverMessage);
        try {
            serverSender.sendMessage(ru.itmo.lab.CommandReader.getCurrentDatagramSocket(), ServerReceiver.getDatagramPack());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Отправлено!");
    }

    //    print_field_descending_fuel_type
    public void printFieldDescendingFuelType() {
        hashtable.getCollection().values().stream().sorted(Comparator.comparing(Vehicle::getFuelType)).forEach((obj) -> printInformation.printInStream(String.valueOf(obj)));

        ServerMessage serverMessage = new ServerMessage(hashtable.getCollection().values().stream().sorted(Comparator.comparing(Vehicle::getFuelType)).collect(Collectors.toList()).toString());
        System.out.println("отпавляем данные с printFieldDescendingFuelType");
        ServerSender serverSender = new ServerSender(serverMessage);
        try {
            serverSender.sendMessage(ru.itmo.lab.CommandReader.getCurrentDatagramSocket(), ServerReceiver.getDatagramPack());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Отправлено!");
    }
}
