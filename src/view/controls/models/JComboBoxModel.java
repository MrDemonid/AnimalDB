package view.controls.models;

import javax.swing.*;
import java.util.ArrayList;

public class JComboBoxModel extends AbstractListModel<String> implements javax.swing.ComboBoxModel<String> {

    private ArrayList<String> items;
    private String selected;

    public JComboBoxModel()
    {
        items = new ArrayList<>();
    }

    public void remove(String item)
    {
        items.remove(item);
        fireContentsChanged(this, 0, items.size());
    }

    public void add(String item)
    {
        items.add(item);
        fireContentsChanged(this, 0, items.size());
    }

    public void add(ArrayList<String> list)
    {
        items.addAll(list);
    }

    public void clear()
    {
        items.clear();
    }

    @Override
    public int getSize()
    {
        return items.size();
    }

    @Override
    public String getElementAt(int index)
    {
        return items.get(index);
    }

    @Override
    public void setSelectedItem(Object item)
    {
        selected = (String) item;
    }

    @Override
    public Object getSelectedItem()
    {
        return selected;
    }
}