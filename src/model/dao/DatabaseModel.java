package model.dao;

import animal.base.Animal;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

/**
 * Обертка для взаимодействия модели с БД.
 */
public class DatabaseModel implements IDataBase, IDbCloseable {

    Connection con;

    CommandsDB commandsDB;
    AnimalDB db;

    public DatabaseModel(Connection con)
    {
        this.con = con;
        commandsDB = new CommandsDB(con);
        db = new AnimalDB(con);
    }

    private ArrayList<Animal> makeResult(ArrayList<Animal> animals)
    {
        if (animals != null)
        {
            for (Animal animal : animals)
            {
                ArrayList<String> cmd = commandsDB.getCommandsById(animal.getId());
                if (cmd != null)
                    for (String s : cmd)
                        animal.addCommand(s);
            }
        }
        return animals;
    }

    @Override
    public ArrayList<Animal> getAll()
    {
        ArrayList<Animal> res = db.getAll();
        return makeResult(res);
    }

    @Override
    public ArrayList<Animal> getByBirthdays(Date from, Date to)
    {
        ArrayList<Animal> res = db.getByBirthdays(from, to);
        return makeResult(res);
    }

    @Override
    public ArrayList<Animal> getByType(String type)
    {
        ArrayList<Animal> res = db.getByType(type);
        return makeResult(res);
    }

    @Override
    public ArrayList<Animal> getById(int id)
    {
        ArrayList<Animal> res = db.getById(id);
        return makeResult(res);
    }

    @Override
    public ArrayList<String> getCommandsList()
    {
        return commandsDB.getCommandsList();
    }

    /**
     * Обновление данных животного
     */
    @Override
    public void updateAnimal(Animal animal) {

    }

    /**
     * Добавление нового животного
     */
    @Override
    public void addAnimal(Animal animal) {

    }

    @Override
    public void close()
    {
        db.close();
        commandsDB.close();
    }
}
