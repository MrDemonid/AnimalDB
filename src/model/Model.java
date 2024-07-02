package model;

import animal.base.Animal;
import model.dao.DatabaseModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Model implements IModel {

    private static final String url = "jdbc:mysql://localhost:3306/anim_db?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String user = "root";
    private static final String password = "qqqqqqqq";

    private static Connection con;
    private DatabaseModel db;


    public Model()
    {
        connect();
    }

//    public void showAnimals()
//    {
//        ArrayList<Animal> cmd = db.getAll();
//        if (cmd == null)
//        {
//            System.out.println("error: database is problem");
//            return;
//        }
//        System.out.println("Animals: " + cmd);
//        for (Animal animal : cmd) {
//            System.out.println(animal);
//        }
//    }
//
//    public void showCommands()
//    {
//        ArrayList<String> cmd = db.getCommandsList();
//        if (cmd == null)
//        {
//            System.out.println("error: database is problem");
//            return;
//        }
//        System.out.println("commands: " + cmd);
//    }
//
//    public void showType(String type)
//    {
//        ArrayList<Animal> cmd = db.getByType(type);
//        if (cmd == null)
//        {
//            System.out.println("error: database is problem");
//            return;
//        }
//        System.out.println("Type: " + cmd);
//    }
//
//    public void showId(int id)
//    {
//        ArrayList<Animal> cmd = db.getById(id);
//        if (cmd == null)
//        {
//            System.out.println("error: database is problem");
//            return;
//        }
//        System.out.println("Id: " + cmd);
//    }
//
//    public void showBithdays(Date from, Date to)
//    {
//        ArrayList<Animal> res = db.getByBirthdays(from, to);
//        if (res != null)
//        {
//            System.out.println("Animals: " + res);
//        }
//    }

    public void addAnimal(Animal animal)
    {
        db.addAnimal(animal);
    }

    public void updateAnimal(Animal animal)
    {
        db.updateAnimal(animal);
    }








    private boolean connect()
    {
        try {
            con = DriverManager.getConnection(url, user, password);
            db = new DatabaseModel(con);
            return true;
        } catch (SQLException e) {
        }
        return false;
    }



    @Override
    public ArrayList<Animal> getAllAnimals()
    {
        return db.getAll();
    }

    @Override
    public ArrayList<Animal> getByBirthdays(Date from, Date to)
    {
        return db.getByBirthdays(from, to);
    }

    @Override
    public ArrayList<Animal> getByType(String type)
    {
        return db.getByType(type);
    }

    @Override
    public ArrayList<Animal> getById(int id)
    {
        return db.getById(id);
    }

    @Override
    public ArrayList<String> getCommandsList()
    {
        return db.getCommandsList();
    }
}
