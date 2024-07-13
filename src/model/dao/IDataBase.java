package model.dao;

import animal.base.Animal;
import animal.base.AnimalSex;

import java.util.ArrayList;
import java.util.Date;

/**
 * Интерфейс между менеджером БД и самой БД (низкоуровневых классов)
 */
public interface IDataBase {

    ArrayList<Animal> getAll();
    ArrayList<Animal> getByBirthdays(Date from, Date to);
    ArrayList<Animal> getByType(String type);
    ArrayList<Animal> getBySex(AnimalSex sex);

    ArrayList<String> getCommandsList();
    ArrayList<String> getTypesList();
    ArrayList<String> getSexList();

    void updateAnimal(Animal animal);
    void addAnimal(Animal animal);
}
