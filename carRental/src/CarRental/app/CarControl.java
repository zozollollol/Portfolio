package CarRental.app;

import CarRental.exception.DataImportException;
import CarRental.exception.OutOfBand;
import CarRental.io.ConsolePrinter;
import CarRental.io.DataReader;
import CarRental.io.file.FileManager;
import CarRental.io.file.FileManagerBuilder;
import CarRental.io.file.SerializableFileManager;
import CarRental.model.ClientManager;
import CarRental.model.CarManager;

import java.lang.reflect.Array;
import java.util.Optional;


public class CarControl {
    ConsolePrinter printer = new ConsolePrinter();
    DataReader dataReader =  new DataReader(printer);
    private FileManager fileManager;
    private CarManager garage;
    private ClientManager clientManager;

    public CarControl() {
        fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            clientManager = fileManager.clientImportData();
            garage = fileManager.carImportData();
            printer.printer("Zainicjowany dane z pliku");
        } catch (DataImportException e) {
            printer.printer(e.getMessage());
            printer.printer("Zainicjowano nową bibliotekę");
            garage = new CarManager(printer, dataReader);
            clientManager = new ClientManager();
        }
    }

    public void mainPage() {
        HomePageOption homePageOption;
        do {
            printOption();
            homePageOption = getOption();
            switch (homePageOption) {
                case EXIT -> exit();
                case RUN_CLIENT -> runClient();
                case RUN_MANAGER -> runManager();
            }
        } while (homePageOption != HomePageOption.EXIT);
    }

    private void exit() {
        printer.printer("Koniec programu");
        dataReader.closeScanner();
    }

    public HomePageOption getOption() {
        boolean notOkay = true;
        HomePageOption option = null;
        while (notOkay) {
            try {
                option = HomePageOption.getOptionFromInt(dataReader.intInput());
                notOkay = false;
            } catch (OutOfBand e) {
                printer.printer(e.getMessage());
            }
        }
        return option;
    }

    private void runClient() {
        ClientControl clientControl = new ClientControl(printer, dataReader, garage,
                clientManager, fileManager);
        clientControl.controlLoop();
    }

    private void runManager() {
        OwnerControl ownerControl = new OwnerControl(printer, dataReader, garage,
                clientManager, fileManager);
        ownerControl.controlLoop();
    }
    private void printOption() {
        HomePageOption[] values = HomePageOption.values();
        for (HomePageOption value : values) {
            printer.printer(value.toString());
        }
    }

    private enum HomePageOption {
        EXIT("Wyjście"),
        RUN_CLIENT("Strona Klienta"),
        RUN_MANAGER("Strona administratora");

        private String description;

        HomePageOption(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return ordinal() + " " + description;
        }

        static HomePageOption getOptionFromInt(int number) {
            try {
                HomePageOption[] values = HomePageOption.values();
                return values[number];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new OutOfBand("prose wybrać liczbę z przedziału: 0 - " + (values().length-1));
            }
        }


    }

}
