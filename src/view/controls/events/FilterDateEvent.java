package view.controls.events;

import java.util.Date;

public class FilterDateEvent extends BaseDateEvent {
    private Date from;
    private Date to;

    public FilterDateEvent(Object source, int id, String command, String from, String to)
    {
        super(source, id, command);
        this.from = parseDate(from);
        this.to = parseDate(to);
    }

    public Date getFrom() {
        return from;
    }
    public Date getTo() {
        return to;
    }

}
