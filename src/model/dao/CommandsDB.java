package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommandsDB implements IDbCloseable {

    static final String sqlGetAll = "SELECT id, denotation FROM cmd_info;";
    static final String sqlGetById = "SELECT denotation FROM cmd_info JOIN cmd_list ON cmd_list.cmd_id = cmd_info.id JOIN animals ON animals.id = cmd_list.anm_id WHERE animals.id = ?;";

    Connection con;
    PreparedStatement stAll;
    PreparedStatement stById;

    public CommandsDB(Connection con)
    {
        this.con = con;
        prepareStatements();
    }

    private ArrayList<String> makeResult(ResultSet rs) throws SQLException {
        ArrayList<String> res = new ArrayList<>();
        while (rs.next()) {
            res.add(rs.getString("denotation"));
        }
        return res;
    }

    /**
     * Возвращает список всех доступных команд, или null при ошибке
     */
    public ArrayList<String> getCommandsList()
    {
        try {
            ResultSet rs = stAll.executeQuery();
            return makeResult(rs);
        }  catch (NullPointerException | SQLException e) {
            System.out.println(getClass().getSimpleName() + " error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Возвращает список команд животного
     * @param id идентификатор животного (в таблице animals)
     */
    public ArrayList<String> getCommandsById(int id)
    {
        try {
            stById.setInt(1, id);
            ResultSet rs = stById.executeQuery();
            return makeResult(rs);
        } catch (NullPointerException | SQLException e) {
            return null;
        }
    }

    @Override
    public void close()
    {
        stAll = closeStatement(stAll);
        stById = closeStatement(stById);
    }

    private void prepareStatements()
    {
        try {
            stAll = con.prepareStatement(sqlGetAll);
            stById = con.prepareStatement(sqlGetById);
        } catch (SQLException e) {
            close();
        }
    }

    private PreparedStatement closeStatement(PreparedStatement stmt)
    {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException ignored) {
        }
        return null;
    }

}
