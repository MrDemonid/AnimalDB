package view.controls;

import animal.base.Animal;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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

    /**
     * Заполняем поля данными Animal
     * @param animal животное, которое необходимо немного изменить
     */
    private void fillDialog(Animal animal)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(animal.getBirthDay());

        fieldName.setText(animal.getNickName());
        fieldDate.setText(date);
        // выделяем поля команд
        DefaultListModel<String> modelCmd = (DefaultListModel<String>) listCommands.getModel();
        ArrayList<Integer> selected = new ArrayList<>();
        for (String command : animal.getCommands())
        {
            int idx = modelCmd.indexOf(command);
            if (idx >= 0)
                selected.add(idx);
        }
        listCommands.setSelectedIndices(selected.stream().mapToInt(i -> i).toArray());
        // выделяем поле вида
        modelCmd = (DefaultListModel<String>) listTypes.getModel();
        int index = modelCmd.indexOf(animal.getClass().getSimpleName());
        listTypes.setSelectedIndex(index);
        listTypes.setEnabled(false);
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
    }

    private JList<String> addList(ArrayList<String> list, int selection, String align)
    {
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addAll(list);
        JList<String> data = new JList<>(model);
        data.setSelectionMode(selection);
        JScrollPane typeScroll = new JScrollPane(data);
        add(typeScroll, align);
        return data;
    }
}
