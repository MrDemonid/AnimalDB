package view.events;

import java.util.ArrayList;

public class UpdateAnimalEvent extends NewAnimalEvent {

    public UpdateAnimalEvent(Object source, int id, String nick, String type, String date, ArrayList<String> commands)
    {
        super(source, nick, type, date, commands);
        this.id = id;
    }

}
