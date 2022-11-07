package view;

import model.entity;
import model.Apple;
import model.Cake;
import model.Book;
import controller.Controller;
import org.w3c.dom.Entity;

import java.util.*;

public class View {
    Controller contr;

    public View(Controller c) { this.contr = c;}

    public void execute() {
        entity apple = new Apple("red");
        apple.setWeight(300);
        contr.add(apple);

        entity book = new Book(255,"Harry pottah");
        book.setWeight(500);
        contr.add(book);

        entity apple2 = new Apple("blue");
        apple2.setWeight(100);
        contr.add(apple2);

        entity cake = new Cake("cioco");
        cake.setWeight(500);
        contr.add(cake);

        List<entity> l = contr.get_all_objects_with_weight_greater_contr();

        for (entity en : l)
        {
            System.out.println(en.toString());
        }
    }

}
