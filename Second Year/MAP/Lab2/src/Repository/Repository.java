package Repository;

import Model.Vehicle;
import Exceptions.NegativeWeightException;

import java.util.*;

public class Repository implements IRepository{
    private Vehicle[] vehicles = new Vehicle[100];
    private int size;

    public Repository()
    {
        this.size = 0;
    }

    @Override
    public void add(Vehicle vehicle) throws NegativeWeightException {
        this.vehicles[size] = vehicle;
        size++;
    }

    @Override
    public Vehicle[] get_all() {
        return this.vehicles;
    }

    @Override
    public void remove(String c, int w) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(c, vehicles[i].getColor()) && w == vehicles[i].getWeight()) {
                for (int j = i; j < size - 1; j++)
                    vehicles[j] = vehicles[j+1];
            }
        }
    }

    @Override
    public ArrayList<Vehicle> get_all_that_are_red() {
        ArrayList<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles)
        {
            if (v != null)
            {
                if (v.getColor().toLowerCase().equals("red")) result.add(v);
            }
        }
        return result;
    }

    @Override
    public int getSize() {
        return this.size;
    }
}
