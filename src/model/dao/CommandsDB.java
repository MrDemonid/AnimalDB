package model.dao;

import animal.base.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommandsDB implements IDbCloseable {

    static final String sqlGetAll =  "SELECT id, denotation FROM commands;";
    static final String sqlGetById = "SELECT commands.denotation FROM commands " +
                                     "JOIN cmd_list ON cmd_list.cmd_id = commands.id " +
                                     "WHERE cmd_list.anm_id = ?;";

    // команды без предварительной компиляции
    static final String sqlDelete =  "DELETE FROM cmd_list WHERE anm_id=?;";
    static final String sqlInsCmd =  "INSERT INTO cmd_list (anm_id, cmd_id) SELECT ?, id FROM commands WHERE denotation = ?;";

    private final Connection con;
    private PreparedStatement stById;


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
        try (PreparedStatement st_cmd = con.prepareStatement(sqlGetAll))
        {
            ResultSet rs = st_cmd.executeQuery();
            return makeResult(rs);
        }  catch (NullPointerException | SQLException e) {
            System.out.println(getClass().getSimpleName() + " error: " + e.getMessage());
        }
        return null;
    }

    public void setCommands(Animal animal) throws SQLException
    {
        try (PreparedStatement st_cmd = con.prepareStatement(sqlInsCmd);
             PreparedStatement st_del = con.prepareStatement(sqlDelete))
        {
            // удаляем старые данные из cmd_list
            st_del.setInt(1, animal.getId());
            st_del.executeUpdate();
            // добавляем новые
            for (String s : animal.getCommands())
            {
                st_cmd.setInt(1, animal.getId());
                st_cmd.setString(2, s);
                st_cmd.executeUpdate();
            }
        } catch (NullPointerException | SQLException e) {
            throw new SQLException("setCommands() error: " + e.getMessage());
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
        stById = closeStatement(stById);
    }

    private void prepareStatements()
    {
        try {
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
