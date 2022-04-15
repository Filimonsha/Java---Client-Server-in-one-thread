package ru.itmo.lab.data;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
@Builder
public class Vehicle implements Comparable<Vehicle>, Serializable {
    @NonNull
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NonNull
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NonNull
    private Coordinates coordinates; //Поле не может быть null
    @NonNull
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @NonNull
    private int enginePower; //Значение поля должно быть больше 0
    @NonNull
    private int numberOfWheels; //Значение поля должно быть больше 0
    @NonNull
    private VehicleType type; //Поле не может быть null
    @NonNull
    private FuelType fuelType; //Поле не может быть null

    @Override
    public String toString(){
        return this.id + "," + this.name + "," +  this.coordinates.getX() + "," + this.coordinates.getY() + "," + this.creationDate + "," +  this.enginePower + "," +  this.numberOfWheels + "," + this.type + "," +  this.fuelType;
    };

    @Override
    public int compareTo(Vehicle o) {
        return  (o.getNumberOfWheels()) - this.getNumberOfWheels();
    }


}
