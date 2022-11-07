package View;

import Controller.Controller;
import Exceptions.NegativeWeightException;
import Model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class View {
    private Controller ctrl;

    public View(Controller c) { this.ctrl = c;}

    private void print_menu() {
        System.out.println("1. Add a vehicle.");
        System.out.println("2. Remove a vehicle.");
        System.out.println("3. Find red vehicles.");
        System.out.println("4. EXIT");
    }

    private void print_add_menu()
    {
        System.out.println("1. Car");
        System.out.println("2. Motorbike");
        System.out.println("3. Bicycle");
    }
    private void add_menu() throws NegativeWeightException {
        int option;
        String color;
        int weight;
        Scanner input = new Scanner(System.in);

        print_add_menu();
        System.out.print("Please select what vehicle you want to add: ");
        option = input.nextInt();
        switch (option) {
            case 1:

                float cc;
                System.out.print("Please enter the color of the car: ");
                color = input.next();

                System.out.print("Please enter the cc of the car: ");
                cc = input.nextFloat();
                input.nextLine();

                System.out.print("Please enter the weight of the car: ");
                weight = input.nextInt();
                input.nextLine();

                if (weight < 0) throw new NegativeWeightException("Weight must be a positive number!\n");
                Car car = new Car(color, weight, cc);
                this.ctrl.add_ctrl(car);
                break;
            case 2:

                String brand;

                System.out.print("Please enter the color of the motorbike: ");
                color = input.next();

                System.out.print("Please enter the brand of the motorbike: ");
                brand = input.nextLine();

                System.out.print("Please enter the weight of the motorbike: ");
                weight = input.nextInt();
                input.nextLine();

                if (weight < 0) throw new NegativeWeightException("Weight must be a positive number!\n");
                Motorbike mt = new Motorbike(color, weight, brand);
                this.ctrl.add_ctrl(mt);
                break;
            case 3:

                float tw;

                System.out.print("Please enter the color of the bike: ");
                color = input.next();

                System.out.print("Please enter the tire width of the bike: ");
                tw = input.nextFloat();

                System.out.print("Please enter the weight of the bike: ");
                weight = input.nextInt();
                input.nextLine();

                if (weight < 0) throw new NegativeWeightException("Weight must be a positive number!");
                Bicycle b = new Bicycle(color,weight,tw);
                this.ctrl.add_ctrl(b);
                break;
            default:
                System.out.println("The number is not valid.");
                break;
        }
    }

    private void remove_menu() throws NegativeWeightException {
        int weight;
        String color;

        Scanner input = new Scanner(System.in);

        System.out.print("Please enter the color of the vehicle you want to remove: ");
        color = input.next();

        System.out.print("Please enter the weight of the vehicle you want to remove: ");
        weight = input.nextInt();
        if (weight < 0) throw new NegativeWeightException("Weight must be a positive number!");

        this.ctrl.remove_ctrl(color,weight);
    }

    private void vehicle_red() {
        ArrayList<Vehicle> result = this.ctrl.get_all_that_are_red_ctrl();
        if (result.size() == 0) {
            System.out.println("There are not any red vehicles..");
        }
        else {
            System.out.println("All the vehicles that are red are: ");
            for (Vehicle v : result)
            {
                System.out.println(v.toString());
            }
        }

    }

    public void execute()
    {
        int option;
        Scanner input = new Scanner(System.in);
        boolean going = true;
        while (going)
        {
            try {
                print_menu();
                System.out.print("Please select your next option: ");
                option = input.nextInt();
                switch (option) {
                    case 1:
                        add_menu();
                        break;
                    case 2:
                        remove_menu();
                        break;
                    case 3:
                        vehicle_red();
                        break;
                    case 4:
                        going = false;
                        break;
                    default:
                        System.out.println("Please enter a valid option");
                        break;
                }
            }
            catch (NegativeWeightException e) { System.out.println(e.get_message()); };
        }
    }
}
