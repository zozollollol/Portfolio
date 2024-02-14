package CarRental.model;

import java.io.Serializable;
import java.util.Objects;

public class Car implements CsvConvertible, Serializable {
    public static final transient String TYPE = "Auto";
    private String brand;
    private String model;
    private int pricePerDay;
    private int  idNumber;

    public Car(String brand, String model, int pricePerDay, int idNumber) {
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.idNumber = idNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setAvailable(int available) {
        idNumber = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;
        return pricePerDay == car.pricePerDay && idNumber == car.idNumber && Objects.equals(brand, car.brand) && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, pricePerDay, idNumber);
    }

    @Override
    public String toString() {
        return "Marka: " + brand + " - " + "Model: " + model + " - " + "Cena za dzień " + pricePerDay
                + " - " + "Numer ID: " + idNumber;
    }

    @Override
    public String toCsvConvert() {
        return TYPE + ";" + getBrand() + ";" + getModel() + ";" + getIdNumber() + ";" + getPricePerDay();
    }
}
