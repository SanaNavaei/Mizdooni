package application;

public class Main {
    public static void main(String[] args) {
        MizDooni mizdooni = new MizDooni();
        Controller controller = new Controller(mizdooni);
        var test = controller.addUser("{\"role\": \"manager\", \"username\": \"user1\", \"password\": \"1234\", \"email\": \"user1@gmail.com\", \"address\": {\"country\": \"Iran\", \"city\": \"Tehran\"}}");
        System.out.println(test);

        var restaurant = controller.addRestaurant("{\"name\": \"restaurant1\", \"managerUsername\": \"user1\", \"type\": \"Iranian\", \"startTime\": \"08:00\", \"endTime\": \"23:00\", \"description\": \"Open seven days a week.\", \"address\": {\"country\": \"Iran\", \"city\": \"Tehran\", \"street\": \"North Kargar\"}}");
        System.out.println(restaurant);

        var restaurant2 = controller.addRestaurant("{\"name\": \"restaurant2\", \"managerUsername\": \"user1\", \"type\": \"Iranian\", \"startTime\": \"08:00\", \"endTime\": \"23:00\", \"description\": \"Open seven days a week.\", \"address\": {\"country\": \"Iran\", \"city\": \"Tehran\", \"street\": \"North Kargar\"}}");
        System.out.println(restaurant2);

        var showRestaurants = controller.searchRestaurantsByName("{\"name\": \"restaurant1\"}");
        System.out.println(showRestaurants);

        var showRestaurants2 = controller.searchRestaurantsByType("{\"type\": \"Iranian\"}");
        System.out.println(showRestaurants2);
    }
}
