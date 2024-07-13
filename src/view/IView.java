package view;

import animal.base.Animal;

import java.util.ArrayList;
import java.util.EventListener;

public interface IView {

    void setCommandList(ArrayList<String> commands);    // список команд животных
    void setClassList(ArrayList<String> classList);     // список видов животных
    void setTableData(ArrayList<Animal> animals);       // список данных о животных
    void setInfo(String text);                          // какая то информация для юзера

    void update();                                      // обновить UI

    void done();                                        // освободить ресурсы

    // добавление и удаление слушателей (типы в папке /events
    <T extends EventListener> void removeListeners(Class<T> t, T l);
    <T extends EventListener> void addListener(Class<T> t, T l);

}
