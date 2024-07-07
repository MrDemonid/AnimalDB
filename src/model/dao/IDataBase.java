package model.dao;

import animal.base.Animal;

import java.util.ArrayList;
import java.util.Date;


public interface IDataBase {

    ArrayList<Animal> getAll();
    ArrayList<Animal> getByBirthdays(Date from, Date to);
    ArrayList<Animal> getByType(String type);
    ArrayList<Animal> getById(int id);

    ArrayList<String> getCommandsList();
    ArrayList<String> getTypesList();

    boolean updateAnimal(Animal animal);
    boolean addAnimal(Animal animal);
}
