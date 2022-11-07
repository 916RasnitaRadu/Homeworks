package repository;

import model.entity;
import exceptions.NegativeWeightException;
import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private entity[] entities;
    private int size;

    public Repository()
    {
        entities = new entity[100];
        size = 0;
    }


    @Override
    public void add1(entity entity) throws NegativeWeightException {
        if (entity.getWeight() <= 0) throw new NegativeWeightException("The weight can not be negative");

        entities[size] = entity;
        size++;
    }

    @Override
    public void remove(entity entity) {
        for (int i = 0; i < size; i++) {
          if (entity == entities[i]) {
              for (int j = i; j < size - 1; j++)
                  entities[j] = entities[j+1];
          }
        }

    }

    @Override
    public entity[] get_all() {
        return entities;
    }

    @Override
    public List<entity> get_all_with_weight_greater_than() {
        ArrayList<entity> l = new ArrayList<>();
        for (entity i : entities)
        {
            if (i != null) {
                if (i.getWeight() > 200) {
                    l.add(i);
                }
            }
        }
        return l;
    }
}
