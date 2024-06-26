package mizdooni.model;

import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String country;
    @Column(length = 50)
    private String city;
    @Column(length = 100)
    private String street;

    public Address() {

    }

    public Address(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }
}
