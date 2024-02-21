package application;

public class Main {
    public static void main(String[] args) {
        MizDooni mizdooni = new MizDooni();
        Controller controller = new Controller(mizdooni);
        var test = controller.addUser("{\"role\": \"client\", \"username\": \"user1\", \"password\": \"1234\", \"email\": \"user1@gmail.com\", \"address\": {\"country\": \"Iran\", \"city\": \"Tehran\"}}");
        System.out.println(test);
    }
}
