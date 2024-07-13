package animal.base;

import animal.AnimalCommands;

import java.util.Date;

public abstract class AnimalPets extends Animal {


    public AnimalPets(int id, String nickName, Date birthDay, AnimalSex sex, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, sex, comments, commands, AnimalClass.Pets);
    }

    public AnimalPets(int id, String nickName, Date birthDay)
    {
        super(id, nickName, birthDay, AnimalSex.Unknown, "", new AnimalCommands(), AnimalClass.Pets);
    }
}
