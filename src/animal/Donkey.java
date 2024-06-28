package animal;

import animal.base.AnimalPack;

import java.util.Date;

public class Donkey extends AnimalPack {

    public Donkey(int id, String nickName, Date birthDay, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, comments, commands);
    }
}
