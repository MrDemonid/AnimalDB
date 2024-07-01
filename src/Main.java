import animal.Dog;
import animal.Hamster;
import animal.base.Animal;
import model.Model;

import java.util.Calendar;
import java.util.Date;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Model model = new Model();
        model.showCommands();
        model.showAnimals();
        Date from = new Date(2021-1900, Calendar.JANUARY, 12);
        Date to = new Date();
        model.showBithdays(from, to);;
        model.showType("Cat");
        model.showType("Horse");
        model.showId(2);
        model.showId(4);

        Animal d = new Hamster(17, "Baulty", new Date(2019-1900, Calendar.JANUARY, 18));
        d.addCommand("Sit");
        d.addCommand("Jump");
        d.addCommand("Kick");
        d.addCommand("Run");
        model.updateAnimal(d);
        model.showAnimals();

    }
}
