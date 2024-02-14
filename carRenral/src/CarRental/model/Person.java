package CarRental.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Person implements CsvConvertible, Serializable {
    private String name;
    private String lastName;
    private String email;
    private int phoneNumber;

    public Person(String name, String lastName, String email, int phoneNumber) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public abstract String toCsvConvert();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return phoneNumber == person.phoneNumber && Objects.equals(name, person.name) && Objects.equals(lastName, person.lastName) && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, email, phoneNumber);
    }

    @Override
    public String toString() {
        return name + " " + lastName + " " + email + " " + phoneNumber;
    }
}
