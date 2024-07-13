package control;

import animal.AnimalFactory;
import animal.base.Animal;
import animal.base.AnimalClass;
import animal.base.AnimalSex;
import control.filters.*;
import model.Model;
import view.View;
import view.events.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Control {

    Model model;
    View view;

    FilterBase filter;
    FilterFactory filterFactory;


    public Control(Model model, View view)
    {
        this.model = model;
        this.view = view;
        this.filterFactory = new FilterFactory(model);
        this.filter = filterFactory.getFilter();        // по умолчанию фильтр отсутствует

        // Вешаем обработчик на закрытие программы
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run() {
                // Обработка завершения приложения
                done();
                model.close();
            }
        });
    }

    public void run()
    {
        setListeners();             // Регистрируем обработчики событий
        ArrayList<String> cmd = model.getCommandsList();
        ArrayList<String> type = model.getTypesList();
        ArrayList<String> sex = model.getSexList();
        ArrayList<String> classes = new ArrayList<>(Arrays.asList(AnimalClass.Pets.name(), AnimalClass.Packs.name()));
        SwingUtilities.invokeLater(() -> {
            view.setCommandList(cmd);
            view.setTypeList(type);
            view.setSexList(sex);
            view.setClassList(classes);
        });
        viewSetTable();
    }

    private void viewSetTable()
    {
        ArrayList<Animal> animals = filter.getData();
        int count = animals.size();

        SwingUtilities.invokeLater(() -> {
            view.setTableData(animals);
            view.setInfo("Всего: " + count + " животных");
            view.update();
        });
    }

    private void setListeners()
    {
        SwingUtilities.invokeLater(() -> {
            view.addListener(FilterAllListener.class, doFilterAll);
            view.addListener(FilterDateListener.class, doFilterDate);
            view.addListener(FilterTypeListener.class, doFilterType);
            view.addListener(FilterClassListener.class, doFilterClass);
            view.addListener(FilterSexListener.class, doFilterSex);
            view.addListener(NewAnimalListener.class, doNewAnimal);
            view.addListener(UpdateAnimalListener.class, doUpdateAnimal);
        });
    }

    private void done()
    {
        // К этому времени поток с view должен остановиться, но само представление еще не уничтожено
        System.out.println(getClass().getSimpleName() + ".close()");
        view.removeListeners(FilterAllListener.class, doFilterAll);
        view.removeListeners(FilterDateListener.class, doFilterDate);
        view.removeListeners(FilterTypeListener.class, doFilterType);
        view.removeListeners(FilterClassListener.class, doFilterClass);
        view.removeListeners(FilterSexListener.class, doFilterSex);
        view.removeListeners(NewAnimalListener.class, doNewAnimal);
        view.removeListeners(UpdateAnimalListener.class, doUpdateAnimal);
    }

    /*===========================================================================
     *
     * Реализация слушателей от контролов View
     *
     ===========================================================================*/

    private final FilterAllListener doFilterAll = new FilterAllListener() {
        @Override
        public void actionPerformed(FilterAllEvent event) {
            filter = filterFactory.getFilter();
            viewSetTable();
        }
    };

    private final FilterDateListener doFilterDate = new FilterDateListener() {
        @Override
        public void actionPerformed(FilterDateEvent event)
        {
            filter = filterFactory.getFilter(event.getFrom(), event.getTo());
            viewSetTable();
        }
    };

    private final FilterTypeListener doFilterType = new FilterTypeListener() {
        @Override
        public void actionPerformed(FilterTypeEvent event) {
            filter = filterFactory.getFilter(event.getType());
            viewSetTable();
        }
    };

    private final FilterClassListener doFilterClass = new FilterClassListener() {
        @Override
        public void actionPerformed(FilterClassEvent event) {
            AnimalClass cl = AnimalClass.getClass(event.getClasses());
            if (cl != null)
            {
                filter = filterFactory.getFilter(cl);
                viewSetTable();
            }
        }
    };

    private final FilterSexListener doFilterSex = new FilterSexListener() {
        @Override
        public void actionPerformed(FilterSexEvent event)
        {
            AnimalSex sex = AnimalSex.getSex(event.getSex());
            if (sex != null)
            {
                filter = filterFactory.getFilter(sex);
                viewSetTable();
            }
        }
    };

    private final NewAnimalListener doNewAnimal = new NewAnimalListener() {
        @Override
        public void actionPerformed(NewAnimalEvent event) {
            Animal animal = AnimalFactory.createAnimal(event.getType(), 0, event.getNick(), event.getBirthDay(), event.getSex(), event.getCommands());
            if (animal != null)
            {
                model.addAnimal(animal);
                viewSetTable();
            }
        }
    };

    private final UpdateAnimalListener doUpdateAnimal = new UpdateAnimalListener() {
        @Override
        public void actionPerformed(UpdateAnimalEvent event) {
            Animal animal = AnimalFactory.createAnimal(event.getType(), event.getId(), event.getNick(), event.getBirthDay(), event.getSex(), event.getCommands());
            if (animal != null)
            {
                model.updateAnimal(animal);
                viewSetTable();
            }
        }
    };


}
