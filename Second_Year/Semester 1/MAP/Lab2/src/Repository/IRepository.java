package Repository;

import Exceptions.NegativeWeightException;
import Model.Vehicle;

import java.util.List;

public interface IRepository {
    void add(Vehicle vehicle) throws NegativeWeightException;

    void remove(String c, int w);

    Vehicle[] get_all();

    List<Vehicle> get_all_that_are_red();

    int getSize();
}
