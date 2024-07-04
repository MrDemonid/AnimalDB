package view.controls;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Класс для реализации диалогового окна для заполнения
 * или изменения данных о животном
 */
public class InputDialog extends JPanel {

    public InputDialog(ArrayList<String> commands)
    {
        setLayout(new MigLayout());
        makeDialog(commands);
    }

    private void makeDialog(ArrayList<String> commands)
    {
        add(new JLabel("Имя:"), "gap, sg 1");
        add(new JTextField(10), "wrap");
        add(new JLabel("Дата рождения:"), "gap, sg 1");
        add(new JTextField(10), "wrap");

        JList<String> list = new JList<>(commands.toArray(new String[0]));
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // TODO: добавить заполнение и выделение полей, если это предусмотрено !!!!

        JScrollPane listbox = new JScrollPane(list);
        add(listbox, "span, grow, wrap");
    }
}
