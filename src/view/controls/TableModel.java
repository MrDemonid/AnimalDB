package view.controls;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;

public class TableModel extends AbstractTableModel {

    private ArrayList<String> columnsName;          // названия столбцов
    private ArrayList<Class> columnsClass;          // типы данных столбцов
    private ArrayList<ArrayList<Object>> data;      // собственно массив строк с данными

    public TableModel()
    {
        super();
        columnsName = new ArrayList<>();
        columnsClass = new ArrayList<>();
        data = new ArrayList<>();
    }

    /**
     * Определяет типы данных для каждого столбца
     * @param classes список типов
     */
    public void setColumnsClass(Class ... classes)
    {
        columnsClass.clear();
        columnsClass.addAll(Arrays.asList(classes));
        fireTableStructureChanged();
    }

    /**
     * Определяет имена для столбцов
     * @param names список имён
     */
    public void setColumnsName(String ... names)
    {
        columnsName.clear();
        columnsName.addAll(Arrays.asList(names));
    }


    /**
     * Добавление строки в таблицу
     * @param row строка с данными
     */
    public void addRowData(ArrayList<Object> row)
    {
        data.add(row);
        fireTableRowsInserted(data.size()-1, data.size()-1);
    }

    /**
     * Задаёт новые данные для таблицы (старые удаляются)
     * @param source массов строк с массивом данных для каждого столбца
     */
    public void addSource(ArrayList<ArrayList<Object>> source)
    {
        data.clear();
        data.addAll(source);
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
        try {
            data.get(rowIndex).set(columnIndex, aValue);
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    /*
        Возвращает кол-во строк в таблице
    */
    @Override
    public int getRowCount()
    {
        return data.size();
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
            return (data.get(rowIndex)).get(columnIndex);
        } catch (IndexOutOfBoundsException e)
        {
            return "";
        }
    }
}
