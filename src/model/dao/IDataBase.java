package model.dao;

import animal.base.Animal;

import java.util.ArrayList;
import java.util.Date;

/**
 * Интерфейс между менеджером БД и самой БД (низкоуровневых классов)
 */
public interface IDataBase {

    ArrayList<Animal> getAll();
    ArrayList<Animal> getByBirthdays(Date from, Date to);
    ArrayList<Animal> getByType(String type);
    ArrayList<Animal> getById(int id);

    ArrayList<String> getCommandsList();
    ArrayList<String> getTypesList();

    void updateAnimal(Animal animal);
    void addAnimal(Animal animal);
}
