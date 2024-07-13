package model.dao;

/**
 * Свойства работающих с БД классов - очистка памяти после себя.
 */
public interface IDbCloseable {

    void close();

}
