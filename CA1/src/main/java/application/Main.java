package application;

public class Main {
    public static void main(String[] args) {
        MizDooni mizdooni = new MizDooni();
        Controller controller = new Controller(mizdooni);
        CommandInterface cmdInterface = new CommandInterface(controller, System.in, System.out);
        cmdInterface.run();
    }
}
