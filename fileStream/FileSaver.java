package ru.itmo.lab.fileStream;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;

public class FileSaver implements Serializable {
    @Getter
    private final ArrayList<String> fileNameList = new ArrayList<>();

    public void save(String nameOfFile){
        fileNameList.add(nameOfFile);
    }
}
