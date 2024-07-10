package control;

import model.Model;
import view.View;


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
                view.done();
            }
        });
    }

    public void run()
    {
        view.setCommandList(model.getCommandsList());
        view.setClassList(model.getTypesList());

        view.setTableData(model.getAllAnimals());
        view.update();
    }


}
