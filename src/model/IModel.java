package model;

import animal.base.Animal;
import animal.base.AnimalClass;
import animal.base.AnimalSex;

import java.util.ArrayList;
import java.util.Date;

/**
 * Интерфейс взаимодействия модели и БД
 */
public interface IModel {

    /*
        Фильтры выборки данных из БД
     */
    ArrayList<Animal> getAllAnimals();
    ArrayList<Animal> getByBirthdays(Date from, Date to);
    ArrayList<Animal> getByType(String type);
    ArrayList<Animal> getByClass(AnimalClass classes);
    ArrayList<Animal> getBySex(AnimalSex sex);

    /*
        Выборка из БД значений таблиц команд и видов животных
     */
    ArrayList<String> getCommandsList();
    ArrayList<String> getTypesList();
    ArrayList<String> getSexList();

    /*
        Добавление новых и изменение существующих животных
     */
    void updateAnimal(Animal animal);
    void addAnimal(Animal animal);
}
