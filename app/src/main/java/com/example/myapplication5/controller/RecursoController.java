package com.example.myapplication5.controller;

import com.example.myapplication5.model.Recurso;
import com.example.myapplication5.persistence.RecursoDao;
import com.example.myapplication5.persistence.TipoUsuarioDao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class RecursoController implements IController<Recurso>{
    private RecursoDao rDao;
    public RecursoController(RecursoDao rDao) {
        this.rDao = rDao;
    }
    @Override
    public void inserir(Recurso recurso) throws SQLException {
        if (rDao.open() == null) {
            rDao.open();
        }
        rDao.insert(recurso);
        rDao.close();
    }

    @Override
    public void modificar(Recurso recurso) throws SQLException {
        if (rDao.open() == null) {
            rDao.open();
        }
        rDao.update(recurso);
        rDao.close();
    }

    @Override
    public void excluir(Recurso recurso) throws SQLException {
        if (rDao.open() == null) {
            rDao.open();
        }
        rDao.delete(recurso);
        rDao.close();
    }

    @Override
    public Recurso buscar(Recurso recurso) throws SQLException {
        if (rDao.open() == null){
            rDao.open();
        }
        return rDao.findOne(recurso);
    }

    @Override
    public List<Recurso> listar() throws SQLException {
        if (rDao.open() == null) {
            rDao.open();
        }
        return rDao.findAll();
    }
}
