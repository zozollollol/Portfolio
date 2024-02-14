package CarRental.io;

import CarRental.model.Car;
import CarRental.model.Client;

import java.lang.reflect.Array;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DataReader {
     private Scanner scanner = new Scanner(System.in);
     private ConsolePrinter printer;

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public String stringInput() {
         return scanner.nextLine();
     }

     public int intInput() {
        boolean loop = true;
         int number = 0;
        do {
            try {
                number = scanner.nextInt();
                loop = false;
            } catch (InputMismatchException e) {
                printer.printer("proszę wprowadzić poprawną wartość (liczbę)");
            } finally {
                scanner.nextLine();
            }
        } while (loop);
         return number;
     }
     public void closeScanner() {
         scanner.close();
     }

     public Car readAndCreateCar() {
         printer.printer("marka auta: ");
         String brand = stringInput();
         printer.printer("model auta: ");
         String model = stringInput();
         printer.printer("cena za dzień: ");
         int price = intInput();
         printer.printer("Podaj ID?");
         int idNumber = intInput();
         return new Car(brand, model, price, idNumber);
     }

    public Client readAndCreateClient() {
        printer.printer("imię: ");
        String name = stringInput();
        printer.printer("nazwisko: ");
        String lastName = stringInput();
        printer.printer("email: ");
        String email = stringInput();
        printer.printer("nr. telefonu: ");
        int phoneNumber = intInput();
        printer.printer("pesel: ");
        String pesel = stringInput();
        return new Client(name, lastName, email, phoneNumber, pesel);
    }

    public boolean checkIfOutOfBand(Array[] array, int number) {
        if (array.length>= number) {
            return false;
        } else {
            return true;
        }
    }

}
