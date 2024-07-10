package view.events;

import java.util.EventObject;

public class FilterAllEvent extends EventObject {

    public FilterAllEvent(Object source)
    {
        super(source);
    }

    @Override
    public String toString() {
        return "FilterAllEvent{}";
    }
}
