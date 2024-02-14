package CarRental.io.file;

import CarRental.exception.DataExportException;
import CarRental.exception.DataImportException;
import CarRental.io.ConsolePrinter;
import CarRental.io.DataReader;
import CarRental.model.*;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CsvFileManager implements FileManager{
    private static final String CAR_FILE_NAME = "carRental.csv";
    private static final String CLIENT_FILE_NAME = "client_car_rental.csv";
    private ConsolePrinter printer;
    private DataReader dataReader;
    @Override
    public CarManager carImportData() {
        CarManager carManager = new CarManager(printer, dataReader);
        try(Scanner scannerReader = new Scanner(new File(CAR_FILE_NAME))) {
            while (scannerReader.hasNextLine()) {
                String s = scannerReader.nextLine();
                Car objectCar = createObjectCar(s);
                carManager.addCar(objectCar);
            }
        } catch (FileNotFoundException e) {
            throw new DataImportException("Błąd odczytu danych z pliku");
        }
        return carManager;
    }

    @Override
    public ClientManager clientImportData() {
        ClientManager clientManager = new ClientManager();
        try(Scanner scannerReader = new Scanner(new File(CLIENT_FILE_NAME))) {
            while (scannerReader.hasNextLine()) {
                String s = scannerReader.nextLine();
                Client objectClient = createObjectClient(s);
                clientManager.addClient(objectClient);
            }
        } catch (FileNotFoundException e) {
            throw new DataImportException("Błąd odczytu danych z pliku");
        }
        return clientManager;
    }

    private Car createObjectCar(String line) {
        String[] split = line.split(";");
        String type = split[0];
        if (type.equals("Auto")) {
            Car carFromString = createCarFromString(split);
            return carFromString;
        } else {
            return null;
        }
    }

    private Client createObjectClient(String line) {
        String[] split = line.split(";");
        String type = split[0];
        if (type.equals("Klient")) {
            Client clientFromString = createClientFromString(split);
            return clientFromString;
        } else {
            return null;
        }
    }

    private Client createClientFromString(String[] array) {
        String name = array[1];
        String lastName = array[2];
        String email = array[3];
        int phoneNumber = Integer.parseInt(array[4]);
        String pesel = array[5];
        return new Client(name, lastName, email, phoneNumber, pesel);
    }

    private Car createCarFromString(String[] array) {
        String brand = array[1];
        String model = array[2];
        int price = Integer.parseInt(array[3]);
        int idNumber = Integer.parseInt(array[4]);
        return new Car(brand, model, price, idNumber);
    }


    @Override
    public void exportData(CarManager garage) {
        List<Car> cars = garage.getCarCollection().stream().toList();
        try(var fileWriter = new FileWriter(CAR_FILE_NAME);
            var bufferedWriter = new BufferedWriter(fileWriter)) {
            for (Car car : cars) {
                bufferedWriter.write(car.toCsvConvert());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku");
        }
    }

    @Override
    public void exportData(ClientManager clientManager) {
        List<Client> clients = clientManager.getClientsList();
        try(var fileWriter = new FileWriter(CLIENT_FILE_NAME);
            var bufferedWriter = new BufferedWriter(fileWriter)) {
            for (Client client : clients) {
                bufferedWriter.write(client.toCsvConvert());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku");
        }
    }
}
