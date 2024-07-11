package control.filters;

import animal.base.Animal;
import model.Model;

import java.util.ArrayList;
import java.util.Date;

public class BirthdayFilter extends FilterBase {

    private Date from;
    private Date to;

    public BirthdayFilter(Model model)
    {
        super(model);
        this.from = new Date();
        this.to = new Date();
    }

    public void setDate(Date from, Date to)
    {
        this.from = from;
        this.to = to;
    }

    @Override
    public ArrayList<Animal> getData()
    {
        return model.getByBirthdays(from, to);
    }
}
