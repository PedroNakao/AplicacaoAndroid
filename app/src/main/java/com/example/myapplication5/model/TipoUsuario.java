package com.example.myapplication5.model;

import androidx.annotation.NonNull;

import java.time.LocalTime;

public class TipoUsuario {
    private int id;
    private String nome;
    private int horasPermitda;

    public int getHorasPermitda() {
        return horasPermitda;
    }

    public void setHorasPermitda(int horasPermitda) {
        this.horasPermitda = horasPermitda;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nome;
    }
}
