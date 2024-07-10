package view.events;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;

public abstract class SimpleDateEvent extends EventObject {

    public SimpleDateEvent(Object source) {
        super(source);
    }

    protected Date strToDate(String source)
    {
        for (String dateFormat : new String[] {"dd-MM-yyyy", "dd/MM/yyyy", "dd.MM.yyyy"} )
        {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                return simpleDateFormat.parse(source.replaceAll("\\s+", " ").trim());
            } catch (Exception ignored) {}
        }
        return null;
    }

}
