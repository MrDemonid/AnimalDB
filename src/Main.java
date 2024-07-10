import control.Control;
import model.Model;
import view.SwingView;

public class Main {
    public static void main(String[] args) {

        Control control = new Control(new Model(), new SwingView());

        control.run();
    }
}
