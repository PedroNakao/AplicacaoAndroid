package com.example.myapplication5.controller;

import java.sql.SQLException;
import java.util.List;

public interface IController <T>{
    public void inserir(T t) throws SQLException;
    public void modificar(T t) throws SQLException;
    public void excluir(T T) throws SQLException;
    public T buscar(T t) throws SQLException;
    public List<T> listar() throws SQLException;
}
