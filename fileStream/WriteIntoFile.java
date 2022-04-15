package ru.itmo.lab.fileStream;

import ru.itmo.lab.collection.CollectionManager;
import ru.itmo.lab.data.Vehicle;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.Map;

public class WriteIntoFile implements Serializable {
    private String pathOfFile;
    private final Map<String,String> env = System.getenv();
    private final String nameOfFile = env.get("COMPUTERNAME");


    public void saveCollectionInFile(CollectionManager collectionManager){
        Path pathOfFile = Path.of(nameOfFile + ".csv").toAbsolutePath();

        try {
            FileWriter fileWriter = new FileWriter(pathOfFile.toString());
            fileWriter.write(collectionManager.prepareCollectionToOutput());
            fileWriter.close();
        } catch (Exception e) {
            try{
                FileWriter fileWriter = new FileWriter(Path.of("newFile.csv").toAbsolutePath().toString());
                fileWriter.write(collectionManager.prepareCollectionToOutput());
                fileWriter.close();
                System.out.println("sssssssssssssssss");
            } catch (IOException ex) {
                System.out.println("Сломалось");
                ex.printStackTrace();
            }
        }
    }


}
