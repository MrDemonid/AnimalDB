package view.events;

import java.util.Date;

public class FilterDateEvent extends SimpleDateEvent {

    private Date from;
    private Date to;

    public FilterDateEvent(Object source, String from, String to)
    {
        super(source);
        this.from = strToDate(from);
        this.to = strToDate(to);
    }

    public Date getFrom()
    {
        return from;
    }

    public Date getTo()
    {
        return to;
    }

    @Override
    public String toString() {
        return "FilterDateEvent{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
