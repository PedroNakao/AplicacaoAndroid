package com.example.myapplication5.persistence;

import java.sql.SQLException;

public interface ITipoUsuarioDao {
    public TipoUsuarioDao open() throws SQLException;
    public void close();
}
