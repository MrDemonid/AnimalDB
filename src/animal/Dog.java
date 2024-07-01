package animal;

import animal.base.AnimalPets;

import java.util.Date;

public class Dog extends AnimalPets {

    public Dog(int id, String nickName, Date birthDay, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, comments, commands);
    }

    public Dog(int id, String nickName, Date birthDay)
    {
        super(id, nickName, birthDay);
    }

    public Dog(String nickName, Date birthDay)
    {
        super(0, nickName, birthDay);
    }
}
