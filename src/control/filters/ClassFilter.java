package control.filters;

import animal.base.Animal;
import animal.base.AnimalClass;
import model.Model;

import java.util.ArrayList;

public class ClassFilter extends FilterBase {

    AnimalClass classes;

    public ClassFilter(Model model)
    {
        super(model);
        classes = null;
    }

    public void setClass(AnimalClass classes)
    {
        this.classes = classes;
    }


    @Override
    public ArrayList<Animal> getData()
    {
        return model.getByClass(classes);
    }

}
