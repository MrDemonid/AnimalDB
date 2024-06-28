package animal;

import animal.base.AnimalPack;

import java.util.Date;

public class Camel extends AnimalPack {

    public Camel(int id, String nickName, Date birthDay, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, comments, commands);
    }
}
