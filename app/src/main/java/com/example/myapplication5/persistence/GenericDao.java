package com.example.myapplication5.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDao extends SQLiteOpenHelper {
    private static final String DATABASE = "RESERVA.DB";
    private static final int DATABASE_VER = 1;
    private static final String CREATE_TABLE_TIPOUSER =
            "CREATE TABLE tipo_usuario ( "+
                    "id INT NOT NULL PRIMARY KEY, "+
                    "nome VARCHAR(100) NOT NULL, "+
                    "horas INT NOT NULL);";
    private static final String CREATE_TABLE_USER =
            "CREATE TABLE usuario ( "+
                    "id INT NOT NULL PRIMARY KEY, "+
                    "nome VARCHAR(100) NOT NULL, "+
                    "email VARCHAR(100) NOT NULL,"+
                    "id_tipo_usuario INT NOT NULL, "+
                    "FOREIGN KEY (id_tipo_usuario) REFERENCES tipo_usuario(id))";
    private static final String CREATE_TABLE_RECURSO =
            "CREATE TABLE recurso( " +
                    "idRecurso INT NOT NULL, " +
                    "nome VARCHAR(40) NOT NULL," +
                    "descricao VARCHAR(255) NOT NULL," +
                    "manutencao BIT NOT NULL," +
                    "PRIMARY KEY (idRecurso)" +
                    ")";
    public GenericDao(Context context) {
        super(context, DATABASE, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_TIPOUSER);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_RECURSO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int antigaVersao, int novaVersao) {
        if (novaVersao > antigaVersao) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tipo_usuario");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS usuario");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Recurso");
            onCreate(sqLiteDatabase);
        }
    }
}
