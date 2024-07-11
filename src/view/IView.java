package view;

import animal.base.Animal;

import java.util.ArrayList;
import java.util.EventListener;

public interface IView {

    void setCommandList(ArrayList<String> commands);
    void setClassList(ArrayList<String> classList);
    void setTableData(ArrayList<Animal> animals);

    void update();

    void done();

    <T extends EventListener> void removeListeners(Class<T> t, T l);
    <T extends EventListener> void addListener(Class<T> t, T l);

}
