import Controller.Controller;
import Model.ADTs.*;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Values.Value;
import Repository.IRepository;
import Repository.Repository;
import View.View;

public class Main {

    public static void main(String[] args) {
        IRepository repository = new Repository();
        Controller controller = new Controller(repository);
        View ui = new View(controller);

        ui.run();
    }
}

// TODO: deepcopy() la expresii si statements