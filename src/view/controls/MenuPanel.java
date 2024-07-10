package view.controls;

import animal.base.Animal;
import net.miginfocom.swing.MigLayout;
import view.events.*;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.System.exit;

public class MenuPanel extends JPanel {

    private final EventListenerList listenerList;

    ArrayList<String> types;
    ArrayList<String> commands;

    // данные фильтров вывода
    JTextField filterDateFrom;
    JTextField filterDateTo;
    JComboBox filterType;


    public MenuPanel(ArrayList<String> types, ArrayList<String> commands)
    {
        super();
        listenerList = new EventListenerList();
        this.types = types;
        this.commands = commands;
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
        public void actionPerformed(ActionEvent e)
        {
            fireFilterAll(new FilterAllEvent(e.getSource()));
        }
    };

    /*
     * слушатель кнопки "Применить" для фильтра "По дате"
     */
    ActionListener lstApplyDate = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            FilterDateEvent event = new FilterDateEvent(e.getSource(), filterDateFrom.getText(), filterDateTo.getText());
            if (event.getFrom() != null && event.getTo() != null)
                fireFilterDate(event);
        }
    };

    /*
     * слушатель кнопки "Применить" для фильтра "По виду"
     */
    ActionListener lstApplyType = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String s = (String) filterType.getSelectedItem();
            if (s != null && !s.isBlank())
                fireFilterType(new FilterTypeEvent(e.getSource(), s));
        }
    };


    /*
     * слушатель кнопки "Добавить"
     */
    ActionListener lstNewRec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            InputDialog dlg = new InputDialog(commands, types);
            if (JOptionPane.showConfirmDialog(null, dlg, "Добавление животного",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION)
            {
                System.out.println("YES");
                NewAnimalEvent event = new NewAnimalEvent(e.getSource(), dlg.getFieldName(),
                                            dlg.getFieldType(), dlg.getFieldDate(), dlg.getCommands());
                if (event.getBirthDay() != null
                        && event.getNick() != null
                        && event.getType() != null
                        && event.getCommands() != null
                        && !event.getNick().isBlank()
                        && !event.getType().isBlank()
                )
                    fireNewAnimal(event);

            } else {
                System.out.println("Cansel");
            }
        }
    };

    /*
     * слушатель кнопки "Изменить"
     */
    ActionListener lstUpdateRec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
//            int row = table.getSelectedRow();
//            if (row >= 0)
//            {
//                Animal animal = tbModel.getRow(table.convertRowIndexToModel(row));
//                if (animal != null)
//                {
//                    InputDialog dlg = new InputDialog(animal, commands, types);
//                    if (JOptionPane.showConfirmDialog(null, dlg, "Изменить данные",
//                            JOptionPane.OK_CANCEL_OPTION,
//                            JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION)
//                    {
//                        System.out.println("YES");
//                        animal = dlg.getResult();
//                        if (animal != null)
//                        {
//                            System.out.println("UPDATE ANIMAL: " + animal);
//                        }
//
//                    } else {
//                        System.out.println("Cansel");
//                    }
//
//                }
//            }
        }
    };



    /*
     * ======================================================
     * Реализация регистрации слушателей и рассылки сообщений
     * ======================================================
     */

    public void addFilterAllListener(FilterAllListener listener)
    {
        listenerList.add(FilterAllListener.class, listener);
    }

    public void addFilterDateListener(FilterDateListener listener)
    {
        listenerList.add(FilterDateListener.class, listener);
    }

    public void addFilterTypeListener(FilterTypeListener listener)
    {
        listenerList.add(FilterTypeListener.class, listener);
    }

    public void addNewAnimalListener(NewAnimalListener listener)
    {
        listenerList.add(NewAnimalListener.class, listener);
    }

    public void addUpdateAnimalListener(UpdateAnimalListener listener)
    {
        listenerList.add(UpdateAnimalListener.class, listener);
    }

    private void fireFilterAll(FilterAllEvent event)
    {
        FilterAllListener[] listeners = listenerList.getListeners(FilterAllListener.class);
        for (int i = listeners.length-1; i>=0; i--)
            (listeners[i]).actionPerformed(event);
    }

    private void fireFilterDate(FilterDateEvent event)
    {
        FilterDateListener[] listeners = listenerList.getListeners(FilterDateListener.class);
        for (int i = listeners.length-1; i>=0; i--)
            (listeners[i]).actionPerformed(event);
    }

    private void fireFilterType(FilterTypeEvent event)
    {
        FilterTypeListener[] listeners = listenerList.getListeners(FilterTypeListener.class);
        for (int i = listeners.length-1; i>=0; i--)
            (listeners[i]).actionPerformed(event);
    }

    private void fireNewAnimal(NewAnimalEvent event)
    {
        NewAnimalListener[] listeners = listenerList.getListeners(NewAnimalListener.class);
        for (int i = listeners.length-1; i>=0; i--)
            (listeners[i]).actionPerformed(event);
    }

    private void fireUpdateAnimal(UpdateAnimalEvent event)
    {
        UpdateAnimalListener[] listeners = listenerList.getListeners(UpdateAnimalListener.class);
        for (int i = listeners.length-1; i>=0; i--)
            (listeners[i]).actionPerformed(event);
    }


}
