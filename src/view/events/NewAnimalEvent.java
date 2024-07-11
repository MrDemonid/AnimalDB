package view.events;

import java.util.ArrayList;
import java.util.Date;

public class NewAnimalEvent extends SimpleDateEvent {

    private String nick;
    private Date birthDay;
    private String type;
    private ArrayList<String> commands;
    protected int id;

    public NewAnimalEvent(Object source, String nick, String type, String date, ArrayList<String> commands)
    {
        super(source);
        this.nick = nick;
        this.type = type;
        this.birthDay = strToDate(date);
        this.commands = commands;
        this.id = 0;
    }

    public int getId() {
        return id;
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
        return getClass().getSimpleName() +
                "{" +
                "nick='" + nick + '\'' +
                ", birthDay=" + birthDay +
                ", type='" + type + '\'' +
                ", commands=" + commands +
                '}';
    }
}
