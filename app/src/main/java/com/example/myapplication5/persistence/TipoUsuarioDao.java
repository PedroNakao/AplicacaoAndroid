package com.example.myapplication5.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication5.model.TipoUsuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TipoUsuarioDao implements ICRUDDao<TipoUsuario>, ITipoUsuarioDao{
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase dataBase;

    public TipoUsuarioDao(Context context) {
        this.context = context;
    }

    @Override
    public void insert(TipoUsuario tipoUsuario) throws SQLException {
        ContentValues contentValues = getContentValues(tipoUsuario);
        dataBase.insert("tipo_usuario", null, contentValues);
    }

    @Override
    public int update(TipoUsuario tipoUsuario) throws SQLException {
        ContentValues contentValues = getContentValues(tipoUsuario);
        return dataBase.update("tipo_usuario", contentValues,
                "id = " + tipoUsuario.getId(), null);
    }

    @Override
    public void delete(TipoUsuario tipoUsuario) throws SQLException {
        dataBase.delete("tipo_usuario", "id = "+ tipoUsuario.getId(),
                null);
    }

    @SuppressLint("Range")
    @Override
    public TipoUsuario findOne(TipoUsuario tipoUsuario) throws SQLException {
        String sql = "SELECT id, nome, horas FROM tipo_usuario WHERE id = "+tipoUsuario.getId();
        Cursor cursor = dataBase.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            tipoUsuario.setId(cursor.getInt(cursor.getColumnIndex("id")));
            tipoUsuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            tipoUsuario.setHorasPermitda(cursor.getInt(cursor.getColumnIndex("horas")));
        }
        cursor.close();
        return tipoUsuario;
    }

    @SuppressLint("Range")
    @Override
    public List<TipoUsuario> findAll() throws SQLException {
        List<TipoUsuario> tipoUsuarios = new ArrayList<>();
        String sql = "SELECT id, nome, horas FROM tipo_usuario";
        Cursor cursor = dataBase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TipoUsuario tipoUsuario = new TipoUsuario();
            tipoUsuario.setId(cursor.getInt(cursor.getColumnIndex("id")));
            tipoUsuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            tipoUsuario.setHorasPermitda(cursor.getInt(cursor.getColumnIndex("horas")));

            cursor.moveToNext();
            tipoUsuarios.add(tipoUsuario);
        }
        cursor.close();
        return tipoUsuarios;
    }

    private static ContentValues getContentValues(TipoUsuario tipoUsuario) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", tipoUsuario.getId());
        contentValues.put("nome",tipoUsuario.getNome());
        contentValues.put("horas", tipoUsuario.getHorasPermitda());

        return contentValues;
    }

    @Override
    public TipoUsuarioDao open() throws SQLException {
        gDao = new GenericDao(context);
        dataBase = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }
}
