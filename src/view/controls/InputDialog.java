package view.controls;

import animal.base.Animal;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Класс для реализации диалогового окна для заполнения
 * или изменения данных о животном
 */
public class InputDialog extends JPanel {
    private JTextField fieldName;
    private JTextField fieldDate;
    private JList<String> listTypes;
    private JList<String> listCommands;


    public InputDialog(ArrayList<String> commands, ArrayList<String> types)
    {
        setLayout(new MigLayout());
        makeDialog(commands, types);
    }

    public InputDialog(Animal animal, ArrayList<String> commands, ArrayList<String> types)
    {
        this(commands, types);
        fillDialog(animal);
    }

    private void fillDialog(Animal animal)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(animal.getBirthDay());

        fieldName.setText(animal.getNickName());
        fieldDate.setText(date);


//        data.setSelectedIndex(1);
    }

    private void makeDialog(ArrayList<String> commands, ArrayList<String> types)
    {
        fieldName = new JTextField(15);
        fieldDate = new JTextField(15);

        add(new JLabel("Имя:"), "gap, sg 1");
        add(fieldName, "wrap");
        add(new JLabel("Дата рождения:"), "gap, sg 1");
        add(fieldDate, "wrap");
        add(new JSeparator(), "growx, spanx, gaptop 10, gapbottom 5, wrap");
        add(new JLabel("Вид"), "gap, sg 1");
        add(new JLabel("Команды"), "gap, wrap");

        listTypes = addList(types, ListSelectionModel.SINGLE_SELECTION, "gap");
        listCommands = addList(commands, ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, "span, grow, wrap");

//        // TODO: добавить заполнение и выделение полей, если это предусмотрено !!!!
    }

    private JList<String> addList(ArrayList<String> list, int selection, String align)
    {
        JList<String> data = new JList<>(list.toArray(new String[0]));
        data.setSelectionMode(selection);
        JScrollPane typeScroll = new JScrollPane(data);
        add(typeScroll, align);
        return data;
    }
}
