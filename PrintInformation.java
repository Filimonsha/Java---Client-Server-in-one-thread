package ru.itmo.lab;

import java.io.PrintStream;
import java.io.Serializable;

/**
 * Универсаьный класс для вывода информации в поток
 */
public class PrintInformation implements Serializable {
    private final PrintStream print;
    public PrintInformation(PrintStream print){
        this.print = print;
    }

    public void printInStream(String in){
        print.println(in);
    }
}
