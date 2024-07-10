package view;

import animal.base.Animal;
import view.controls.AnimalTable;
import view.controls.MenuPanel;
import view.events.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SwingView extends View {

    private MenuPanel menuPanel;

    private JScrollPane scrollPane;
    private JTable table;
    private AnimalTable tbModel;


    public SwingView()
    {
        createViews();
        setVisible(true);
    }


    private void createViews()
    {
        setTitle("Animals database");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        // создаём панели
        tbModel = new AnimalTable();
        table = new JTable(tbModel);
        menuPanel = new MenuPanel(table);
        scrollPane = new JScrollPane(table);
        getContentPane().add(menuPanel, BorderLayout.WEST);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Регистрируем обработчики событий
        menuPanel.addListener(FilterAllListener.class, doFilterAll);
        menuPanel.addListener(FilterDateListener.class, doFilterDate);
        menuPanel.addListener(FilterTypeListener.class, doFilterType);
        menuPanel.addListener(NewAnimalListener.class, doNewAnimal);
        menuPanel.addListener(UpdateAnimalListener.class, doUpdateAnimal);
    }



    @Override
    public void setCommandList(ArrayList<String> commands)
    {
        menuPanel.setCommands(commands);
    }

    @Override
    public void setClassList(ArrayList<String> classList)
    {
        menuPanel.setTypes(classList);
    }

    @Override
    public void setTableData(ArrayList<Animal> animals)
    {
        tbModel.addSource(animals);
    }

    @Override
    public void update()
    {
        scrollPane.repaint();
    }

    @Override
    public void done()
    {
        System.out.println(getClass().getSimpleName() + "() close");
        menuPanel.removeListeners(FilterAllListener.class, doFilterAll);
        menuPanel.removeListeners(FilterDateListener.class, doFilterDate);
        menuPanel.removeListeners(FilterTypeListener.class, doFilterType);
        menuPanel.removeListeners(NewAnimalListener.class, doNewAnimal);
        menuPanel.removeListeners(UpdateAnimalListener.class, doUpdateAnimal);
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


}
