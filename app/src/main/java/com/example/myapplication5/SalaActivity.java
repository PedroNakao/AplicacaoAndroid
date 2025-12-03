package com.example.myapplication5;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication5.controller.SalaController;
import com.example.myapplication5.model.Sala;
import com.example.myapplication5.persistence.SalaDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


public class SalaActivity extends AppCompatActivity {

    private Button btnBuscarSala;
    private Button btnCriarSala;
    private Button btnMostrarSala;
    private Button btnAtualizarSala;
    private Button btnRemoverSala;

    private EditText etNomeSala;
    private EditText etIDSala;
    private EditText etCapacidadeSala;

    private TextView tvListaSala;

    private SalaController salaController;

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


        btnBuscarSala = findViewById(R.id.btnBuscarSala);
        btnCriarSala = findViewById(R.id.btnCriarSala);
        btnMostrarSala = findViewById(R.id.btnMostrarSala);
        btnAtualizarSala = findViewById(R.id.btnAtualizarSala);
        btnRemoverSala = findViewById(R.id.btnRemoverSala);

        etNomeSala = findViewById(R.id.etNomeSala);
        etIDSala = findViewById(R.id.etIDSala);
        etCapacidadeSala = findViewById(R.id.etCapacidadeSala);
        tvListaSala = findViewById(R.id.tvListaSala);
        tvListaSala.setMovementMethod(new ScrollingMovementMethod());

        salaController = new SalaController(new SalaDao(this));

        btnCriarSala.setOnClickListener(click -> acaoInserir());
        btnAtualizarSala.setOnClickListener(click -> acaoModificar());
        btnRemoverSala.setOnClickListener(click -> acaoExcluir());
        btnBuscarSala.setOnClickListener(click -> acaoBuscar());
        btnMostrarSala.setOnClickListener(click -> acaoListar());
    }


    private Sala montaSala() {
        Sala tipoUsuario = new Sala();
        tipoUsuario.setId(Integer.parseInt(etIDSala.getText().toString()));
        tipoUsuario.setNome(etNomeSala.getText().toString());
        tipoUsuario.setCapacidade(Integer.parseInt(etCapacidadeSala.getText().toString()));

        return tipoUsuario;
    }

    private void preencheCampos(Sala sala) {
        etIDSala.setText(String.valueOf(sala.getId()));
        etNomeSala.setText(sala.getNome());
        etCapacidadeSala.setText(sala.getCapacidade());
    }

    private void limpaCampos() {
        etIDSala.setText("");
        etNomeSala.setText("");
        etCapacidadeSala.setText("");
    }
    private void acaoInserir() {
        Sala sala = montaSala();
        try {
            salaController.inserir(sala);
            //Toast.makeText(this, getString(R.string.prof_insert), Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("SQLException", Objects.requireNonNull(e.getMessage()));
        }
        limpaCampos();
    }

    private void acaoModificar() {
        Sala sala = montaSala();
        try {
            salaController.modificar(sala);
            //Toast.makeText(this, getString(R.string.prof_update), Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("SQLException", Objects.requireNonNull(e.getMessage()));
        }
        limpaCampos();
    }

    private void acaoExcluir() {
        Sala sala = montaSala();
        try {
            salaController.excluir(sala);
            //Toast.makeText(this, getString(R.string.prof_delete), Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("SQLException", Objects.requireNonNull(e.getMessage()));
        }
        limpaCampos();
    }

    private void acaoBuscar() {
        Sala sala = montaSala();
        try {
            sala = salaController.buscar(sala);
            if (sala.getNome() != null) {
                preencheCampos(sala);
            } else {
                //Toast.makeText(this, getString(R.string.prof_nao_encontrado),Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("SQLException", Objects.requireNonNull(e.getMessage()));
        }
    }

    private void acaoListar() {
        try {
            List<Sala> listaTpUsuarios = salaController.listar();
            StringBuffer buffer = new StringBuffer();
            for (Sala p : listaTpUsuarios) {
                buffer.append(p.toString());
                buffer.append("\n");
            }
            tvListaSala.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("SQLException", Objects.requireNonNull(e.getMessage()));
        }
    }
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_principal, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){
            int id = item.getItemId();
            if (id == R.id.menu_inicial) {
                Intent i = new Intent(this, MainActivity.class);
                this.startActivity(i);
                this.finish();
                return true;
            }

            if (id == R.id.menu_usuario) {
                Intent i = new Intent(this, UsuarioActivity.class);
                this.startActivity(i);
                this.finish();
                return true;
            }

            if (id == R.id.menu_reserva) {
                Intent i = new Intent(this, ReservaActivity.class);
                this.startActivity(i);
                this.finish();
                return true;
            }

            if (id == R.id.menu_sala) {
                Intent i = new Intent(this, SalaActivity.class);
                this.startActivity(i);
                this.finish();
                return true;
            }
            if (id == R.id.menu_recurso) {
                Intent i = new Intent(this, RecursoActivity.class);
                this.startActivity(i);
                this.finish();
                return true;
            }

            if (id == R.id.menu_tipo_usuario) {
                Intent i = new Intent(this, TipoUsuarioActivity.class);
                this.startActivity(i);
                this.finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

}