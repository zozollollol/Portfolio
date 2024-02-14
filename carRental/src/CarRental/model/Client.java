package CarRental.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Client extends Person implements Comparator<Client> {
    public static final transient String TYPE = "Klient";
    private String pesel;

    public Client(String name, String lastName, String email, int phoneNumber,
                  String pesel) {
        super(name, lastName, email, phoneNumber);
        this.pesel = pesel;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public int compare(Client o1, Client o2) {
        return o1.getLastName().compareToIgnoreCase(o2.getLastName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(pesel, client.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pesel);
    }

    @Override
    public String toString() {
        return super.toString() + " " + pesel + " ";
    }


    @Override
    public String toCsvConvert() {
        return TYPE + ";" + getName() + ";" + getLastName() + ";" + getEmail() + ";" + getPhoneNumber()+ ";" +  getPesel();
    }
}
