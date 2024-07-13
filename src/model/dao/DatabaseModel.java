package model.dao;

import animal.base.Animal;
import animal.base.AnimalSex;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Обертка для взаимодействия модели с БД.
 */
public class DatabaseModel implements IDataBase, IDbCloseable {

    Connection con;

    CommandsDB commandsDB;
    TypesDB typesDB;
    SexDB sexDB;
    AnimalDB db;

    public DatabaseModel(Connection con)
    {
        this.con = con;
        commandsDB = new CommandsDB(con);
        typesDB = new TypesDB(con);
        sexDB = new SexDB(con);
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
    public ArrayList<Animal> getBySex(AnimalSex sex)
    {
        ArrayList<Animal> res = db.getBySex(sex.toString());
        return makeResult(res);
    }

    @Override
    public ArrayList<String> getCommandsList()
    {
        return commandsDB.getCommandsList();
    }

    @Override
    public ArrayList<String> getTypesList()
    {
        return typesDB.getTypesList();
    }

    @Override
    public ArrayList<String> getSexList()
    {
        return sexDB.getSexList();
    }

    /**
     * Обновление данных животного
     */
    @Override
    public void updateAnimal(Animal animal)
    {
        try {
            con.setAutoCommit(false);
            db.updateAnimal(animal);
            // добавляем команды
            commandsDB.setCommands(animal);
            con.commit();

        } catch (NullPointerException | SQLException e) {
            System.out.println("DB error: " + e.getMessage());
            // отменяем транзакцию
            try {
                con.rollback();
            } catch (SQLException ignored) {}
        } finally {
            // нужно восстановить AutoCommit()
            try {
                con.setAutoCommit(true);
            } catch (SQLException ignored) {}
        }
    }

    /**
     * Добавление нового животного
     */
    @Override
    public void addAnimal(Animal animal)
    {
        try {
            con.setAutoCommit(false);
            db.addAnimal(animal);
            // добавляем команды
            commandsDB.setCommands(animal);
            con.commit();

        } catch (NullPointerException | SQLException e) {
            System.out.println("DB error: " + e.getMessage());
            // отменяем транзакцию
            try {
                con.rollback();
            } catch (SQLException ignored) {}
        } finally {
            // нужно восстановить AutoCommit()
            try {
                con.setAutoCommit(true);
            } catch (SQLException ignored) {}
        }
    }

    @Override
    public void close()
    {
        System.out.println(getClass().getSimpleName() + ".close()");
        db.close();
        commandsDB.close();
    }
}
