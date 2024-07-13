package animal;

import animal.base.AnimalPack;
import animal.base.AnimalSex;

import java.util.Date;

public class Donkey extends AnimalPack {

    public Donkey(int id, String nickName, Date birthDay, AnimalSex sex, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, sex, comments, commands);
    }

    public Donkey(int id, String nickName, Date birthDay)
    {
        super(id, nickName, birthDay);
    }

    public Donkey(String nickName, Date birthDay)
    {
        super(0, nickName, birthDay);
    }

}
