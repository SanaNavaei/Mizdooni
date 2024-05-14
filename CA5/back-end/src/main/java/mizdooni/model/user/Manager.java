package mizdooni.model.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import mizdooni.model.Address;

@Entity
@DiscriminatorValue("manager")
public class Manager extends User {
    public Manager() {
        super("", "", "", new Address("", "", ""), Role.manager);
    }

    public Manager(String username, String password, String email, Address address) {
        super(username, password, email, address, Role.manager);
    }
}
