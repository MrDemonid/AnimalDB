package view.controls.events;

import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseDateEvent extends ActionEvent {

    public BaseDateEvent(Object source, int id, String command)
    {
        super(source, id, command);
    }

    protected Date parseDate(String source)
    {
        String src = source.replaceAll("\\s+", " ").trim();
        for (String format : new String[] {"dd-MM-yyyy", "dd/MM/yyyy", "dd.MM.yyyy"} )
        {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                Date date = simpleDateFormat.parse(src);
                return date;
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}
