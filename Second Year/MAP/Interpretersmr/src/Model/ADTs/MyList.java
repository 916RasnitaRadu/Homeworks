package Model.ADTs;


import Exceptions.InterpreterException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class MyList<T> implements IList<T> {

    private List<T> list;

    public MyList(List<T> list) {
        this.list = list;
    }

    public MyList()
    {
        list = new LinkedList<>();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }


    @Override
    public void add(T element) {
        list.add(element);
    }

    @Override
    public T pop() throws InterpreterException {
        if (this.list.size() == 0) {throw new InterpreterException("LIST ERROR: List is empty");}
        T result = list.get(0);
        list.remove(0);
        return result;
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        list.forEach(action);
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public T get(int i) {
        return this.list.get(i);
    }
}
