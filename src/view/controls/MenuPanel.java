package view.controls;

import animal.base.Animal;
import net.miginfocom.swing.MigLayout;
import view.controls.models.AnimalTableModel;
import view.controls.models.InfoLayout;
import view.controls.models.JComboBoxModel;
import view.events.*;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;

public class MenuPanel extends JPanel {

    private final EventListenerList listenerList;

    JTable table;                   // ссылка на таблицу, для автозаполнения полей формы UpdateAnimal()
    ArrayList<String> types;
    ArrayList<String> commands;

    // данные фильтров вывода
    JTextField filterDateFrom;
    JTextField filterDateTo;
    JComboBox filterType;

    // лейбл для вывода краткой инфы
    JLabel labelInfo;


    public MenuPanel(JTable table)
    {
        super();
        listenerList = new EventListenerList();
        this.table = table;
        this.types = new ArrayList<>();
        this.commands = new ArrayList<>();
        init();
    }

    public void setTypes(ArrayList<String> types)
    {
        this.types = types;
        JComboBoxModel boxModel = (JComboBoxModel) filterType.getModel();
        boxModel.clear();
        boxModel.add(types);
        filterType.setSelectedIndex(0);
        filterType.updateUI();
    }

    public void setCommands(ArrayList<String> commands)
    {
        this.commands = commands;
    }

    public void setInfo(String text)
    {
        labelInfo.setText(text);
    }

    private void init()
    {
        setLayout(new InfoLayout(8));
        addButton("Добавить", lstNewRec);
        addButton("Изменить", lstUpdateRec);
        addButton("Выход", e -> fireExit(new ActionEvent(this, 1, "Exit")));
        add(new JSeparator());
        add(new JLabel("Фильтр:"));
        add(addTabPanel());
        labelInfo = new JLabel("Info");
        add(labelInfo);
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

        JComboBoxModel boxModel = new JComboBoxModel();
        filterType = new JComboBox<>(boxModel);
        boxModel.add(types);
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
                NewAnimalEvent event = new NewAnimalEvent(e.getSource(), dlg.getFieldName(), dlg.getFieldType(), dlg.getFieldDate(), dlg.getCommands());
                if (event.getBirthDay() != null
                        && event.getNick() != null
                        && event.getType() != null
                        && !event.getNick().isBlank()
                        && !event.getType().isBlank()
                )
                    fireNewAnimal(event);
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
            AnimalTableModel model = (AnimalTableModel) table.getModel();
            int row = table.getSelectedRow();
            if (row >= 0)
            {
                Animal animal = model.getRow(table.convertRowIndexToModel(row));
                if (animal != null)
                {
                    InputDialog dlg = new InputDialog(animal.getNickName(),
                            animal.getClass().getSimpleName(),
                            animal.getBirthDay(),
                            animal.getCommands().getCommands(),
                            commands, types);
                    if (JOptionPane.showConfirmDialog(null, dlg, "Изменить данные",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                        UpdateAnimalEvent event = new UpdateAnimalEvent(e.getSource(),
                                animal.getId(),
                                dlg.getFieldName(),
                                dlg.getFieldType(),
                                dlg.getFieldDate(),
                                dlg.getCommands());
                        if (event.getBirthDay() != null
                                && event.getNick() != null
                                && event.getType() != null
                                && !event.getNick().isBlank()
                                && !event.getType().isBlank()
                        )
                            fireUpdateAnimal(event);
                    }
                }
            }
        }
    };

    /*
     * ======================================================
     * Реализация регистрации слушателей и рассылки сообщений
     * ======================================================
     */

    public <T extends EventListener> void removeListeners(Class<T> t, T l)
    {
        listenerList.remove(t, l);
    }

    public <T extends EventListener> void addListener(Class<T> t, T l)
    {
        listenerList.add(t, l);
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

    private void fireExit(ActionEvent event)
    {
        ActionListener[] listeners = listenerList.getListeners(ActionListener.class);
        for (int i = listeners.length-1; i>=0; i--)
            (listeners[i]).actionPerformed(event);
    }

}
