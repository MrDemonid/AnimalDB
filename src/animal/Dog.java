package animal;

import animal.base.AnimalPets;
import animal.base.AnimalSex;

import java.util.Date;

public class Dog extends AnimalPets {

    public Dog(int id, String nickName, Date birthDay, AnimalSex sex, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, sex, comments, commands);
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
