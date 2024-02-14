package CarRental.model;

import CarRental.io.ConsolePrinter;
import CarRental.io.DataReader;

import java.io.Serializable;
import java.util.*;

public class CarManager implements Serializable{
    private final transient ConsolePrinter consolePrinter;
    private final transient DataReader dataReader;
    private List<Car> cars = new ArrayList<>();
    private Map<String, Car> takenCars = new HashMap<>();
    private List<Car> availableCars = new ArrayList<>();

    public CarManager(ConsolePrinter consolePrinter, DataReader dataReader) {
        this.consolePrinter = consolePrinter;
        this.dataReader = dataReader;
    }
    public boolean checkIfExist(Car car) {
        long count = cars.stream().filter(c -> c.getIdNumber() == car.getIdNumber()).count();
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void addCar(Car car) {
        cars.add(car);
        availableCars.add(car);
    }
    public void putCarToMap(String pesel, Car car) {
        takenCars.put(pesel, car);
    }
    public Car searchById(int index) {
        Car car = null;
        for (Car availableCar : availableCars) {
            if (availableCar.getIdNumber() == index) {
                car = availableCar;
            }
        } return car;
    }

    public void removeCarFromAvailable(Car car) {
        availableCars.remove(car);
    }

    public Collection<Car> getCarCollection() {
        return cars;
    }

    public List<Car> getAvailableCars() {
        return availableCars;
    }

    public Map<String, Car> getTakenCars() {
        return takenCars;
    }
}
