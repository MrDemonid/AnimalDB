package animal;

import animal.base.AnimalPets;

import java.util.Date;

public class Cat extends AnimalPets {

    public Cat(int id, String nickName, Date birthDay, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, comments, commands);
    }

    public Cat(int id, String nickName, Date birthDay)
    {
        super(id, nickName, birthDay);
    }
    public Cat(String nickName, Date birthDay)
    {
        super(0, nickName, birthDay);
    }
}
