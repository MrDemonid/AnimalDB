package view.controls;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.System.exit;

public class MenuPanel extends JPanel {

    private final EventListenerList listenerList;

    ArrayList<String> commands;
    ArrayList<String> types;

    public MenuPanel(ArrayList<String> commands, ArrayList<String> types)
    {
        super();
        listenerList = new EventListenerList();
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
            fireActionPerformed(new ActionEvent(e.getSource(), 101, "New"));
        }
    };

    ActionListener lstUpdateRec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            fireActionPerformed(new ActionEvent(e.getSource(), 102, "Update"));
        }
    };


    /*
     * ======================================================
     * Реализация регистрации слушателей и рассылки сообщений
     * ======================================================
     */

    public void addActionListener(ActionListener listener)
    {
        listenerList.add(ActionListener.class, listener);
    }

    public void removeActionListener(ActionListener listener)
    {
        listenerList.remove(ActionListener.class, listener);
    }

    private void fireActionPerformed(ActionEvent event)
    {
        Object[] listeners = listenerList.getListeners(ActionListener.class);
        for (int i = listeners.length-1; i>=0; i--)
            ((ActionListener)listeners[i]).actionPerformed(event);
    }

}
