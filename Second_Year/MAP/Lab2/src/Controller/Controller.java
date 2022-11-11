package Controller;

import Exceptions.NegativeWeightException;
import Model.Vehicle;
import Repository.*;

import java.util.*;
public class Controller {
    private IRepository repo;

    public Controller(IRepository repo) { this.repo = repo;}

    public void add_ctrl(Vehicle vehicle) throws NegativeWeightException {
        this.repo.add(vehicle);
    }

    public void remove_ctrl(String color, int w)
    {
        this.repo.remove(color,w);
    }

    public int get_size_ctrl()
    {
        return repo.getSize();
    }

    public ArrayList<Vehicle> get_all_that_are_red_ctrl()
    {
        return (ArrayList<Vehicle>) repo.get_all_that_are_red();
    }
}
