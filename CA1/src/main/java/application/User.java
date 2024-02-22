package application;

public class User {
    public enum Role {
        client,
        manager,
    }

    private String username;
    private String password;
    private String email;
    private Address address;
    private Role role;

    public User(String username, String password, String email, Address address, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() { return role; }
}
