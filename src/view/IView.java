package view;

import animal.base.Animal;

import java.util.ArrayList;

public interface IView {

    void setCommandList(ArrayList<String> commands);
    void setClassList(ArrayList<String> classList);
    void setTableData(ArrayList<Animal> animals);

    void update();

    void done();
}
