package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
Перед созданием пакета, следует отключить автоматическое завершение транзакции
после выполнения каждого запроса setAutoCommit(false).
Это приведёт к тому, что завершение или откат транзакции придётся выполнять явно
вызывая методы commit() или rollback().
Вызов rollback() будет приводить к откату всего пакета SQL операторов.
 */

public class CommandsDAO {

    static final String sqlGetAll = "SELECT id, denotation FROM cmd_info;";
    static final String sqlGetById = "SELECT denotation FROM cmd_info JOIN cmd_list ON cmd_list.cmd_id = cmd_info.id JOIN animals ON animals.id = cmd_list.anm_id WHERE animals.id = ?;";

    Connection con;
    PreparedStatement stAll;
    PreparedStatement stById;

    public CommandsDAO(Connection con)
    {
        this.con = con;
        prepareStatements();
    }

    /**
     * Возвращает список всех доступных команд, или null при ошибке
     */
    public ArrayList<String> getCommandsList()
    {
        ArrayList<String> res = new ArrayList<>();
        try {
            ResultSet rs = stAll.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("denotation"));
            }
        } catch (NullPointerException | SQLException e) {
            System.out.println("cmd error: " + e.getMessage());
            return null;
        }
        return res;
    }

    /**
     * Возвращает список команд животного
     * @param id идентификатор животного (в таблице animals)
     */
    public ArrayList<String> getCommandsById(int id)
    {
        ArrayList<String> res = new ArrayList<>();
        try {
            stById.setInt(1, id);
            ResultSet rs = stById.executeQuery();
            while (rs.next())
            {
                res.add(rs.getString("denotation"));
            }
        } catch (NullPointerException | SQLException e) {
            return null;
        }
        return res;
    }

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

/*
connection.setAutoCommit(false);
try (Statement stmt = connection.createStatement()) { //(1)
    for (int i = 1; i <= SIZE; i++) {
        stmt.addBatch("INSERT INTO book (title) VALUES ('" + "JDBC Insert Example: " + i + "')"); //(2)
        if (i % BATCH_SIZE == 0 || i == SIZE) {
            try {
                int[] result = stmt.executeBatch(); //(3)
                connection.commit();
            } catch (BatchUpdateException ex) {
                Log(ex);
                connection.rollback();
            }
        }
    }
}
Шаги:

Cоздаём объект Statement;
Cобираем пакет запросов с помощью метода void addBatch(String SQL);
Посылаем пакет серверу БД вызвав метод executeBatch().
Метод executeBatch() возвращает массив обработанных строк.

Преимущество
Использование объекта Statement даёт возможность собирать в один пакет разные
SQL операторы INSERT, UPDATE, DELETE.

Недостаток
Каждый SQL запрос проверяется и компилируется БД, что приводит к увеличению времени выполнения.
 */

/*
connection.setAutoCommit(false);
try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO book (title) VALUES (?)")) { //(1)
    for (int i = 1; i <= SIZE; i++) {
        pstmt.setString(1, "JDBC Insert Example: " + i); //(2)
        pstmt.addBatch(); //(3)
        if (i % BATCH_SIZE == 0 || i == SIZE) {
            try {
                int[] result = pstmt.executeBatch(); //(4)
                connection.commit();
            } catch (BatchUpdateException ex) {
                Log(ex);
                connection.rollback();
            }
        }
    }
}

Cоздаём объект PreparedStatement передав в качестве параметра SQL запрос;
Устанавливаем все параметры, указанные в запросе;
Собираем пакет запросов с помощью метода void addBatch();
Посылаем пакет серверу БД вызвав метод executeBatch().

Шаги 3) и 4) такие же, как и для Statement, единственное отличие — это addBatch() без параметров.

Преимущество
SQL запрос компилируется и оптимизируется базой данных один раз, после чего его можно использовать
многократно, задавая различные значения параметров. И это серьёзное преимущество, так как не затрачивается
время на компиляцию каждого последующего запроса.

Недостаток
Использование интерфейса PreparedStatement не предусматривает возможности собирать в один пакет разные
SQL операторы (INSERT, UPDATE, DELETE) подобно как для Statement, а только какой-то один.
*/