package mizdooni.model.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import mizdooni.model.Address;

@Entity
@DiscriminatorValue("client")
public class Client extends User {
    public Client() {
        super("", "", "", new Address("", "", ""), Role.client);
    }

    public Client(String username, String password, String email, Address address) {
        super(username, password, email, address, Role.client);
    }
}
