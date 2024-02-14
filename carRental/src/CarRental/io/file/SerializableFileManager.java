package CarRental.io.file;

import CarRental.exception.DataExportException;
import CarRental.exception.DataImportException;
import CarRental.model.ClientManager;
import CarRental.model.CarManager;

import java.io.*;

public class SerializableFileManager implements FileManager{
    private static final String CAR_FILE_NAME = "carRental.o";
    private static final String CLIENT_FILE_NAME = "client_car_rental.o";


    @Override
    public CarManager carImportData() {
        try(var fileInputStream = new FileInputStream(CAR_FILE_NAME);
            var objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (CarManager) objectInputStream.readObject();
        } catch (IOException e) {
            throw new DataImportException("Błąd odczytu pliku - car manager IO");
        } catch (ClassNotFoundException e) {
            throw new DataImportException("Błąd odczytu pliku - car manager clss");
        }
    }

    @Override
    public ClientManager clientImportData() {
        try(var fileInputStream = new FileInputStream(CLIENT_FILE_NAME);
            var objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (ClientManager) objectInputStream.readObject();
        } catch (IOException e) {
            throw new DataImportException("Błąd odczytu pliku - client manager IO");
        } catch (ClassNotFoundException e) {
            throw new DataImportException("Błąd odczytu pliku - client manager Clss");
        }
    }

    @Override
    public void exportData(CarManager garage) {
        try(var fileOutputStream = new FileOutputStream(CAR_FILE_NAME);
            var objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(garage);
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu pliku - car manager");
        }
    }

    @Override
    public void exportData(ClientManager clientManager) {
        try(var fileOutputStream = new FileOutputStream(CLIENT_FILE_NAME);
            var objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(clientManager);
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu pliku - client manager");
        }
    }
}
