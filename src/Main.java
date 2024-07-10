import control.Control;
import model.Model;
import view.SwingView;
import view.View;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

import static java.lang.System.exit;

public class Main {

    static View view;

    public static void main(String[] args) {

        try {
            SwingUtilities.invokeAndWait(
                    () -> Main.view = new SwingView()
            );
        } catch (InterruptedException | InvocationTargetException e) {
            exit(1);
        }
        Control control = new Control(new Model(), Main.view);
        control.run();
    }
}
