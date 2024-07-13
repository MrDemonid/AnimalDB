package model.dao;

import animal.*;
import animal.base.Animal;
import animal.base.AnimalSex;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class AnimalDB implements IDbCloseable {

    static final String sqlUpdate =     "UPDATE anm_data SET nick=?, birth_day=?, comments=? WHERE id=(SELECT data_id FROM animals WHERE id=?);";
    static final String sqlInsData =    "INSERT INTO anm_data (nick, birth_day, comments) VALUES (?, ?, ?);";
    static final String sqlInsAnim =    "INSERT INTO animals (type_id, data_id) VALUES ((SELECT id FROM anm_type WHERE denotation = ?), ?);";


    static final String sqlGetAll =     "SELECT animals.id, animals.nick, animals.birth_day, types.denotation AS 'type', sex.denotation AS 'sex' " +
                                        "FROM animals " +
                                        "JOIN typ_list ON typ_list.anm_id = animals.id " +
                                        "JOIN sex_list ON sex_list.anm_id = animals.id " +
                                        "JOIN types ON typ_list.typ_id = types.id " +
                                        "JOIN sex ON sex_list.sex_id = sex.id " +
                                        "ORDER BY animals.id;";
    static final String sqlGetByBirthdays = "SELECT animals.id, animals.nick, animals.birth_day, types.denotation AS 'type', sex.denotation AS 'sex' " +
                                        "FROM animals " +
                                        "JOIN typ_list ON typ_list.anm_id = animals.id " +
                                        "JOIN sex_list ON sex_list.anm_id = animals.id " +
                                        "JOIN types ON typ_list.typ_id = types.id " +
                                        "JOIN sex ON sex_list.sex_id = sex.id " +
                                        "WHERE animals.birth_day BETWEEN ? AND ? " +
                                        "ORDER BY animals.birth_day;";

    static final String sqlGetByType =  "SELECT animals.id, animals.nick, animals.birth_day, types.denotation AS 'type', sex.denotation AS 'sex' " +
                                        "FROM animals " +
                                        "JOIN typ_list ON typ_list.anm_id = animals.id " +
                                        "JOIN sex_list ON sex_list.anm_id = animals.id " +
                                        "JOIN types ON typ_list.typ_id = types.id " +
                                        "JOIN sex ON sex_list.sex_id = sex.id " +
                                        "WHERE types.denotation = ? " +
                                        "ORDER BY animals.id;";


    static final String sqlGetBySex =   "SELECT animals.id, animals.nick, animals.birth_day, types.denotation AS 'type', sex.denotation AS 'sex' " +
                                        "FROM animals " +
                                        "JOIN typ_list ON typ_list.anm_id = animals.id " +
                                        "JOIN sex_list ON sex_list.anm_id = animals.id " +
                                        "JOIN types ON typ_list.typ_id = types.id " +
                                        "JOIN sex ON sex_list.sex_id = sex.id " +
                                        "WHERE sex.denotation = ? " +
                                        "ORDER BY animals.id;";

    Connection con;

    PreparedStatement stGetAll;
    PreparedStatement stGetByBirthdays;
    PreparedStatement stGetByType;
    PreparedStatement stGetBySex;

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
            int id = rs.getInt("id");
            String nick = rs.getString("nick");
            Date date = rs.getDate("birth_day");
            String ssex = rs.getString("sex");
            String type = rs.getString("type");
            Animal animal = AnimalFactory.createAnimal(type, id, nick, date, AnimalSex.getSex(ssex));
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
     * Выборка животных по полу
     * @param sex пол животных ('male', 'female')
     */
    public ArrayList<Animal> getBySex(String sex)
    {
        try {
            stGetBySex.setString(1, sex);
            ResultSet rs = stGetBySex.executeQuery();
            return makeResult(rs);
        } catch (NullPointerException | SQLException e) {
            System.out.println(getClass().getSimpleName() + " error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Обновляет данные в БД (таблица anm_data)
     * @param animal объект с новыми данными
     */
    public void updateAnimal(Animal animal) throws SQLException
    {
        try (PreparedStatement st_data = con.prepareStatement(sqlUpdate);)
        {
            // обновляем таблицу anm_data
            st_data.setString(1, animal.getNickName());
            st_data.setDate(2, new java.sql.Date(animal.getBirthDay().getTime()));
            st_data.setString(3, animal.getComments());
            st_data.setInt(4, animal.getId());
            st_data.executeUpdate();

        } catch (NullPointerException | SQLException e) {
            throw new SQLException("updateAnimal() error: " + e.getMessage());
        }
    }

    /**
     * Добавлет в БД новое животное
     * @param animal Объетк для добавления, которому присваивается новый ID.
     */
    public void addAnimal(Animal animal) throws SQLException
    {
        try (PreparedStatement st_data = con.prepareStatement(sqlInsData, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement st_index = con.prepareStatement(sqlInsAnim, Statement.RETURN_GENERATED_KEYS);)
        {
            // добавляем в таблицу anm_data
            st_data.setString(1, animal.getNickName());
            st_data.setDate(2, new java.sql.Date(animal.getBirthDay().getTime()));
            st_data.setString(3, animal.getComments());
            int last_id = runStatementWIthKey(st_data);
            // добавляем в таблицу animals
            st_index.setString(1, animal.getClass().getSimpleName());
            st_index.setInt(2, last_id);
            last_id = runStatementWIthKey(st_index);
            animal.setId(last_id);

        } catch (NullPointerException | SQLException e) {
            throw new SQLException("addAnimal() error: " + e.getMessage());
        }
    }

    /**
     * Выполнение запроса, с возвратом сгенерированного ключа (для автоинкремента)
     */
    private int runStatementWIthKey(PreparedStatement stmt) throws SQLException
    {
        stmt.executeUpdate();
        try (ResultSet rs = stmt.getGeneratedKeys();)
        {
            if (!rs.next())
                throw new SQLException("Not found id.");
            return rs.getInt(1);
        }
    }



    @Override
    public void close()
    {
        stGetAll = closeStatement(stGetAll);
        stGetByBirthdays = closeStatement(stGetByBirthdays);
        stGetByType = closeStatement(stGetByType);
        stGetBySex = closeStatement(stGetBySex);
    }

    private void prepareStatements()
    {
        try {
            stGetAll = con.prepareStatement(sqlGetAll);
            stGetByBirthdays = con.prepareStatement(sqlGetByBirthdays);
            stGetByType = con.prepareStatement(sqlGetByType);
            stGetBySex = con.prepareStatement(sqlGetBySex);
        } catch (SQLException e) {
            System.out.println("Prepare error: " + e.getMessage());
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
