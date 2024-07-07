package view;

import animal.base.Animal;
import view.controls.AnimalTable;
import view.controls.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphView extends View {

    private JFrame window;
    private MenuPanel menuPanel;
    private JScrollPane scrollPane;
    private AnimalTable tbModel;

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
        menuPanel = new MenuPanel(commands, types);

        tbModel = new AnimalTable();
        JTable table = new JTable(tbModel);
        scrollPane = new JScrollPane(table);

        window.getContentPane().add(menuPanel, BorderLayout.WEST);
        window.getContentPane().add(scrollPane, BorderLayout.CENTER);

        window.setVisible(true);
        // подключаем layouts

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

}
