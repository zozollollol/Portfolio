package CarRental.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClientManager implements Serializable {
    private List<Car> carsHistory = new ArrayList<>();
    private List<Client> clientsList = new ArrayList<>();

    public List<Client> getSortedList() {
        return new ArrayList<>(clientsList);
    }

    public List<Client> getClientsList() {
        return clientsList;
    }


    public void addClient(Client client) {
        clientsList.add(client);
    }
}
