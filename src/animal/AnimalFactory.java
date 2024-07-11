package animal;

import animal.base.Animal;

import java.util.ArrayList;
import java.util.Date;

public class AnimalFactory {

    static public Animal createAnimal(String type, int id, String nick, Date date, String comments, ArrayList<String> commandList)
    {
        try {
            AnimalCommands commands = new AnimalCommands();
            commands.addCommand(commandList);
            Animal animal;
            switch (type) {
                case "Cat" -> animal = new Cat(id, nick, date, comments, commands);
                case "Dog" -> animal = new Dog(id, nick, date, comments, commands);
                case "Hamster" -> animal = new Hamster(id, nick, date, comments, commands);
                case "Horse" -> animal = new Horse(id, nick, date, comments, commands);
                case "Camel" -> animal = new Camel(id, nick, date, comments, commands);
                case "Donkey" -> animal = new Donkey(id, nick, date, comments, commands);
                default -> animal = null;
            }
            return animal;

        } catch (Exception ignored) {}
        return null;
    }

    static public Animal createAnimal(String type, int id, String nick, Date date, ArrayList<String> commandList)
    {
        return AnimalFactory.createAnimal(type, id, nick, date, "", commandList);
    }

    static public Animal createAnimal(String type, int id, String nick, Date date)
    {
        return AnimalFactory.createAnimal(type, id, nick, date, new ArrayList<>());
    }


}
