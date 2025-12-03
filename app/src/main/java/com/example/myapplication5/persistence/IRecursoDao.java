package com.example.myapplication5.persistence;

import java.sql.SQLException;

public interface IRecursoDao {
    public RecursoDao open() throws SQLException;
    public void close();
}
