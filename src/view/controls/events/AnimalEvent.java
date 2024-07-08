package view.controls.events;

import animal.*;
import animal.base.Animal;

import java.util.Date;

public class AnimalEvent extends BaseDateEvent {

    Animal animal;

    public AnimalEvent(Object source, int id, String command, String type, String nick, String birthDay, String[] commands)
    {
        super(source, id, command);
        makeEvent(type, nick, birthDay, commands);
    }

    public Animal getAnimal()
    {
        return animal;
    }

    private void makeEvent(String type, String nick, String birthDay, String[] commands)
    {
        try {
            Date date = parseDate(birthDay);
            if (date == null)
                throw new NullPointerException();
            switch (type)
            {
                case "Cat" -> animal = new Cat(nick, date);
                case "Dog" -> animal = new Dog(nick, date);
                case "Hamster" -> animal = new Hamster(nick, date);
                case "Horse" -> animal = new Horse(nick, date);
                case "Camel" -> animal = new Camel(nick, date);
                case "Donkey" -> animal = new Donkey(nick, date);
                default -> animal = null;
            }
            // добавляем команды
            for (String s : commands)
                animal.addCommand(s);

        } catch (Exception e) {
            animal = null;
        }
    }


}
