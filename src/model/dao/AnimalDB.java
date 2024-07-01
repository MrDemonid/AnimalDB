package model.dao;

import animal.*;
import animal.base.Animal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class AnimalDB implements IDbCloseable {

    static final String sqlUpdate =     "UPDATE anm_data SET nick=?, birth_day=?, comments=? WHERE id=(SELECT data_id FROM animals WHERE id=?);";
    static final String sqlDelete =     "DELETE FROM cmd_list WHERE anm_id=?;";
    static final String sqlInsData =    "INSERT INTO anm_data (nick, birth_day, comments) VALUES (?, ?, ?);";
    static final String sqlInsAnim =    "INSERT INTO animals (type_id, data_id) VALUES ((SELECT id FROM anm_type WHERE denotation = ?), ?);";
    static final String sqlInsCmd =     "INSERT INTO cmd_list (anm_id, cmd_id) SELECT ?, id FROM cmd_info WHERE denotation = ?;";


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


    /**
     * Обновляет данные в БД
     * @param animal объект с новыми данными
     */
    public void updateAnimal(Animal animal)
    {
        try (PreparedStatement st_data = con.prepareStatement(sqlUpdate);
             PreparedStatement st_del = con.prepareStatement(sqlDelete);)
        {
            con.setAutoCommit(false);
            // обновляем таблицу anm_data
            st_data.setString(1, animal.getNickName());
            st_data.setDate(2, new java.sql.Date(animal.getBirthDay().getTime()));
            st_data.setString(3, animal.getComments());
            st_data.setInt(4, animal.getId());
            st_data.executeUpdate();

            // удаляем старые данные из cmd_list
            st_del.setInt(1, animal.getId());
            st_del.executeUpdate();
            // добавляем команды
            setCommands(animal);
            con.commit();
        } catch (NullPointerException |SQLException e)
        {
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

    /**
     * Добавлет в БД новое животное
     * @param animal Объетк для добавления, которому присваивается новый ID.
     */
    public void addAnimal(Animal animal)
    {
        try (PreparedStatement st_data = con.prepareStatement(sqlInsData, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement st_index = con.prepareStatement(sqlInsAnim, Statement.RETURN_GENERATED_KEYS);)
        {
            con.setAutoCommit(false);
            // добавляем в таблицу anm_data
            st_data.setString(1, animal.getNickName());
            st_data.setDate(2, new java.sql.Date(animal.getBirthDay().getTime()));
            st_data.setString(3, animal.getComments());
            int last_id = runStatementWIthKey(st_data);
            // добавляем в таблицу animals
            st_index.setString(1, animal.getClass().getSimpleName());
            st_index.setInt(2, last_id);
            last_id = runStatementWIthKey(st_index);

            // добавляем команды
            animal.setId(last_id);
            setCommands(animal);

            con.commit();

        } catch (NullPointerException | SQLException e) {
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

    /**
     * Добавляет в БД список команд животного
     * @param animal живность
     */
    private void setCommands(Animal animal) throws SQLException
    {
        try (PreparedStatement st_cmd = con.prepareStatement(sqlInsCmd);)
        {
            for (String s : animal.getCommands())
            {
                st_cmd.setInt(1, animal.getId());
                st_cmd.setString(2, s);
                st_cmd.executeUpdate();
            }
        } catch (NullPointerException | SQLException e) {
            throw new SQLException("Can't add commands to 'cmd_list' table!");
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
