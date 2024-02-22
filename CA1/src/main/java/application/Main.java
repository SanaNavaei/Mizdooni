package application;

public class Main {
    public static void main(String[] args) {
        MizDooni mizdooni = new MizDooni();
        Controller controller = new Controller(mizdooni);
        var test = controller.addUser("{\"role\": \"manager\", \"username\": \"user1\", \"password\": \"1234\", \"email\": \"user1@gmail.com\", \"address\": {\"country\": \"Iran\", \"city\": \"Tehran\"}}");
        System.out.println(test);

        var restaurant = controller.addRestaurant("{\"name\": \"restaurant1\", \"managerUsername\": \"user1\", \"type\": \"Iranian\", \"startTime\": \"08:00\", \"endTime\": \"23:00\", \"description\": \"Open seven days a week.\", \"address\": {\"country\": \"Iran\", \"city\": \"Tehran\", \"street\": \"North Kargar\"}}");
        System.out.println(restaurant);

        var table = controller.addTable("{\"tableNumber\": 1, \"restaurantName\": \"restaurant1\", \"managerUsername\": \"user1\", \"seatsNumber\": 4}");
        System.out.println(table);
    }
}
