package model;

import animal.base.Animal;

import java.util.ArrayList;
import java.util.Date;

public interface IModel {

    ArrayList<Animal> getAllAnimals();
    ArrayList<Animal> getByBirthdays(Date from, Date to);
    ArrayList<Animal> getByType(String type);
    ArrayList<Animal> getById(int id);

    ArrayList<String> getCommandsList();
    ArrayList<String> getTypesList();

    void updateAnimal(Animal animal);
    void addAnimal(Animal animal);
}
