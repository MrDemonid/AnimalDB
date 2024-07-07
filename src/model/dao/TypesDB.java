package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TypesDB implements IDbCloseable {

    static final String sqlGetAll =  "SELECT id, denotation FROM anm_type;";

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
