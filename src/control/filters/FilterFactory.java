package control.filters;

import model.Model;

import java.util.Date;

/*
    Фабрика фильтров. Поскольку фильтров всего три, то не стал заморачиваться с ENUM или константами для их нумерации.
    Если буду расширять, то переделаю на ENUM.
 */

public class FilterFactory {

    private final FilterBase[] filters;

    public FilterFactory(Model model)
    {
        filters = new FilterBase[] {new NothingFilter(model), new BirthdayFilter(model), new TypeFilter(model)};
    }

    public FilterBase getFilter()
    {
        return filters[0];
    }

    public FilterBase getFilter(Date from, Date to)
    {
        ((BirthdayFilter) filters[1]).setDate(from, to);
        return filters[1];
    }

    public FilterBase getFilter(String type)
    {
        ((TypeFilter) filters[2]).setType(type);
        return filters[2];
    }

}
