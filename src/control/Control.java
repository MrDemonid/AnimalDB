package control;

import animal.base.Animal;
import model.Model;
import view.View;

import javax.swing.*;
import java.util.ArrayList;


public class Control {

    Model model;
    View view;

    public Control(Model model, View view)
    {
        this.model = model;
        this.view = view;

        // Вешаем обработчик на закрытие программы
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run() {
                // Обработка завершения приложения
                System.out.println("Close program");
                SwingUtilities.invokeLater(() -> view.done()
                );
            }
        });
    }

    public void run()
    {
        ArrayList<String> cmd = model.getCommandsList();
        ArrayList<String> type = model.getTypesList();
        ArrayList<Animal> anm = model.getAllAnimals();

        SwingUtilities.invokeLater(() -> {
                    view.setCommandList(cmd);
                    view.setClassList(type);
                    view.setTableData(anm);
                    view.update();
                });
    }

}
