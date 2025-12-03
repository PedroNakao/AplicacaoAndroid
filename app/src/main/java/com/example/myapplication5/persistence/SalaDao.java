package com.example.myapplication5.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication5.model.Sala;
import com.example.myapplication5.model.TipoUsuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SalaDao implements ICRUDDao<Sala>, ISalaDao{
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase dataBase;

    public SalaDao(Context context) {
        this.context = context;
    }

    @Override
    public void insert(Sala sala) throws SQLException {
        ContentValues contentValues = getContentValues(sala);
        dataBase.insert("sala", null, contentValues);
    }

    @Override
    public int update(Sala sala) throws SQLException {
        ContentValues contentValues = getContentValues(sala);
        return dataBase.update("tipo_usuario", contentValues,
                "id = " + sala.getId(), null);
    }

    @Override
    public void delete(Sala sala) throws SQLException {
        dataBase.delete("tipo_usuario", "id = "+ sala.getId(),
                null);
    }

    @SuppressLint("Range")
    @Override
    public Sala findOne(Sala sala) throws SQLException {
        String sql = "SELECT id, nome, horas FROM tipo_usuario WHERE id = "+sala.getId();
        Cursor cursor = dataBase.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            sala.setId(cursor.getInt(cursor.getColumnIndex("id")));
            sala.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            sala.setCapacidade(cursor.getInt(cursor.getColumnIndex("capacidade")));
        }
        cursor.close();
        return sala;
    }

    @SuppressLint("Range")
    @Override
    public List<Sala> findAll() throws SQLException {
        List<Sala> salas = new ArrayList<>();
        String sql = "SELECT id, nome, horas FROM sala";
        Cursor cursor = dataBase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Sala sala = new Sala();
            sala.setId(cursor.getInt(cursor.getColumnIndex("id")));
            sala.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            sala.setCapacidade(cursor.getInt(cursor.getColumnIndex("capacidade")));

            cursor.moveToNext();
            salas.add(sala);
        }
        cursor.close();
        return salas;
    }

    private static ContentValues getContentValues(Sala sala) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", sala.getId());
        contentValues.put("nome", sala.getNome());
        contentValues.put("capacidade", sala.getCapacidade());

        return contentValues;
    }

    @Override
    public SalaDao open() throws SQLException {
        gDao = new GenericDao(context);
        dataBase = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }
}