package CarRental.app;

import CarRental.exception.DataExportException;
import CarRental.exception.OutOfBand;
import CarRental.io.ConsolePrinter;
import CarRental.io.DataReader;
import CarRental.io.file.FileManager;
import CarRental.io.file.FileType;
import CarRental.io.file.SerializableFileManager;
import CarRental.model.Car;
import CarRental.model.Client;
import CarRental.model.ClientManager;
import CarRental.model.CarManager;

public class ClientControl {
    private ConsolePrinter printer;
    private DataReader dataReader;
    private CarManager garage;
    private ClientManager client;
    private FileManager fileManager;

    public ClientControl(ConsolePrinter printer, DataReader dataReader, CarManager garage,
                         ClientManager client, FileManager fileManager) {
        this.printer = printer;
        this.dataReader = dataReader;
        this.garage = garage;
        this.client = client;
        this.fileManager = fileManager;
    }

    public void controlLoop() {
        ClientOption option;
        do {
            printOption();
            option = getOption();
            switch (option) {
                case EXIT -> exit();
                case BORROW -> borrowCar();
                case REGISTER -> register();
                case PRINT_ALL_CARS -> printAllCars();
                case PRINT_AVAILABLE_CARS -> printAllAvailableCars();
            }
        } while (option != ClientOption.EXIT);
    }

    private void printUsers() {
        printer.printAllUsers(client.getSortedList());
    }

    private void exit() {
        try {
            fileManager.exportData(garage);
            fileManager.exportData(client);
            printer.printer("Export danych do pliku zakonczony powodzeniem");
        } catch (DataExportException e) {
            printer.printer(e.getMessage());
        }

    }

    public void borrowCar() {
        printer.printAllCars(garage.getAvailableCars());
        if (garage.getAvailableCars().toArray().length != 0) {
            printer.printer("Podaj numer id auta, które chciałbyś wypożyczyć");
            int providedID = dataReader.intInput();
            rentCar(providedID);
        }
    }

    public void printAllAvailableCars() {
        printer.printAllCars(garage.getAvailableCars());
    }
    public void printAllCars() {
        printer.printAllCars(garage.getCarCollection());
    }
    public void register() {
        Client client1 = dataReader.readAndCreateClient();
        client.addClient(client1);
    }

    public ClientOption getOption() {
        ClientOption option = null;
        while (option == null) {
            try {
                int number = dataReader.intInput();
                option = ClientOption.getOptionFromInt(number);
            } catch (OutOfBand e) {
                printer.printer(e.getMessage());
            }

        }
        return option;
    }
    public void printOption() {
        for (ClientOption option: ClientOption.values()) {
            printer.printer(option.toString());
        }
    }
    private void rentCar(int providedID) {
        int value = 0;
        printer.printer("proszę podać pesel");
        String pesel = dataReader.stringInput();
        for (Client client1: client.getClientsList()) {
            if (pesel.equals(client1.getPesel())) {
                Car car = garage.searchById(providedID);
                System.out.println(car);
                garage.putCarToMap(pesel, car);
                garage.removeCarFromAvailable(car);
                printer.printer("gratulacje! Auto zostało wypożyczone");
                value = 1;
            }
        } if (value == 0) {
            printer.printer("nie jesteś zarejestrowany, proszę się zarejestrować");
        }
    }

    private enum ClientOption {
        EXIT("Wyjście do strony głownej"),
        BORROW("Wypożycz auto"),
        REGISTER("Zarejestruj się"),
        PRINT_ALL_CARS("Wyświetl wszytskie auta"),
        PRINT_AVAILABLE_CARS("Wyświetl wszytskie wolne auta");
        private String description;

        ClientOption(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return ordinal() + " - " + description;
        }
        static ClientOption getOptionFromInt(int number) {
            try {
                ClientOption[] values = ClientOption.values();
                return values[number];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new OutOfBand("prose wybrać liczbę z przedziału: 0 - " + (values().length-1));
            }
        }
    }
}
