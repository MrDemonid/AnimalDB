import control.Control;
import model.Model;
import view.RinnableView;
import view.SwingView;
import view.View;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {

        // запускаем представление в отдельном потоке
        RinnableView view = new RinnableView();
        try {
            SwingUtilities.invokeAndWait(view);
        } catch (InterruptedException | InvocationTargetException e) {
            System.out.println("Fatal error: " + Arrays.toString(e.getStackTrace()));
            exit(1);
        }
        // и передаём управление контроллеру
        Control control = new Control(new Model(), view.getView());
        control.run();
    }
}
