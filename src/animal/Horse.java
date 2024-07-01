package animal;

import animal.base.AnimalPack;

import java.util.Date;

public class Horse extends AnimalPack {

    public Horse(int id, String nickName, Date birthDay, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, comments, commands);
    }

    public Horse(int id, String nickName, Date birthDay)
    {
        super(id, nickName, birthDay);
    }
}
