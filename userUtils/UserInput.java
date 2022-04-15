package ru.itmo.lab.userUtils;

import ru.itmo.lab.data.FuelType;
import ru.itmo.lab.data.VehicleType;

import java.io.Serializable;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

public class UserInput implements Serializable {
    /**
     * Парсит строковый параметр
     * @param message который выводится для пользователя
     * @return Optional, если он пустой, заново запускает парсер
     */
    public String getString(String message) {
        FunctionalInputGetter<String> getter = new FunctionalInputGetter<>();
        return getter.parseSomething((x)->{
            if (!x.contains(",") &&  !(x.isEmpty())) return Optional.of(x);
            else return Optional.empty();
        },message);

    }
    /**
     *  Парсит интовский параметр
     * @param message который выводится для пользователя
     * @return Optional, если он пустой, заново запускает парсер
     */
    public int getInt(String message) {
        FunctionalInputGetter<Integer> getter = new FunctionalInputGetter<>();
        return getter.parseSomething((x) -> {
            try {
                Integer result = Integer.parseInt(x);
                return Optional.of(result);
            } catch (NumberFormatException ignored) {
                return Optional.empty();
            }
        },message);
    }
    /**
     *  Парсит тип транспортного средства параметр
     * @param message который выводится для пользователя
     * @return Optional, если он пустой, заново запускает парсер
     */
    public VehicleType getVehicleEnum(String message){
        FunctionalInputGetter<VehicleType> getter = new FunctionalInputGetter<>();
        return getter.parseSomething((x)->{
            try{
                VehicleType enumString = VehicleType.valueOf(x);
                return Optional.of(enumString);
            }catch (Exception e){
                return Optional.empty();
            }
        },message);
    }
    /**
     *  Парсит тип транспортного топлива параметр
     * @param message который выводится для пользователя
     * @return Optional, если он пустой, заново запускает парсер
     */
    public FuelType getFuelType(String message){
        FunctionalInputGetter<FuelType> getter = new FunctionalInputGetter<>();
        return getter.parseSomething((x)->{
            try{
                FuelType enumString = FuelType.valueOf(x);
                return Optional.of(enumString);
            }catch (Exception e){
                return Optional.empty();

            }
        },message);
    }
}

/**
 * Параметризованный класс для создания лямбды выражния
 * @param <T> параметр передаваемый в Optional
 */
class FunctionalInputGetter<T> implements Serializable{
    public T parseSomething(Function<String, Optional<T>> input,String message) {
        boolean isRight = false;
        Scanner scanner = new Scanner(System.in);
        Optional<T> result = Optional.empty();

        while (!isRight) {
            System.out.println(message);
            String tmp = scanner.nextLine();

            result = input.apply(tmp);

            if (result.isPresent()) {
                isRight = true;
            }
        }
        return result.get();
    }
}
