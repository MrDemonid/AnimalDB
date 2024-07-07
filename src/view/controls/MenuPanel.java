package view.controls;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.System.exit;

public class MenuPanel extends JPanel {

    private final EventListenerList listenerList;

    ArrayList<String> types;

    // данные фильтров вывода
    JTextField filterDateFrom;
    JTextField filterDateTo;
    JComboBox filterType;


    public MenuPanel(ArrayList<String> types)
    {
        super();
        listenerList = new EventListenerList();
        this.types = types;
        init();
    }

    private void init()
    {
        setLayout(new InfoLayout(4));
        addButton("Добавить", lstNewRec);
        addButton("Изменить", lstUpdateRec);
        addButton("Выход", e -> exit(0));
        add(new JSeparator());
        add(new JLabel("Фильтр:"));

        add(addTabPanel());

    }

    private void addButton(String title, ActionListener listener)
    {
        JButton btn = new JButton(title);
        btn.addActionListener(listener);
        add(btn);
    }

    /*
     * Создаёт панель вкладок для фильтра вывода данных с БД
     */
    private JTabbedPane addTabPanel()
    {
        JPanel tabAll = new JPanel(new MigLayout("fill"));
        JPanel tabDate = new JPanel(new MigLayout("fill"));
        JPanel tabType = new JPanel(new MigLayout("fill"));

        // создаем элементы для вкладки "Все"
        JButton apply = new JButton("Применить");
        apply.addActionListener(lstApplyAll);
        tabAll.add(apply, "south");

        // создаем элементы для вкладки "По дате"
        filterDateFrom = new JTextField(14);
        filterDateTo = new JTextField(14);
        apply = new JButton("Применить");
        apply.addActionListener(lstApplyDate);
        tabDate.add(new JLabel("От:"), "gap 4, gaptop 10, sg 1");
        tabDate.add(filterDateFrom, "gap 4, gaptop 10, wrap");
        tabDate.add(new JLabel("До:"), "gap 4, gapbottom 10, sg 1");
        tabDate.add(filterDateTo, "gap 4, gapbottom 10, wrap");
        tabDate.add(apply, "south");

        // создаем элементы для вкладки "По виду"
        apply = new JButton("Применить");
        apply.addActionListener(lstApplyType);
        filterType = new JComboBox<>(types.toArray(new String[0]));
        tabType.add(filterType, "gap 4, gaptop 10, wrap");
        tabType.add(apply, "south");

        // собираем панель вкладок
        JTabbedPane pane = new JTabbedPane();
        pane.addTab("Все", tabAll);
        pane.addTab("По дате", tabDate);
        pane.addTab("По типу", tabType);
        return pane;
    }

    /*
     * слушатель кнопки "Применить" для фильтра "Все"
     */
    ActionListener lstApplyAll = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            fireActionPerformed(new ActionEvent(e.getSource(), 201, "FilterAll"));
        }
    };

    /*
     * слушатель кнопки "Применить" для фильтра "По дате"
     */
    ActionListener lstApplyDate = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            fireActionPerformed(new ActionEvent(e.getSource(), 201, "FilterDate"));
        }
    };

    /*
     * слушатель кнопки "Применить" для фильтра "По виду"
     */
    ActionListener lstApplyType = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            fireActionPerformed(new ActionEvent(e.getSource(), 201, "FilterType"));
        }
    };




    /*
     * слушатель кнопки "Добавить"
     */
    ActionListener lstNewRec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            fireActionPerformed(new ActionEvent(e.getSource(), 101, "New"));
        }
    };

    /*
     * слушатель кнопки "Изменить"
     */
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
