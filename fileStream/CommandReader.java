package ru.itmo.lab.fileStream;

import lombok.Getter;

import java.io.*;
import java.util.ArrayList;

public class CommandReader {
    @Getter
    private final ArrayList<String> listOfCommand = new ArrayList<>();
    public void read(String fileName){
        File file = new File(fileName);
        System.out.println(file);
        try {
            FileInputStream f = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(f));
            String a;
            while ((a = reader.readLine())!=null){
                listOfCommand.add(a);
            }
            f.close();
        }
        catch (IOException e){
            System.out.println("Invalid name of file.");
        }
    }
}
