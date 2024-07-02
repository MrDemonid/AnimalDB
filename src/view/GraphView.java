package view;

import animal.base.Animal;
import view.controls.InfoLayout;
import view.controls.TableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class GraphView extends View {

    private JFrame window;
    private JPanel panBtn;
    private JScrollPane scrollPane;
    private TableModel tbModel;


    public GraphView()
    {
        init();
    }

    private void init()
    {
        window = new JFrame("Animals database");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        // создаём панели

        panBtn = new JPanel();
        panBtn.setLayout(new InfoLayout(4));
        panBtn.add(new JButton("Добавить"));
        panBtn.add(new JButton("Посмотреть"));
        panBtn.add(new JButton("Поиск"));
        panBtn.add(new JButton("Выход"));

        tbModel = new TableModel();
        JTable table = new JTable(tbModel);
        scrollPane = new JScrollPane(table);

        window.getContentPane().add(panBtn, BorderLayout.WEST);
        window.getContentPane().add(scrollPane, BorderLayout.CENTER);

        window.setVisible(true);
        // подключаем layouts

    }


    @Override
    public void showTable(ArrayList<Animal> animals)
    {
        try {
            tbModel.setColumnsClass(Integer.class, String.class, Date.class, String.class, String.class);
            tbModel.setColumnsName("ID", "Имя", "День рождения", "Команды", "Комментарии");

            ArrayList<ArrayList<Object>> rows = new ArrayList<>();
            for (Animal animal : animals)
            {
                rows.add(makeRow(animal));
            }
            tbModel.addSource(rows);
            scrollPane.repaint();

        } catch (NullPointerException e)
        {
            System.out.println("showTable(): " + e.getMessage());
        }
    }


    private ArrayList<Object> makeRow(Animal animal)
    {
        ArrayList<Object> res = new ArrayList<>();
        res.add(animal.getId());
        res.add(animal.getNickName());
        res.add(animal.getBirthDay());
        res.add(animal.getCommands().toString());
        res.add(animal.getComments());
        return res;
    }
}
