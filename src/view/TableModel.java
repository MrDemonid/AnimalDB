package view;

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


    public void setColumnsName(String ... names)
    {
        columnsName.clear();
        columnsName.addAll(Arrays.asList(names));
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
