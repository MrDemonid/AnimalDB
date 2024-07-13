package model;

import animal.base.Animal;
import animal.base.AnimalClass;
import animal.base.AnimalSex;
import model.dao.DatabaseModel;

import java.lang.reflect.InvocationTargetException;
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

    public void close()
    {
        System.out.println(getClass().getSimpleName() + ".close()");
        if (con != null) {
            try {
                db.close();
                con.close();
            } catch (SQLException ignored) {}
        }
    }


    private void connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            con = DriverManager.getConnection(url, user, password);
            db = new DatabaseModel(con);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            System.out.println("no sync MySQL driver");

        } catch (SQLException e) {
            System.out.println("error connect to database");
        }
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
    public ArrayList<Animal> getByClass(AnimalClass classes)
    {
        ArrayList<Animal> animals = db.getAll();
        ArrayList<Animal> res = new ArrayList<>();
        for (Animal animal : animals)
        {
            if (animal.getAnimalClass() == classes)
                res.add(animal);
        }
        return res;
    }

    @Override
    public ArrayList<Animal> getBySex(AnimalSex sex)
    {
        return db.getBySex(sex);
    }

    @Override
    public ArrayList<String> getCommandsList()
    {
        return db.getCommandsList();
    }

    @Override
    public ArrayList<String> getTypesList()
    {
        return db.getTypesList();
    }
    @Override
    public ArrayList<String> getSexList()
    {
        return db.getSexList();
    }

    @Override
    public void addAnimal(Animal animal)
    {
        db.addAnimal(animal);
    }

    @Override
    public void updateAnimal(Animal animal)
    {
        db.updateAnimal(animal);
    }


}
