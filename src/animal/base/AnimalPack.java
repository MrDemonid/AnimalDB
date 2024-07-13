package animal.base;

import animal.AnimalCommands;

import java.util.Date;

public abstract class AnimalPack extends Animal {


    public AnimalPack(int id, String nickName, Date birthDay, AnimalSex sex, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, sex, comments, commands, AnimalClass.Packs);
    }

    public AnimalPack(int id, String nickName, Date birthDay)
    {
        super(id, nickName, birthDay, AnimalSex.Unknown, "", new AnimalCommands(), AnimalClass.Packs);
    }
}

