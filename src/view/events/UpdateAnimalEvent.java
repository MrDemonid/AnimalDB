package view.events;

import animal.base.AnimalSex;

import java.util.ArrayList;

public class UpdateAnimalEvent extends NewAnimalEvent {

    public UpdateAnimalEvent(Object source, int id, String nick, String type, String date, AnimalSex sex, ArrayList<String> commands)
    {
        super(source, nick, type, date, sex, commands);
        this.id = id;
    }

}
