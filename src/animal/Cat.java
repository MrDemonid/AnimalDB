package animal;

import animal.base.AnimalPets;
import animal.base.AnimalSex;

import java.util.Date;

public class Cat extends AnimalPets {

    public Cat(int id, String nickName, Date birthDay, AnimalSex sex, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, sex, comments, commands);
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
