package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SexDB implements IDbCloseable {

    static final String sqlGetSex =  "SELECT id, denotation FROM sex;";

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
