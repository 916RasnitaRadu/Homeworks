package Model.ADTs;

import Exceptions.InterpreterException;

import java.util.function.Consumer;
import java.util.List;

public interface IList<T> extends Iterable<T>{
    void add(T v);

    T pop() throws InterpreterException;

    boolean isEmpty();

    @Override
    void forEach(Consumer<? super T> action);

    List<T> getList();

    void setList(List<T> list);

    int getSize();

    T get(int i);
}
