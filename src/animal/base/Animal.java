package animal.base;

import animal.AnimalCommands;

import java.util.Date;

public abstract class Animal {
    private int id;
    private String nickName;
    private Date birthDay;
    private String comments;
    private AnimalCommands commands;

    private AnimalClass animalClass;

    public Animal(int id, String nickName, Date birthDay, String comments, AnimalCommands commands, AnimalClass animalClass)
    {
        this.id = id;
        this.nickName = nickName;
        this.birthDay = birthDay;
        this.comments = comments;
        this.commands = commands;
        this.animalClass = animalClass;
    }



    public int getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public String getComments() {
        return comments;
    }

    public AnimalCommands getCommands() {
        return commands;
    }

    public AnimalClass getAnimalClass() {
        return animalClass;
    }

    /**
     * Добавление новой команды животному
     * @param command команда
     */
    public void addCommand(String command)
    {
        commands.addCommand(command);
    }

    @Override
    public String toString() {
        return String.format("[%d] (%s: '%s') '%s', %te-%tm-%tY, '%s'",
                            id, animalClass.toString(), getClass().getSimpleName(), nickName, birthDay, birthDay, birthDay, commands);
    }
}
