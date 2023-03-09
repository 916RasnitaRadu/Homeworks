//  Please write a Java program that computes the average of all integer numbers given as command-line parameters.

import java.util.ArrayList;

public class Main {

    static double compute_average(ArrayList<Integer> lst) {
        // A function that computes the average of an array of numbers
        int s = 0;
        for (Integer x : lst)
        {
            s = s + x;
        }
        return (double) s / lst.size();
    }

    public static void main(String[] args) {

        if (args.length > 0) // verify if args is empty
        {
            ArrayList<Integer> numbers = new ArrayList<>();
            for (String st : args)
            {
                int number = Integer.parseInt(st); // parse the integers from the argument string

                numbers.add(number); // add the to the list
            }
            double result = compute_average(numbers);
            System.out.println("The average of the given numbers is: " + result);
        }
        else System.out.println("Please input at least one argument");
    }
}