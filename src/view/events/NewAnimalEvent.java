package view.events;

import java.util.ArrayList;
import java.util.Date;

public class NewAnimalEvent extends SimpleDateEvent {

    private String nick;
    private Date birthDay;
    private String type;
    private ArrayList<String> commands;

    public NewAnimalEvent(Object source, String nick, String type, String date, ArrayList<String> commands)
    {
        super(source);
        this.nick = nick;
        this.type = type;
        this.birthDay = strToDate(date);
        this.commands = commands;
    }

    public String getNick()
    {
        return nick;
    }

    public String getType() {
        return type;
    }

    public Date getBirthDay()
    {
        return birthDay;
    }

    public ArrayList<String> getCommands()
    {
        return commands;
    }

    @Override
    public String toString() {
        return "NewAnimalEvent{" +
                "nick='" + nick + '\'' +
                ", birthDay=" + birthDay +
                ", type='" + type + '\'' +
                ", commands=" + commands +
                '}';
    }
}
