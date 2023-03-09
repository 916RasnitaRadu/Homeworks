// 1. Intr-o parcare exista masini, motociclete
//si biciclete. Sa se afiseze toate vehiculele
//de culoare rosie.

import Exceptions.NegativeWeightException;
import Model.Bicycle;
import Model.Car;
import Model.Motorbike;
import Repository.*;
import Controller.Controller;
import View.View;

public class Main {

    public static void test_app() throws NegativeWeightException {
        IRepository repo = new Repository();
        Controller controller = new Controller(repo);

        Car car1 = new Car("red",700,(float)1.9);
        Car car2 = new Car("blue",860,(float)1.6);
        Car car3 = new Car("red",650, (float) 1.2);

        Motorbike mt1 = new Motorbike("red", 300, "Suzuki");
        Motorbike mt2 = new Motorbike("black", 300, "harley davidson");
        Motorbike mt3 = new Motorbike("grey", 300, "harley davidson");

        Bicycle b1 = new Bicycle("red", 30, (float)1.3);
        Bicycle b2 = new Bicycle("red", 20, (float)2.1);
        Bicycle b3 = new Bicycle("blue", 45, (float)3.0);


        controller.add_ctrl(car1);
        controller.add_ctrl(car2);
        controller.add_ctrl(car3);
        controller.add_ctrl(mt1);
        controller.add_ctrl(mt2);
        controller.add_ctrl(mt3);
        controller.add_ctrl(b1);
        controller.add_ctrl(b2);
        controller.add_ctrl(b3);






        controller.remove_ctrl("black", 300);

    }

    public static void main(String[] args) throws NegativeWeightException {
        test_app();

        IRepository repo = new Repository();
        Controller controller = new Controller(repo);
        View ui = new View(controller);

        ui.execute();
    }
}