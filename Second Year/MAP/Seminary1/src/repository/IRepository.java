package repository;

import exceptions.NegativeWeightException;
import model.entity;

import java.util.List;

public interface IRepository {

    void add1(entity entity) throws NegativeWeightException;

    void remove(entity entity);

    entity[] get_all();

    List<entity> get_all_with_weight_greater_than();
}
