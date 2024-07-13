package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TypesDB implements IDbCloseable {

    static final String sqlGetAll =  "SELECT id, denotation FROM types;";
    static final String sqlInsAnm = "INSERT INTO typ_list (anm_id, typ_id) VALUES (?, (SELECT id FROM types WHERE denotation = ?));";

    private final Connection con;

    public TypesDB(Connection con)
    {
        this.con = con;
    }

    public ArrayList<String> getTypesList() {
        try (PreparedStatement st_cmd = con.prepareStatement(sqlGetAll);)
        {
            ResultSet rs = st_cmd.executeQuery();
            return makeResult(rs);

        } catch (NullPointerException | SQLException e) {
            System.out.println(getClass().getSimpleName() + " error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Добавляет ссылку на животное (animals) в таблицу typ_list
     * @param animal_id Идентификатор животного в таблицу animals
     * @param typ       Вид (Cat, Dog, Hamster, Horse, Camel, Donkey)
     */
    public void insertAnimal(int animal_id, String typ) throws SQLException
    {
        try (PreparedStatement st_cmd = con.prepareStatement(sqlInsAnm);)
        {
            st_cmd.setInt(1, animal_id);
            st_cmd.setString(2, typ);
            st_cmd.executeUpdate();
        }
    }

    private ArrayList<String> makeResult(ResultSet rs) throws SQLException {
        ArrayList<String> res = new ArrayList<>();
        while (rs.next())
        {
            res.add(rs.getString("denotation"));
        }
        return res;
    }


    @Override
    public void close() {

    }
}
