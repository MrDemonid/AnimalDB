package animal;

import animal.base.AnimalPets;
import animal.base.AnimalSex;

import java.util.Date;

public class Hamster extends AnimalPets {

    public Hamster(int id, String nickName, Date birthDay, AnimalSex sex, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, sex, comments, commands);
    }

    public Hamster(int id, String nickName, Date birthDay)
    {
        super(id, nickName, birthDay);
    }

    public Hamster(String nickName, Date birthDay)
    {
        super(0, nickName, birthDay);
    }
}
