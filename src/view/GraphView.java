package view;

import animal.base.Animal;
import view.controls.AnimalTable;
import view.controls.MenuPanel;
import view.events.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class GraphView extends View {

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
        window.addWindowListener(new ViewWindowAdapter());
    }

    private void init()
    {
        window = new JFrame("Animals database");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        // создаём панели
        tbModel = new AnimalTable();
        table = new JTable(tbModel);
        menuPanel = new MenuPanel(table, types, commands);
        scrollPane = new JScrollPane(table);

        window.getContentPane().add(menuPanel, BorderLayout.WEST);
        window.getContentPane().add(scrollPane, BorderLayout.CENTER);

        window.setVisible(true);

        // Регистрируем обработчики событий
        menuPanel.addListener(FilterAllListener.class, doFilterAll);
        menuPanel.addListener(FilterDateListener.class, doFilterDate);
        menuPanel.addListener(FilterTypeListener.class, doFilterType);
        menuPanel.addListener(NewAnimalListener.class, doNewAnimal);
        menuPanel.addListener(UpdateAnimalListener.class, doUpdateAnimal);
    }

    public void close()
    {
        menuPanel.removeListeners(FilterAllListener.class, doFilterAll);
        menuPanel.removeListeners(FilterDateListener.class, doFilterDate);
        menuPanel.removeListeners(FilterTypeListener.class, doFilterType);
        menuPanel.removeListeners(NewAnimalListener.class, doNewAnimal);
        menuPanel.removeListeners(UpdateAnimalListener.class, doUpdateAnimal);
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


    /*===========================================================================
     *
     * Реализация слушателей от контролов
     *
     ===========================================================================*/

    private final FilterAllListener doFilterAll = new FilterAllListener() {
        @Override
        public void actionPerformed(FilterAllEvent event) {
            System.out.println("do not filter: " + event);
        }
    };

    private final FilterDateListener doFilterDate = new FilterDateListener() {
        @Override
        public void actionPerformed(FilterDateEvent event)
        {
            System.out.println("do filter by Date: " + event);
        }
    };

    private final FilterTypeListener doFilterType = new FilterTypeListener() {
        @Override
        public void actionPerformed(FilterTypeEvent event) {
            System.out.println("do filter by Class: " + event);
        }
    };

    private final NewAnimalListener doNewAnimal = new NewAnimalListener() {
        @Override
        public void actionPerformed(NewAnimalEvent event) {
            System.out.println("do new Animal: " + event);
        }
    };

    private final UpdateAnimalListener doUpdateAnimal = new UpdateAnimalListener() {
        @Override
        public void actionPerformed(UpdateAnimalEvent event) {
            System.out.println("do update Animal: " + event);
        }
    };




    /**
     *
     *  Слушатель эвентов окна приложения, для загрузки и сохранения параметров окна
     *
     */
    public class ViewWindowAdapter extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e)
        {
            if (e.getSource() instanceof JFrame && (JFrame) e.getSource() == window)
            {
                close();
            }
            System.exit(0);
        }

    }


}
