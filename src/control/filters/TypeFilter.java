package control.filters;

import animal.base.Animal;
import model.Model;

import java.util.ArrayList;

public class TypeFilter extends FilterBase {

    private String type;


    public TypeFilter(Model model)
    {
        super(model);
        this.type = "";
    }

    protected void setType(String type)
    {
        this.type = type;
    }

    @Override
    public ArrayList<Animal> getData()
    {
        return model.getByType(type);
    }
}
