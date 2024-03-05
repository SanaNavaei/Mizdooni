package database;

import model.Restaurant;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class Database {
    public List<User> users;
    public List<Restaurant> restaurants;

    private static final Database instance = new Database();

    private Database() {
        users = new ArrayList<>();
        restaurants = new ArrayList<>();
        new DataLoader(this).read();
    }

    public static Database getInstance() {
        return instance;
    }
}
