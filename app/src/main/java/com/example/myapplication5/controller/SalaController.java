package com.example.myapplication5.controller;

import com.example.myapplication5.model.Sala;
import com.example.myapplication5.model.TipoUsuario;
import com.example.myapplication5.persistence.SalaDao;
import com.example.myapplication5.persistence.TipoUsuarioDao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class SalaController implements IController<Sala>{
    private SalaDao salaDao;
    public SalaController(SalaDao salaDao) {
        this.salaDao = salaDao;
    }
    @Override
    public void inserir(Sala sala) throws SQLException {
        if (salaDao.open() == null) {
            salaDao.open();
        }
        salaDao.insert(sala);
        salaDao.close();
    }

    @Override
    public void modificar(Sala sala) throws SQLException {
        if (salaDao.open() == null) {
            salaDao.open();
        }
        salaDao.update(sala);
        salaDao.close();
    }

    @Override
    public void excluir(Sala sala) throws SQLException {
        if (salaDao.open() == null) {
            salaDao.open();
        }
        salaDao.delete(sala);
        salaDao.close();
    }

    @Override
    public Sala buscar(Sala sala) throws SQLException {
        if (salaDao.open() == null){
            salaDao.open();
        }
        return salaDao.findOne(sala);
    }

    @Override
    public List<Sala> listar() throws SQLException {
        if (salaDao.open() == null) {
            salaDao.open();
        }
        return salaDao.findAll();
    }
}