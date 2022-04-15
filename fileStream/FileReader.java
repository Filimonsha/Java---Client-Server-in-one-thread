package ru.itmo.lab.fileStream;

import ru.itmo.lab.collection.CollectionLoader;
import ru.itmo.lab.data.Coordinates;
import ru.itmo.lab.data.FuelType;
import ru.itmo.lab.data.Vehicle;
import ru.itmo.lab.data.VehicleType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class FileReader {
    private String pathOfFile;
    private final ArrayList<String> listOFlines = new ArrayList<>();
    private final CollectionLoader<String, Vehicle> loadedCollection = new CollectionLoader<>();

    public FileReader() {
        readOutFile();
    }

    private void readOutFile(){
        var env = System.getenv();
        var nameOfFile = env.get("COMPUTERNAME");
        Path pathOfFile = Path.of(nameOfFile + ".csv").toAbsolutePath();


        try (
                InputStream inputStream = new FileInputStream(pathOfFile.toString());
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
        ) {
//            Пропускаем нейминг полей
            String nextLine =  reader.readLine();
//            Создаем коллекцию для хранения

//            Считываем все строки
            while (nextLine!=null){
                nextLine = reader.readLine();
                if (nextLine!=null) {
                    String [] arrayOfParam = nextLine.split(",");
                    loadedCollection.addElementToCollection(
                            arrayOfParam[0],
                            Vehicle.builder().id((Long.parseLong(arrayOfParam[0])))
                                    .name(arrayOfParam[1]).coordinates(new Coordinates(Integer.parseInt(arrayOfParam[2]),
                                            Integer.parseInt(arrayOfParam[3]))).creationDate(LocalDateTime.of(2014, 9, 19, 14, 5))
                                    .enginePower(Integer.parseInt(arrayOfParam[5])).numberOfWheels(Integer.parseInt(arrayOfParam[6]))
                                    .type(VehicleType.valueOf(arrayOfParam[7]))
                                    .fuelType(FuelType.valueOf(arrayOfParam[8])).build()

                    );
                }

            }


        } catch (IOException e) {
            try {
                Files.createFile(Path.of("newFile.csv").toAbsolutePath());
            } catch (IOException ex) {
                System.out.println("Да ацтаньте вы");
                ex.printStackTrace();
            }
        }
    }



    public Map<String,Vehicle> getLoadedCollectionFromFile(){
        return loadedCollection.getCollection();
    }


}