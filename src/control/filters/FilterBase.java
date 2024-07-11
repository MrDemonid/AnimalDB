package control.filters;

import animal.base.Animal;
import model.Model;

import java.util.ArrayList;

public abstract class FilterBase {

    protected Model model;

    public FilterBase(Model model)
    {
        this.model = model;
    }

    abstract public ArrayList<Animal> getData();
}
