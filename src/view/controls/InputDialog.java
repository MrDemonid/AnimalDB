package view.controls;

import animal.*;
import animal.base.Animal;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс для реализации диалогового окна для заполнения
 * или изменения данных о животном
 */
public class InputDialog extends JPanel implements ActionListener {
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

    public Animal getResult()
    {
        Animal animal;
        String nick = fieldName.getText();
        String sd = fieldDate.getText();
        String type = listTypes.getSelectedValue();
        List<String> cmd = listCommands.getSelectedValuesList();

        Date date = null;
        try {
            for (String dateFormat : new String[] {"dd-MM-yyyy", "dd/MM/yyyy", "dd.MM.yyyy"} ) {
                date = parseDate(sd, dateFormat);
                if (date != null)
                    break;
            }
            if (nick == null || date == null || nick.isBlank())
                throw new NullPointerException();

            switch (type)
            {
                case "Cat" -> animal = new Cat(nick, date);
                case "Dog" -> animal = new Dog(nick, date);
                case "Hamster" -> animal = new Hamster(nick, date);
                case "Horse" -> animal = new Horse(nick, date);
                case "Camel" -> animal = new Camel(nick, date);
                case "Donkey" -> animal = new Donkey(nick, date);
                default -> animal = null;
            }
            // добавляем команды
            for (String s : cmd) {
                animal.addCommand(s);
            }
            return animal;

        } catch (NullPointerException ignored) {}
        return null;
    }

    private Date parseDate(String source, String format) throws NullPointerException
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(source.replaceAll("\\s+", " ").trim());
        } catch (ParseException ignored) {}
        return null;
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
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancel");
        ok.addActionListener(this);
        cancel.addActionListener(this);;


        add(new JLabel("Имя:"), "gap, sg 1");
        add(fieldName, "wrap");
        add(new JLabel("Дата рождения:"), "gap, sg 1");
        add(fieldDate, "wrap");
        add(new JSeparator(), "growx, spanx, gaptop 10, gapbottom 5, wrap");
        add(new JLabel("Вид"), "gap, sg 1");
        add(new JLabel("Команды"), "gap, wrap");

        listTypes = addList(types, ListSelectionModel.SINGLE_SELECTION, "gap");
        listCommands = addList(commands, ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, "span, grow, wrap");

        add(ok, "gaptop 20, skip 1, split, sg buttons, align right");
        add(cancel, "sg buttons");
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


    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("action: " + e.getActionCommand());
    }
}
