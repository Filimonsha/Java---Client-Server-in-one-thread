package ru.itmo.lab.fileStream;

import java.util.ArrayList;
import java.util.Objects;

public class FileChecker {

    private FileSaver fileSaver = new FileSaver();

    public FileChecker(FileSaver fileSaver){
        this.fileSaver = fileSaver;
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

}
