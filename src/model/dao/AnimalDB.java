package model.dao;

import animal.*;
import animal.base.Animal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class AnimalDB implements IDbCloseable {

    static final String sqlGetAll =     "SELECT animals.id, anm_data.nick, anm_data.birth_day, anm_type.denotation FROM anm_data " +
                                        "JOIN animals ON animals.data_id = anm_data.id " +
                                        "JOIN anm_type ON animals.type_id = anm_type.id;";
    static final String sqlGetByBirthdays = "SELECT animals.id,anm_data.nick, anm_data.birth_day, anm_type.denotation " +
                                        "FROM anm_data " +
                                        "JOIN animals ON animals.data_id = anm_data.id " +
                                        "JOIN anm_type ON animals.type_id = anm_type.id " +
                                        "WHERE anm_data.birth_day BETWEEN ? AND ?;";
    static final String sqlGetByType =  "SELECT animals.id, anm_data.nick, anm_data.birth_day, anm_type.denotation FROM anm_data " +
                                        "JOIN animals ON animals.data_id = anm_data.id " +
                                        "JOIN anm_type ON animals.type_id = anm_type.id " +
                                        "WHERE animals.type_id = (SELECT anm_type.id FROM anm_type WHERE denotation = ?);";
    static final String sqlGetById =    "SELECT animals.id, anm_data.nick, anm_data.birth_day, anm_type.denotation FROM anm_data " +
                                        "JOIN animals ON animals.data_id = anm_data.id " +
                                        "JOIN anm_type ON animals.type_id = anm_type.id " +
                                        "WHERE animals.id = ?;";

    Connection con;

    PreparedStatement stGetAll;
    PreparedStatement stGetByBirthdays;
    PreparedStatement stGetByType;
    PreparedStatement stGetById;

    public AnimalDB(Connection con)
    {
        this.con = con;
        prepareStatements();
    }

    private ArrayList<Animal> makeResult(ResultSet rs) throws SQLException
    {
        ArrayList<Animal> res = new ArrayList<>();
        while (rs.next())
        {
            Animal animal;
            int id = rs.getInt("id");
            String nick = rs.getString("nick");
            Date date = rs.getDate("birth_day");
            String type = rs.getString("denotation");
            switch (type)
            {
                case "Cat" -> animal = new Cat(id, nick, date);
                case "Dog" -> animal = new Dog(id, nick, date);
                case "Hamster" -> animal = new Hamster(id, nick, date);
                case "Horse" -> animal = new Horse(id, nick, date);
                case "Camel" -> animal = new Camel(id, nick, date);
                case "Donkey" -> animal = new Donkey(id, nick, date);
                default -> animal = null;
            }
            if (animal != null)
                res.add(animal);
        }
        return res;
    }

    /**
     * Возвращает список всех животных из БД
     */
    public ArrayList<Animal> getAll()
    {
        try {
            ResultSet rs = stGetAll.executeQuery();
            return makeResult(rs);
        }  catch (NullPointerException | SQLException e) {
            System.out.println(getClass().getSimpleName() + " error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Выборка животных из БД по их дню рождения
     * @param from от какой даты ищем
     * @param to   до какой даты ищем
     */
    public ArrayList<Animal> getByBirthdays(Date from, Date to)
    {
        try {
            stGetByBirthdays.setDate(1, new java.sql.Date(from.getTime()));
            stGetByBirthdays.setDate(2, new java.sql.Date(to.getTime()));
            ResultSet rs = stGetByBirthdays.executeQuery();
            return makeResult(rs);
        } catch (NullPointerException | SQLException e) {
            System.out.println(getClass().getSimpleName() + " error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Выборка животных из БД по их типу
     * @param type тип животных ('Cat', 'Dog', 'Hamster', 'Horse', 'Camel', 'Donkey')
     */
    public ArrayList<Animal> getByType(String type)
    {
        try {
            stGetByType.setString(1, type);
            ResultSet rs = stGetByType.executeQuery();
            return makeResult(rs);
        } catch (NullPointerException | SQLException e) {
            System.out.println(getClass().getSimpleName() + " error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Выборка животных по id
     * @param id уникальный идентификатор животного
     * @return в целях унификации возвращается массив, с единственным элементом (или null - если была ошибка)
     */
    public ArrayList<Animal> getById(int id)
    {
        try {
            stGetById.setInt(1, id);
            ResultSet rs = stGetById.executeQuery();
            return makeResult(rs);
        } catch (NullPointerException | SQLException e) {
            System.out.println(getClass().getSimpleName() + " error: " + e.getMessage());
            return null;
        }
    }


    public void updateAnimal(Animal animal)
    {

    }

    public void addAnimal(Animal animal)
    {
        try (PreparedStatement st_data = con.prepareStatement("INSERT INTO anm_data (nick, birth_day, comments) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
             PreparedStatement st_index = con.prepareStatement("INSERT INTO animals (type_id, data_id) VALUES ((SELECT id FROM anm_type WHERE denotation = ?), ?);", Statement.RETURN_GENERATED_KEYS);
             PreparedStatement st_cmd = con.prepareStatement("INSERT INTO cmd_list (anm_id, cmd_id) SELECT ?, id FROM cmd_info WHERE denotation = ?;");)
        {
            con.setAutoCommit(false);
            // добавляем в таблицу anm_data
            st_data.setString(1, animal.getNickName());
            st_data.setDate(2, new java.sql.Date(animal.getBirthDay().getTime()));
            st_data.setString(3, "");
            int last_id = runStatementWIthKey(st_data);

            // добавляем в таблицу animals
            st_index.setString(1, animal.getClass().getSimpleName());
            st_index.setInt(2, last_id);
            last_id = runStatementWIthKey(st_index);

            // добавляем команды
            AnimalCommands cmd = animal.getCommands();
            for (String s : cmd)
            {
                st_cmd.setInt(1, last_id);
                st_cmd.setString(2, s);
                st_cmd.executeUpdate();
            }
            con.commit();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ignored) {
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }

    private int runStatementWIthKey(PreparedStatement stmt) throws SQLException
    {
        try (ResultSet rs = stmt.getGeneratedKeys();)
        {
            stmt.executeUpdate();
            if (!rs.next())
                throw new SQLException("Not found id.");
            return rs.getInt(1);
        }
    }

    private int run_statement(PreparedStatement stmt)
    {
        int last_id = 0;
        ResultSet rs = null;
        try {
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next())
                last_id = rs.getInt(1);
        } catch (SQLException e) {
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
        return last_id;
    }



    @Override
    public void close()
    {
        stGetAll = closeStatement(stGetAll);
        stGetByBirthdays = closeStatement(stGetByBirthdays);
        stGetByType = closeStatement(stGetByType);
        stGetById = closeStatement(stGetById);
    }

    private void prepareStatements()
    {
        try {
            stGetAll = con.prepareStatement(sqlGetAll);
            stGetByBirthdays = con.prepareStatement(sqlGetByBirthdays);
            stGetByType = con.prepareStatement(sqlGetByType);
            stGetById = con.prepareStatement(sqlGetById);
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
