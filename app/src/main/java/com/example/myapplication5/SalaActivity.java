package com.example.myapplication5;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SalaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sala);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_inicial){
            Intent i = new Intent(this, MainActivity.class);
            this.startActivity(i);
            this.finish();
            return true;
        }

        if (id == R.id.menu_usuario){
            Intent i = new Intent(this, UsuarioActivity.class);
            this.startActivity(i);
            this.finish();
            return true;
        }

        if (id == R.id.menu_reserva){
            Intent i = new Intent(this, ReservaActivity.class);
            this.startActivity(i);
            this.finish();
            return true;
        }

        if (id == R.id.menu_sala){
            Intent i = new Intent(this, SalaActivity.class);
            this.startActivity(i);
            this.finish();
            return true;
        }
        if (id == R.id.menu_recurso){
            Intent i = new Intent(this,RecursoActivity.class);
            this.startActivity(i);
            this.finish();
            return true;
        }

        if (id == R.id.menu_tipo_usuario){
            Intent i = new Intent(this,TipoUsuarioActivity.class);
            this.startActivity(i);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}