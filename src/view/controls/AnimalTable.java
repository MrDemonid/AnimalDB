package view.controls;

import animal.base.Animal;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AnimalTable extends AbstractTableModel {

    private ArrayList<String> columnsName;          // названия столбцов
    private ArrayList<Class> columnsClass;          // типы данных столбцов

    private ArrayList<Animal> animals;              // собственно массив строк с данными


    public AnimalTable()
    {
        super();
        setColumnsName("ID", "Вид", "Тип", "Имя", "День рождения", "Команды", "Комментарии");
        setColumnsClass(Integer.class, String.class, String.class, String.class, Date.class, String.class, String.class);
        animals = new ArrayList<>();
    }

    public Animal getRow(int row)
    {
        try {
            return animals.get(row);
        } catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    /**
     * Определяет типы данных для каждого столбца
     * @param classes список типов
     */
    public void setColumnsClass(Class ... classes)
    {
        columnsClass = new ArrayList<>();
        columnsClass.addAll(Arrays.asList(classes));
        fireTableStructureChanged();
    }

    /**
     * Определяет имена для столбцов
     * @param names список имён
     */
    public void setColumnsName(String ... names)
    {
        columnsName = new ArrayList<>();
        columnsName.addAll(Arrays.asList(names));
    }


    public void addSource(ArrayList<Animal> animals)
    {
        this.animals.clear();
        this.animals.addAll(animals);
        fireTableStructureChanged();
    }

    /*
        Возвращает тип столбца
     */
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        try {
            return columnsClass.get(columnIndex);
        } catch (IndexOutOfBoundsException e)
        {
            return String.class;
        }
    }

    /*
        Возвращает имя столбца (заголовки столбцов)
     */
    @Override
    public String getColumnName(int column)
    {
        try {
            return columnsName.get(column);
        } catch (IndexOutOfBoundsException e)
        {
            return "?";
        }
    }

    /*
        Определяет возможность редактирования заданной ячейки
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    /*
        Задает новое значение для ячеек с разрешенным редактированием
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
//        try {
//            if (aValue instanceof Animal)
//            {
//                Animal nanm = (Animal) aValue;
//                Animal animal = animals.get(rowIndex);
//
//            }
////            data.get(rowIndex).set(columnIndex, aValue);
//        } catch (IndexOutOfBoundsException ignored) {
//        }
    }

    /*
        Возвращает кол-во строк в таблице
    */
    @Override
    public int getRowCount()
    {
        return animals.size();
    }

    /*
        Возвращает кол-во столбцов в таблице
     */
    @Override
    public int getColumnCount()
    {
        return columnsName.size();
    }

    /*
        Возвращает значение в определенном столбце
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        try {
            Animal animal = animals.get(rowIndex);
            return switch (columnIndex)
            {
                case 0 -> animal.getId();
                case 1 -> animal.getClass().getSimpleName();
                case 2 -> animal.getAnimalClass();
                case 3 -> animal.getNickName();
                case 4 -> animal.getBirthDay();
                case 5 -> animal.getCommands();
                case 6 -> animal.getComments();
                default -> "";
            };
        } catch (IndexOutOfBoundsException e)
        {
            return "";
        }
    }
}
