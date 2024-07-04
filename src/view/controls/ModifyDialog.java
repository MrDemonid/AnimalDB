package view.controls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import net.miginfocom.swing.MigLayout;


public class ModifyDialog extends JDialog {


    public ModifyDialog(Frame owner, ArrayList<String> commands)
    {
        super(owner);
        // При активизации кнопки ОК диалогове окно закрывается.

        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                setVisible(false);
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                setVisible(false);
            }
        });

        JPanel pan = new JPanel(new MigLayout());
        pan.add(new JLabel("Имя:"), "gap, sg 1");
        pan.add(new JTextField(20), "wrap");
        pan.add(new JLabel("Дата рождения:"), "gap, sg 1");
        pan.add(new JTextField(20), "wrap");

        JList<String> list = new JList<String>(commands.toArray(new String[0]));
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane listbox = new JScrollPane(list);
        pan.add(listbox, "span, grow, wrap");

        add(pan, BorderLayout.NORTH);



        JPanel panel = new JPanel();
        panel.add(cancel);
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);


        Dimension size = getPreferredSize();
        setSize(size.width, size.height);
        setLocationRelativeTo(null);
    }


}
