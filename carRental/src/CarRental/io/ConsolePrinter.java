package CarRental.io;

import CarRental.model.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ConsolePrinter {

    public void printAllCars(Collection<Car> cars) {
        Object[] array = cars.toArray();
        if (array.length == 0) {
            printer("brak aut w garazu");
        } else {
            for (Object car : array) {
                printer(car.toString());
            }
        }
    }

    public void printCarAndOwner(Map<String, Car> takenCars) {
        if (takenCars.values().isEmpty()) {
            printer("żadne auto nie zostało wypożyczone");
        } else {
            Object[] array = takenCars.values().toArray();
            Object[] array1 = takenCars.keySet().toArray();
            for (int i = 0, j = 0; i < array.length && j < array1.length; i++, j++) {
                printer(array[i] + " " + " -> numer pesel osoby, która wypożyczyła: " + array1[j]);
            }
        }
    }


    public void printAllUsers(Collection<Client> clients) {
        Object[] array = clients.toArray();
        if (array.length == 0) {
            printer("brak klientów w bazie danych");
        } else {
            for (Object client : array) {
                printer(client.toString());
            }
        }
    }

    public void printer(String text) {
        System.out.println(text);
    }

}
