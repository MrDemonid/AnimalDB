package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SexDB implements IDbCloseable {

    static final String sqlGetSex =  "SELECT id, denotation FROM sex;";
    static final String sqlInsSex =  "INSERT INTO sex_list (anm_id, sex_id) VALUES (?, (SELECT id FROM sex WHERE denotation = ?));";


    private final Connection con;

    public SexDB(Connection con)
    {
        this.con = con;
    }

    public ArrayList<String> getSexList() {
        try (PreparedStatement st_cmd = con.prepareStatement(sqlGetSex);)
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
     * @param sex       Пол животного (Male, Female, Unknown)
     */
    public void insertAnimal(int animal_id, String sex) throws SQLException
    {
        try (PreparedStatement st_cmd = con.prepareStatement(sqlInsSex))
        {
            st_cmd.setInt(1, animal_id);
            st_cmd.setString(2, sex);
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
