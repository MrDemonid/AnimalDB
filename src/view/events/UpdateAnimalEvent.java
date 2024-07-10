package view.events;

import java.util.ArrayList;

public class UpdateAnimalEvent extends NewAnimalEvent {

    public UpdateAnimalEvent(Object source, String nick, String type, String date, ArrayList<String> commands)
    {
        super(source, nick, type, date, commands);
    }


}
