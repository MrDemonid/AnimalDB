package view.controls.events;

import java.awt.event.ActionEvent;

public class FilterTypeEvent extends ActionEvent {

    private final String type;

    public FilterTypeEvent(Object source, int id, String command, String type)
    {
        super(source, id, command);
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

}
