package model.dao;

import animal.base.Animal;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

public class AnimalDAO implements IAnimalDAO {

    CommandsDAO commandsDAO;
    Connection con;

    public AnimalDAO(Connection con)
    {
        this.con = con;
        commandsDAO = new CommandsDAO(con);
    }

    @Override
    public ArrayList<Animal> getAll() {
        return null;
    }

    @Override
    public ArrayList<Animal> getByDate(Date from, Date to) {
        return null;
    }

    @Override
    public ArrayList<Animal> getByType(String type) {
        return null;
    }

    @Override
    public ArrayList<Animal> getById(int id) {
        return null;
    }

    @Override
    public ArrayList<String> getCommandsList()
    {
        return commandsDAO.getCommandsList();
    }

    public ArrayList<String> getCommandsById(int id)
    {
        return commandsDAO.getCommandsById(id);
    }

    @Override
    public void addAnimal(Animal animal) {

    }
}
