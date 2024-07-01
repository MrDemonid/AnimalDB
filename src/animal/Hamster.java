package animal;

import animal.base.AnimalPets;

import java.util.Date;

public class Hamster extends AnimalPets {

    public Hamster(int id, String nickName, Date birthDay, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, comments, commands);
    }

    public Hamster(int id, String nickName, Date birthDay)
    {
        super(id, nickName, birthDay);
    }
}
