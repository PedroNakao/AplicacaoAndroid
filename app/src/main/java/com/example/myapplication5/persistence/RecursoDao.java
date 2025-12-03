package com.example.myapplication5.persistence;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication5.model.Recurso;
import com.example.myapplication5.model.TipoUsuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecursoDao implements ICRUDDao<Recurso>, IRecursoDao{
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase dataBase;

    public RecursoDao(Context context) {
        this.context = context;
    }
    @Override
    public void insert(Recurso recurso) throws SQLException {
        ContentValues contentValues = getContentValues(recurso);
        dataBase.insert("recurso", null, contentValues);

    }

    @Override
    public int update(Recurso recurso) throws SQLException {
        ContentValues contentValues = getContentValues(recurso);
        return dataBase.update("recurso", contentValues,
                "id = " + recurso.getId(), null);
    }

    @Override
    public void delete(Recurso recurso) throws SQLException {
        dataBase.delete("recurso", "id = "+ recurso.getId(),
                null);
    }
    @SuppressLint("Range")
    @Override
    public Recurso findOne(Recurso recurso) throws SQLException {
        String sql = "SELECT id, nome, descricao, manutencao FROM recurso WHERE id = "+recurso.getId();
        Cursor cursor = dataBase.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            recurso.setId(cursor.getInt(cursor.getColumnIndex("id")));
            recurso.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            recurso.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            recurso.setEmManutencao(cursor.getInt(cursor.getColumnIndex("manutencao"))==1);
        }
        cursor.close();
        return recurso;
    }
    @SuppressLint("Range")
    @Override
    public List<Recurso> findAll() throws SQLException {
        List<Recurso> recursos = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, manutencao FROM recurso";
        Cursor cursor = dataBase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recurso recurso = new Recurso();
            recurso.setId(cursor.getInt(cursor.getColumnIndex("id")));
            recurso.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            recurso.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            recurso.setEmManutencao(cursor.getInt(cursor.getColumnIndex("manutencao"))==1);

            cursor.moveToNext();
            recursos.add(recurso);
        }
        cursor.close();
        return recursos;
    }

    private static ContentValues getContentValues(Recurso recurso) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", recurso.getId());
        contentValues.put("nome",recurso.getNome());
        contentValues.put("descricao", recurso.getDescricao());

        return contentValues;
    }

    @Override
    public RecursoDao open() throws SQLException {
        gDao = new GenericDao(context);
        dataBase = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }
}
