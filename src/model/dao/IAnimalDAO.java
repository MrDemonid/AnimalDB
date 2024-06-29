package model.dao;

import animal.base.Animal;

import java.util.ArrayList;
import java.util.Date;


public interface IAnimalDAO {

    public ArrayList<Animal> getAll();
    public ArrayList<Animal> getByDate(Date from, Date to);
    public ArrayList<Animal> getByType(String type);
    public ArrayList<Animal> getById(int id);

    public ArrayList<String> getCommandsList();

    public void addAnimal(Animal animal);
}
