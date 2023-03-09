import controller.Controller;
import repository.IRepository;
import repository.Repository;
import view.View;

public class Main {
    public static void main(String[] args) {
        IRepository repo = new Repository();
        Controller ctrl = new Controller(repo);
        View ui = new View(ctrl);
        ui.execute();

    }
}