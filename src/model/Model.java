package model;

import model.dao.AnimalDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Model {

    private static final String url = "jdbc:mysql://localhost:3306/anim_db?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String user = "root";
    private static final String password = "qqqqqqqq";

    private static Connection con;
    private AnimalDAO db;

    public Model()
    {
        connect();
        if (con != null)
        {
            db = new AnimalDAO(con);
        }

    }

    public void showCommands()
    {
        ArrayList<String> cmd = db.getCommandsList();
        if (cmd == null)
        {
            System.out.println("error: database is problem");
            return;
        }
        System.out.println("commands: " + cmd);
    }

    public void showById(int id)
    {
        ArrayList<String> cmd = db.getCommandsById(id);
        if (cmd == null)
        {
            System.out.println("error: database is problem");
            return;
        }
        System.out.println("commands: " + cmd);
    }

    private void connect()
    {
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
        }

    }
}
