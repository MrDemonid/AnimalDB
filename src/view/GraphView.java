package view;

import animal.base.Animal;
import net.miginfocom.swing.MigLayout;
import view.controls.AnimalTable;
import view.controls.InputDialog;
import view.controls.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GraphView extends View implements ActionListener {

    private JFrame window;
    private MenuPanel menuPanel;
    private JScrollPane scrollPane;

    private AnimalTable tbModel;
    private JTable table;

    private ArrayList<String> commands;     // список команд
    private ArrayList<String> types;        // список видов животных

    public GraphView(ArrayList<String> commands, ArrayList<String> types)
    {
        this.commands = commands;
        this.types = types;
        init();
    }

    private void init()
    {
        window = new JFrame("Animals database");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        // создаём панели
        tbModel = new AnimalTable();
        table = new JTable(tbModel);
        menuPanel = new MenuPanel(commands, types);
        scrollPane = new JScrollPane(table);

        window.getContentPane().add(menuPanel, BorderLayout.WEST);
        window.getContentPane().add(scrollPane, BorderLayout.CENTER);

        window.setVisible(true);

        // Регистрируем обработчики событий
        menuPanel.addActionListener(this);
    }

    @Override
    public void showTable(ArrayList<Animal> animals)
    {
        try {
            tbModel.addSource(animals);
            scrollPane.repaint();

        } catch (NullPointerException e)
        {
            System.out.println("showTable(): " + e.getMessage());
        }
    }

    /**
     * Обработчик событий от кнопок панелей
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand())
        {
            case "New" -> newAnimal();
            case "Update" -> updateAnimal();
        }
        System.out.println("action: " + e.getActionCommand());
    }

    private void newAnimal()
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

    private void updateAnimal()
    {
        int row = table.getSelectedRow();
        if (row >= 0)
        {
            Animal animal = tbModel.getRow(table.convertRowIndexToModel(row));
            if (animal != null)
            {
                if (JOptionPane.showConfirmDialog(
                        null,
                        new InputDialog(animal, commands, types),
                        "Изменить данные",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    System.out.println("YES");
                } else {
                    System.out.println("Cansel");
                }

            }
        }

    }

}
