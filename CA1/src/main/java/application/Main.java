package application;

public class Main {
    public static void main(String[] args) {
        MizDooni mizdooni = new MizDooni();
        Controller controller = new Controller(mizdooni);

        var user1 = controller.addUser("{\"role\": \"manager\", \"username\": \"user1\", \"password\": \"1234\", \"email\": \"user1@gmail.com\", \"address\": {\"country\": \"Iran\", \"city\": \"Tehran\"}}");
        var user2 = controller.addUser("{\"role\": \"client\", \"username\": \"user2\", \"password\": \"1234\", \"email\": \"user2@gmail.com\", \"address\": {\"country\": \"Iran\", \"city\": \"Tehran\"}}");
        System.out.println(user1);
        System.out.println(user2);

        var restaurant = controller.addRestaurant("{\"name\": \"restaurant1\", \"managerUsername\": \"user1\", \"type\": \"Iranian\", \"startTime\": \"08:00\", \"endTime\": \"23:00\", \"description\": \"Open seven days a week.\", \"address\": {\"country\": \"Iran\", \"city\": \"Tehran\", \"street\": \"North Kargar\"}}");
        var restaurant2 = controller.addRestaurant("{\"name\": \"restaurant2\", \"managerUsername\": \"user1\", \"type\": \"Iranian\", \"startTime\": \"08:00\", \"endTime\": \"23:00\", \"description\": \"Open seven days a week.\", \"address\": {\"country\": \"Iran\", \"city\": \"Tehran\", \"street\": \"North Kargar\"}}");
        System.out.println(restaurant);
        System.out.println(restaurant2);

        var showRestaurants = controller.searchRestaurantsByName("{\"name\": \"restaurant1\"}");
        var showRestaurants2 = controller.searchRestaurantsByType("{\"type\": \"Iranian\"}");
        System.out.println(showRestaurants);
        System.out.println(showRestaurants2);

        var addTable1 = controller.addTable("{\"tableNumber\": 1, \"restaurantName\": \"restaurant1\", \"managerUsername\": \"user1\", \"seatsNumber\": 4}");
        System.out.println(addTable1);

        var reserveTable = controller.reserveTable("{\"username\": \"user2\", \"restaurantName\": \"restaurant1\", \"tableNumber\": 1, \"datetime\": \"2024-02-26 21:00\"}");
        System.out.println(reserveTable);
    }
}
