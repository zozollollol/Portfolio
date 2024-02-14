package CarRental.app;

import CarRental.exception.DataExportException;
import CarRental.exception.OutOfBand;
import CarRental.io.ConsolePrinter;
import CarRental.io.DataReader;
import CarRental.io.file.FileManager;
import CarRental.model.Car;
import CarRental.model.Client;
import CarRental.model.ClientManager;
import CarRental.model.CarManager;

import java.util.Map;
import java.util.Set;


public class OwnerControl {

    private ConsolePrinter printer;
    private DataReader dataReader;
    private CarManager garage;
    private ClientManager client;
    private FileManager fileManager;

    public OwnerControl(ConsolePrinter printer, DataReader dataReader, CarManager garage,
                        ClientManager client, FileManager fileManager) {
        this.printer = printer;
        this.dataReader = dataReader;
        this.garage = garage;
        this.client = client;
        this.fileManager = fileManager;
    }

    public void controlLoop() {
            Option option;
            do {
                printOption();
                option = getOption();
                switch (option) {
                    case EXIT -> exit();
                    case ADD_CAR -> addCar();
                    case PRINT_ALL_CARS -> printAllCars();
                    case PRINT_BORROWED_CARS -> printBorrowedCars();
                    case ADD_USER -> addUser();
                    case PRINT_ACTIVE_USERS ->  printActiveUsers();
                    case DELETE_CAR -> deleteCar();
                    case DELETE_USER ->  deleteUser();
                }
            } while (option != Option.EXIT);
    }


    private void printActiveUsers() {
            printer.printAllUsers(client.getClientsList());
    }

    private void deleteUser() {
        printer.printAllUsers(client.getClientsList());
        printer.printer("podaj numer pesel użytkownika, którego chcesz usunąć");
        String pesel = dataReader.stringInput();
        long count = client.getClientsList()
                .stream()
                .filter(c -> c.getPesel().equals(pesel))
                .count();
        if (count !=0) {
        client.getClientsList().removeIf( c -> c.getPesel().equals(pesel));
        printer.printer("użytkownik został usunięty z bazy danych");
        } else {
            printer.printer("Brak klientów o podanym numerze pesel");
        }
    }

    private void printBorrowedCars() {
            printer.printCarAndOwner(garage.getTakenCars());
    }

    private void deleteCar() {
        printer.printAllCars(garage.getCarCollection());
        printer.printer("podaj numer id auta, które chcesz usunąć");
        int idNumber = dataReader.intInput();
        long count = garage.getCarCollection()
                .stream()
                .filter(c -> c.getIdNumber() == idNumber)
                .count();
        if (count !=0) {
            garage.getAvailableCars().removeIf(c -> c.getIdNumber() == idNumber);
            garage.getCarCollection().removeIf(c -> c.getIdNumber() == idNumber);
            String s = peselFromIdNumber(idNumber);
            garage.getTakenCars().remove(s);
            printer.printer("auto zostało usunięte z bazy danych");
            } else {
                printer.printer("Brak auta z takim numerem ID");
            }
    }

    private String peselFromIdNumber(int idNumber) {
        Object[] array =garage.getTakenCars().keySet().toArray();
        for (int i = 0; i < array.length; i++) {
            if (garage.getTakenCars().get(array[i]).getIdNumber() == idNumber) {
                return array[i].toString();
            }
        }
        return null;
    }

    private void addUser() {
            Client newClient = dataReader.readAndCreateClient();
            client.addClient(newClient);
            printer.printer("Rejestracja przebiegła pomyślnie");
        }

        private void printAllCars() {
            printer.printAllCars(garage.getCarCollection());
        }

        private void addCar() {
            Car car = dataReader.readAndCreateCar();
            boolean b = garage.checkIfExist(car);
            if (b) {
                garage.addCar(car);
            } else {
                printer.printer("auto o podanym numerze id istnieje");
            }

        }

        public Option getOption() {
            Option option = null;
            while (option == null) {
                try {
                    int number = dataReader.intInput();
                    option = Option.getOptionFromInt(number);
                } catch (OutOfBand e) {
                    printer.printer(e.getMessage());
                }
            }
            return option;
        }

        public void printOption() {
            for (Option option: Option.values()) {
                printer.printer(option.toString());
            }
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
        private enum Option {
            EXIT("Wyjście do strony głównej"),
            ADD_CAR("Dodaj auto"),
            PRINT_ALL_CARS("Wyświetl wszytskie auta"),
            PRINT_BORROWED_CARS("Wyświetl wszytskie wypożyczone auta"),
            ADD_USER("dodaj klienta"),
            PRINT_ACTIVE_USERS("Wyświetl aktywnych klientów"),
            DELETE_CAR("usuń auto"),
            DELETE_USER("usuń użytkownika");
            private String description;

            Option(String description) {
                this.description = description;
            }

            @Override
            public String toString() {
                return ordinal() + " - " + description;
            }
            static Option getOptionFromInt(int number) {
                try {
                    Option[] values = Option.values();
                    return values[number];
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new OutOfBand("prose wybrać liczbę z przedziału: 0 - " + (values().length-1));
                }

            }
    }
}
