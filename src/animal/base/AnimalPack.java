package animal.base;

import animal.AnimalCommands;

import java.util.Date;

public abstract class AnimalPack extends Animal {


    public AnimalPack(int id, String nickName, Date birthDay, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, comments, commands, AnimalClass.Packs);
    }

    public AnimalPack(int id, String nickName, Date birthDay)
    {
        super(id, nickName, birthDay, "", new AnimalCommands(), AnimalClass.Packs);
    }
}

