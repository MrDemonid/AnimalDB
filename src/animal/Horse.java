package animal;

import animal.base.AnimalPack;
import animal.base.AnimalSex;

import java.util.Date;

public class Horse extends AnimalPack {

    public Horse(int id, String nickName, Date birthDay, AnimalSex sex, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, sex, comments, commands);
    }

    public Horse(int id, String nickName, Date birthDay)
    {
        super(id, nickName, birthDay);
    }

    public Horse(String nickName, Date birthDay)
    {
        super(0, nickName, birthDay);
    }
}
