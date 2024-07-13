package view.events;

import java.util.EventObject;

public class FilterClassEvent extends EventObject {

    String classes;

    public FilterClassEvent(Object source, String classes)
    {
        super(source);
        this.classes = classes;
    }

    public String getClasses()
    {
        return classes;
    }

    @Override
    public String toString() {
        return "FilterClassEvent{" +
                "classes='" + classes + '\'' +
                '}';
    }

}
