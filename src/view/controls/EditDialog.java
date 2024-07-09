package view.controls;


import animal.base.Animal;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditDialog extends JDialog {

    private JTextField fieldName;
    private JTextField fieldDate;
    private JList<String> listTypes;
    private JList<String> listCommands;

    public EditDialog(Frame owner, boolean modal, ArrayList<String> commands, ArrayList<String> types)
    {
        super(owner, modal);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        makeDialog(commands, types);
        pack();
    }

    public EditDialog(Frame owner, boolean modal, Animal animal, ArrayList<String> commands, ArrayList<String> types)
    {
        this(owner, modal, commands, types);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        makeDialog(commands, types);
        pack();
        setVisible(true);
    }


    private void makeDialog(ArrayList<String> commands, ArrayList<String> types)
    {
        JPanel pan = new JPanel(new MigLayout());

        fieldName = new JTextField(15);
        fieldDate = new JTextField(15);
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancel");
        ok.addActionListener(listenerOk);
        cancel.addActionListener(listenerCancel);

        pan.add(new JLabel("Имя:"), "gap, sg 1");
        pan.add(fieldName, "wrap");
        pan.add(new JLabel("Дата рождения:"), "gap, sg 1");
        pan.add(fieldDate, "wrap");
        pan.add(new JSeparator(), "growx, spanx, gaptop 10, gapbottom 5, wrap");
        pan.add(new JLabel("Вид"), "gap, sg 1");
        pan.add(new JLabel("Команды"), "gap, wrap");
        listTypes = addList(pan, types, ListSelectionModel.SINGLE_SELECTION, "gap");
        listCommands = addList(pan, commands, ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, "span, grow, wrap");

        add(pan, BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.add(ok);
        panel.add(cancel);
        add(panel, BorderLayout.SOUTH);

        Dimension size = getPreferredSize();
        setSize(size.width, size.height);
        setLocationRelativeTo(null);

//        add(ok, "gaptop 20, skip 1, split, sg buttons, align right");
//        add(cancel, "sg buttons");
    }

    private JList<String> addList(JPanel pan, ArrayList<String> list, int selection, String align)
    {
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addAll(list);
        JList<String> data = new JList<>(model);
        data.setSelectionMode(selection);
        JScrollPane typeScroll = new JScrollPane(data);
        pan.add(typeScroll, align);
        return data;
    }


    /*
     * слушатель кнопки "Ok"
     */
    ActionListener listenerOk = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Ok");
            setVisible(false);
        }
    };

    /*
     * слушатель кнопки "Отмена"
     */
    ActionListener listenerCancel = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Cancel");
            setVisible(false);
        }
    };




}
