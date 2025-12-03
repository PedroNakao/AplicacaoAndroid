package com.example.myapplication5.persistence;

import java.sql.SQLException;

public interface ISalaDao {
    public SalaDao open() throws SQLException;
    public void close();
}
