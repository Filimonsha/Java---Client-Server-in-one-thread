package ru.itmo.lab.data;

import lombok.*;

import java.io.Serializable;

@RequiredArgsConstructor
public class Coordinates implements Serializable {
    @NonNull
    @Getter
    private int x;
    @NonNull
    @Getter
    private Integer y; //Поле не может быть null
//    public Coordinates(int x, int y){
//        this.x = x;
//        this.y =y;
//    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
