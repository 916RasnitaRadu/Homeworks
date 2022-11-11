package controller;

import exceptions.NegativeWeightException;
import model.entity;
import org.w3c.dom.Entity;
import repository.IRepository;

import java.util.List;

public class Controller {

    IRepository repository;

    public Controller(IRepository repo) { this.repository = repo;}

    public void add(entity entity)
    {
        try {
            repository.add1(entity);
        }
        catch (NegativeWeightException e) {
            System.out.println(e.getMessage());
        }
    }

    public entity[] get_all_contr() { return repository.get_all();}

    public void remove_contr(entity ent) { repository.remove(ent);}

    public List<entity> get_all_objects_with_weight_greater_contr() { return repository.get_all_with_weight_greater_than(); }
}
