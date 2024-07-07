package view.controls;

import animal.Cat;
import animal.base.Animal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.System.exit;

public class MenuPanel extends JPanel {

    ArrayList<String> commands;
    ArrayList<String> types;

    public MenuPanel(ArrayList<String> commands, ArrayList<String> types)
    {
        super();
        this.commands = commands;
        this.types = types;
        init();
    }

    private void init()
    {
        setLayout(new InfoLayout(4));
        addButton("Добавить", lstNewRec);
        addButton("Изменить", lstUpdateRec);
        addButton("Выход", e -> exit(0));

    }

    private void addButton(String title, ActionListener listener)
    {
        JButton btn = new JButton(title);
        btn.addActionListener(listener);
        add(btn);
    }

    ActionListener lstNewRec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (JOptionPane.showConfirmDialog(null,
                    new InputDialog(commands, types),
                    "Добавление животного",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION)
            {
                System.out.println("YES");
            } else {
                System.out.println("Cansel");
            }
        }
    };

    ActionListener lstUpdateRec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (JOptionPane.showConfirmDialog(null,
                    new InputDialog(new Cat("Waskes", new Date(2024-1900, Calendar.JULY, 12)),

                            commands, types),
                    "Изменить данные",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION)
            {
                System.out.println("YES");
            } else {
                System.out.println("Cansel");
            }


        }
    };

}
