package control.filters;

import animal.base.Animal;
import model.Model;

import java.util.ArrayList;

public class NothingFilter extends FilterBase {

    public NothingFilter(Model model)
    {
        super(model);
    }

    @Override
    public ArrayList<Animal> getData()
    {
        return model.getAllAnimals();
    }
}
