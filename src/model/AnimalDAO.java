package model;

import animal.base.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;

public class AnimalDAO implements IAnimalDAO {



    private final Connection con;
    private PreparedStatement stAll;
    private PreparedStatement stByDate;
    private PreparedStatement stByType;
    private PreparedStatement stById;

    public AnimalDAO(Connection con)
    {
        this.con = con;
//        stAll = con.prepareStatement()

//        stmtGetById = connection.prepareStatement("SELECT * FROM objects WHERE id=?");
//        stmtAdd = connection.prepareStatement("INSERT INTO objects (id, description) VALUES (?,?)");
//        stmtDelete = connection.prepareStatement("DELETE FROM objects WHERE id=?");
//        stmtUpdate = connection.prepareStatement("UPDATE objects SET id=?, description=?, WHERE id=?");
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
    public ArrayList<String> getCommandsList() {
        return null;
    }

    @Override
    public void addAnimal(Animal animal) {

    }
}
