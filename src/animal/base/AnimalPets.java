package animal.base;

import animal.AnimalCommands;

import java.util.Date;

public abstract class AnimalPets extends Animal {


    public AnimalPets(int id, String nickName, Date birthDay, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, comments, commands, AnimalClass.Pets);
    }

    public AnimalPets(int id, String nickName, Date birthDay)
    {
        super(id, nickName, birthDay, "", new AnimalCommands(), AnimalClass.Pets);
    }
}
