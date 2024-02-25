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

        var addTable2 = controller.addTable("{\"tableNumber\": 2, \"restaurantName\": \"restaurant1\", \"managerUsername\": \"user1\", \"seatsNumber\": 4}");

        var reserveTable = controller.reserveTable("{\"username\": \"user2\", \"restaurantName\": \"restaurant1\", \"tableNumber\": 1, \"datetime\": \"2024-02-26 21:00\"}");
        var reserveTable2 = controller.reserveTable("{\"username\": \"user2\", \"restaurantName\": \"restaurant1\", \"tableNumber\": 2, \"datetime\": \"2024-02-26 18:00\"}");
        var reserveTable3 = controller.reserveTable("{\"username\": \"user2\", \"restaurantName\": \"restaurant1\", \"tableNumber\": 1, \"datetime\": \"2024-02-27 19:00\"}");
        //var cancelReservation = controller.cancelReservation("{\"username\": \"user2\", \"reservationNumber\": 1}");
        var showReservationHistory = controller.showReservationHistory("{\"username\": \"user2\"}");
        System.out.println(reserveTable);
        System.out.println(reserveTable2);
        //System.out.println(cancelReservation);
        System.out.println(showReservationHistory);

        var showAvailableTables = controller.showAvailableTables("{\"restaurantName\": \"restaurant1\"}");
        System.out.println(showAvailableTables);

        var addReview = controller.addReview("{\"username\": \"user2\", \"restaurantName\": \"restaurant1\", \"foodRate\": 4.5, \"serviceRate\": 3, \"ambianceRate\": 4.5, \"overallRate\": 4, \"comment\": \"Not bad!\"}");
        System.out.println(addReview);
    }
}
