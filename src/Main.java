import animal.base.Animal;
import model.Model;
import view.GraphView;
import view.View;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Model model = new Model();
        ArrayList<String> cmd = model.getCommandsList();
        ArrayList<String> typ = model.getTypesList();

        View view = new GraphView(cmd, typ);



        ArrayList<Animal> res = model.getAllAnimals();
        view.showTable(res);

    }
}
