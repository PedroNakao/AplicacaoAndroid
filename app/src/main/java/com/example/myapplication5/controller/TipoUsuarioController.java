package com.example.myapplication5.controller;

import com.example.myapplication5.model.TipoUsuario;
import com.example.myapplication5.persistence.TipoUsuarioDao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class TipoUsuarioController implements IController<TipoUsuario>{
    private TipoUsuarioDao tpDao;
    public TipoUsuarioController(TipoUsuarioDao tpDao) {
    this.tpDao = tpDao;
    }
    @Override
    public void inserir(TipoUsuario tipoUsuario) throws SQLException {
        if (tpDao.open() == null) {
            tpDao.open();
        }
        tpDao.insert(tipoUsuario);
        tpDao.close();
    }

    @Override
    public void modificar(TipoUsuario tipoUsuario) throws SQLException {
        if (tpDao.open() == null) {
            tpDao.open();
        }
        tpDao.update(tipoUsuario);
        tpDao.close();
    }

    @Override
    public void excluir(TipoUsuario tipoUsuario) throws SQLException {
        if (tpDao.open() == null) {
            tpDao.open();
        }
        tpDao.delete(tipoUsuario);
        tpDao.close();
    }

    @Override
    public TipoUsuario buscar(TipoUsuario tipoUsuario) throws SQLException {
        if (tpDao.open() == null){
            tpDao.open();
        }
        return tpDao.findOne(tipoUsuario);
    }

    @Override
    public List<TipoUsuario> listar() throws SQLException {
        if (tpDao.open() == null) {
            tpDao.open();
        }
        return tpDao.findAll();
    }
}
