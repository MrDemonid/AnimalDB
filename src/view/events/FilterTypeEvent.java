package view.events;

import java.util.EventObject;

public class FilterTypeEvent extends EventObject {

    private String type;

    public FilterTypeEvent(Object source, String type)
    {
        super(source);
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    @Override
    public String toString() {
        return "FilterTypeEvent{" +
                "type='" + type + '\'' +
                '}';
    }
}
