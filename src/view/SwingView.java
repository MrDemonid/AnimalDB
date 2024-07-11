package view;

import animal.base.Animal;
import view.controls.AnimalTableModel;
import view.controls.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.EventListener;

public class SwingView extends View {

    private MenuPanel menuPanel;

    private JScrollPane scrollPane;
    private JTable table;
    private AnimalTableModel tbModel;


    public SwingView()
    {
        createViews();
        setVisible(true);
        addWindowListener(new ViewWindowAdapter());
    }

    private void createViews()
    {
        setTitle("Animals database");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        // создаём панели
        tbModel = new AnimalTableModel();
        table = new JTable(tbModel);
        menuPanel = new MenuPanel(table);
        scrollPane = new JScrollPane(table);
        getContentPane().add(menuPanel, BorderLayout.WEST);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        addListener(ActionListener.class, doCloseWindow);
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
        System.out.println(getClass().getSimpleName() + ".close()");
        removeListeners(ActionListener.class, doCloseWindow);
    }


    public <T extends EventListener> void removeListeners(Class<T> t, T l)
    {
        menuPanel.removeListeners(t, l);
    }

    public <T extends EventListener> void addListener(Class<T> t, T l)
    {
        menuPanel.addListener(t, l);
    }


    private final ActionListener doCloseWindow = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            doClose();
        }
    };

    private void doClose()
    {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    /**
     *
     *  Слушатель эвентов окна приложения, для загрузки и сохранения параметров окна
     *
     */
    public class ViewWindowAdapter extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e)
        {
            done();
            System.exit(0);
        }
    }

}
