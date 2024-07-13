package control.filters;

import animal.base.Animal;
import animal.base.AnimalSex;
import model.Model;

import java.util.ArrayList;

public class SexFilter extends FilterBase {

    AnimalSex sex;

    public SexFilter(Model model)
    {
        super(model);
        sex = AnimalSex.Unknown;
    }

    public void setSex(AnimalSex sex)
    {
        this.sex = sex;
    }

    @Override
    public ArrayList<Animal> getData()
    {
        return model.getBySex(sex);
    }

}
