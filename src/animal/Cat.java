package animal;

import animal.base.AnimalPets;

import java.util.Date;

public class Cat extends AnimalPets {

    public Cat(int id, String nickName, Date birthDay, String comments, AnimalCommands commands)
    {
        super(id, nickName, birthDay, comments, commands);
    }



}
