package animal;

import animal.base.Animal;
import animal.base.AnimalSex;

import java.util.ArrayList;
import java.util.Date;

public class AnimalFactory {

    static public Animal createAnimal(String type, int id, String nick, Date date, AnimalSex sex, String comments, ArrayList<String> commandList)
    {
        try {
            AnimalCommands commands = new AnimalCommands();
            commands.addCommand(commandList);
            Animal animal;
            switch (type) {
                case "Cat" -> animal = new Cat(id, nick, date, sex, comments, commands);
                case "Dog" -> animal = new Dog(id, nick, date, sex, comments, commands);
                case "Hamster" -> animal = new Hamster(id, nick, date, sex, comments, commands);
                case "Horse" -> animal = new Horse(id, nick, date, sex, comments, commands);
                case "Camel" -> animal = new Camel(id, nick, date, sex, comments, commands);
                case "Donkey" -> animal = new Donkey(id, nick, date, sex, comments, commands);
                default -> animal = null;
            }
            return animal;

        } catch (Exception ignored) {}
        return null;
    }

    static public Animal createAnimal(String type, int id, String nick, Date date, AnimalSex sex, ArrayList<String> commandList)
    {
        return AnimalFactory.createAnimal(type, id, nick, date, sex, "", commandList);
    }

    static public Animal createAnimal(String type, int id, String nick, Date date, AnimalSex sex)
    {
        return AnimalFactory.createAnimal(type, id, nick, date, sex, new ArrayList<>());
    }


}
