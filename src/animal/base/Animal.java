package animal.base;

import animal.AnimalCommands;

import java.util.Date;

public abstract class Animal {
    private int id;
    private String nickName;
    private Date birthDay;
    private String comments;
    private AnimalCommands commands;

    private String animalClass;

    public Animal(int id, String nickName, Date birthDay, String comments, AnimalCommands commands, String animalClass)
    {
        this.id = id;
        this.nickName = nickName;
        this.birthDay = birthDay;
        this.comments = comments;
        this.commands = commands;
        this.animalClass = animalClass;
    }

    public void addCommand(String command)
    {
        commands.addCommand(command);
    }
}
