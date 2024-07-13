package animal;

import animal.base.AnimalPack;
import animal.base.AnimalSex;

import java.util.Date;

public class Camel extends AnimalPack {

    public Camel(int id, String nickName, Date birthDay, AnimalSex sex, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, sex, comments, commands);
    }

    public Camel(int id, String nickName, Date birthDay)
    {
        super(id, nickName, birthDay);
    }

    public Camel(String nickName, Date birthDay)
    {
        super(0, nickName, birthDay);
    }

}
