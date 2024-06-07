package mizdooni.model.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import mizdooni.model.Address;

@Entity
@DiscriminatorValue("client")
public class Client extends User {
    public Client() {
        super();
    }

    public Client(int id, String username, String password, String email, Address address) {
        super(id, username, password, email, address, Role.client);
    }
}
