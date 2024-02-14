package CarRental.io.file;

import CarRental.model.ClientManager;
import CarRental.model.CarManager;

public interface FileManager {
    CarManager carImportData();
    ClientManager clientImportData();

    void exportData(CarManager garage);
    void exportData(ClientManager clientManager);
}
