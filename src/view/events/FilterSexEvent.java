package view.events;

import java.util.EventObject;

public class FilterSexEvent extends EventObject {

    String sex;

    public FilterSexEvent(Object source, String sex)
    {
        super(source);
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }

    @Override
    public String toString() {
        return "FilterSexEvent{" +
                "sex='" + sex + '\'' +
                '}';
    }
}
