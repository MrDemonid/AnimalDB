package view.controls;

import animal.base.AnimalSex;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * Класс для реализации диалогового окна для заполнения
 * или изменения данных о животном
 */
public class InputDialog extends JPanel {
    private JTextField fieldName;
    private JTextField fieldDate;
    private JList<String> listTypes;
    private JList<String> listSex;
    private JList<String> listCommands;


    public AnimalSex getSex()
    {
        return AnimalSex.Unknown;
    }

    public InputDialog(ArrayList<String> commands, ArrayList<String> types, ArrayList<String> sex)
    {
        setLayout(new MigLayout());
        makeDialog(commands, types, sex);
    }

    public InputDialog(String nick, String type, Date birthDay, String sex, ArrayList<String> selected, ArrayList<String> commands, ArrayList<String> types, ArrayList<String> sexs)
    {
        this(commands, types, sexs);
        setDatas(nick, type, birthDay, selected, sex);
    }

    public String getFieldName()
    {
        return fieldName.getText();
    }

    public String getFieldDate()
    {
        return fieldDate.getText();
    }

    public ArrayList<String> getCommands()
    {
        try {
            return (ArrayList<String>) listCommands.getSelectedValuesList();
        } catch (Exception ignored) {
        }
        return null;
    }

    public String getFieldType()
    {
        return listTypes.getSelectedValue();
    }

    private void setDatas(String nick, String type, Date birthDay, ArrayList<String> commands, String sex)
    {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String date = formatter.format(birthDay);
            fieldName.setText(nick);
            fieldDate.setText(date);

            // выделяем поля команд
            DefaultListModel<String> modelCmd = (DefaultListModel<String>) listCommands.getModel();
            ArrayList<Integer> selected = new ArrayList<>();
            for (String command : commands)
            {
                int idx = modelCmd.indexOf(command);
                if (idx >= 0)
                    selected.add(idx);
            }
            listCommands.setSelectedIndices(selected.stream().mapToInt(i -> i).toArray());

            // выделяем поле вида
            modelCmd = (DefaultListModel<String>) listTypes.getModel();
            int index = modelCmd.indexOf(type);
            listTypes.setSelectedIndex(index);
            listTypes.setEnabled(false);

            // выделяем поле пола
            modelCmd = (DefaultListModel<String>) listSex.getModel();
            index = modelCmd.indexOf(sex);
            listSex.setSelectedIndex(index);
            listSex.setEnabled(false);

        } catch (Exception ignored) {}
    }


    private void makeDialog(ArrayList<String> commands, ArrayList<String> types, ArrayList<String> sex)
    {
        fieldName = new JTextField(16);
        fieldDate = new JTextField(16);
        add(new JLabel("Имя:"), "gap, sg 1");
        add(fieldName, "span 3, wrap");
        add(new JLabel("Дата рождения:"), "gap, sg 1");
        add(fieldDate, "span 3, wrap");
        add(new JSeparator(), "growx, spanx, gaptop 10, gapbottom 5, wrap");
        add(new JLabel("Вид"), "gap");
        add(new JLabel("Пол"), "gap");
        add(new JLabel("     "), "gap");
        add(new JLabel("Команды"), "gap, wrap");
        listTypes = addList(types, ListSelectionModel.SINGLE_SELECTION, "gap");
        listSex = addList(sex, ListSelectionModel.SINGLE_SELECTION, "gap");
        listCommands = addList(commands, ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, "skip 1, grow, wrap");

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
