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

import com.example.myapplication5.controller.TipoUsuarioController;
import com.example.myapplication5.model.TipoUsuario;
import com.example.myapplication5.persistence.TipoUsuarioDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class TipoUsuarioActivity extends AppCompatActivity {
    private Button btnBuscarTipoUsuario;
    private Button btnCriarTipoUsuario;
    private Button btnMostrarTipoUsuario;
    private Button btnAtualizarTipoUsuario;
    private Button btnRemoverTipoUsuario;

    private EditText etNomeTipoUsuario;
    private EditText etIDTipoUsuario;
    private EditText etHorasTipoUsuario;

    private TextView tvListaTipoUsuario;

    private TipoUsuarioController tipoUsuarioController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tipo_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBuscarTipoUsuario = findViewById(R.id.btnBuscarTipoUsuario);
        btnCriarTipoUsuario = findViewById(R.id.btnCriarTipoUsuario);
        btnMostrarTipoUsuario = findViewById(R.id.btnMostrarTipoUsuario);
        btnAtualizarTipoUsuario = findViewById(R.id.btnAtualizarTipoUsuario);
        btnRemoverTipoUsuario = findViewById(R.id.btnRemoverTipoUsuario);

        etNomeTipoUsuario = findViewById(R.id.etNomeTipoUsuario);
        etIDTipoUsuario = findViewById(R.id.etIDTipoUsuario);
        etHorasTipoUsuario = findViewById(R.id.etHorasTipoUsuario);
        tvListaTipoUsuario = findViewById(R.id.tvListaTipoUsuario);
        tvListaTipoUsuario.setMovementMethod(new ScrollingMovementMethod());

        tipoUsuarioController = new TipoUsuarioController(new TipoUsuarioDao(this));

        btnCriarTipoUsuario.setOnClickListener(click -> acaoInserir());
        btnAtualizarTipoUsuario.setOnClickListener(click -> acaoModificar());
        btnRemoverTipoUsuario.setOnClickListener(click -> acaoExcluir());
        btnBuscarTipoUsuario.setOnClickListener(click -> acaoBuscar());
        btnMostrarTipoUsuario.setOnClickListener(click -> acaoListar());
    }

    private TipoUsuario montaTipoUsario() {
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(Integer.parseInt(etIDTipoUsuario.getText().toString()));
        tipoUsuario.setNome(etNomeTipoUsuario.getText().toString());
        tipoUsuario.setHorasPermitda(Integer.parseInt(etHorasTipoUsuario.getText().toString()));

        return tipoUsuario;
    }

    private void preencheCampos(TipoUsuario tipoUsuario) {
        etIDTipoUsuario.setText(String.valueOf(tipoUsuario.getId()));
        etNomeTipoUsuario.setText(tipoUsuario.getNome());
        etHorasTipoUsuario.setText(tipoUsuario.getHorasPermitda());
    }

    private void limpaCampos() {
        etIDTipoUsuario.setText("");
        etNomeTipoUsuario.setText("");
        etHorasTipoUsuario.setText("");
    }
    private void acaoInserir() {
        TipoUsuario tipoUsuario = montaTipoUsario();
        try {
            tipoUsuarioController.inserir(tipoUsuario);
            //Toast.makeText(this, getString(R.string.prof_insert), Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("SQLException", Objects.requireNonNull(e.getMessage()));
        }
        limpaCampos();
    }

    private void acaoModificar() {
        TipoUsuario tipoUsuario = montaTipoUsario();
        try {
            tipoUsuarioController.modificar(tipoUsuario);
            //Toast.makeText(this, getString(R.string.prof_update), Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("SQLException", Objects.requireNonNull(e.getMessage()));
        }
        limpaCampos();
    }

    private void acaoExcluir() {
        TipoUsuario tipoUsuario = montaTipoUsario();
        try {
            tipoUsuarioController.excluir(tipoUsuario);
            //Toast.makeText(this, getString(R.string.prof_delete), Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("SQLException", Objects.requireNonNull(e.getMessage()));
        }
        limpaCampos();
    }

    private void acaoBuscar() {
        TipoUsuario tipoUsuario = montaTipoUsario();
        try {
            tipoUsuario = tipoUsuarioController.buscar(tipoUsuario);
            if (tipoUsuario.getNome() != null) {
                preencheCampos(tipoUsuario);
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
            List<TipoUsuario> listaTpUsuarios = tipoUsuarioController.listar();
            StringBuffer buffer = new StringBuffer();
            for (TipoUsuario p : listaTpUsuarios) {
                buffer.append(p.toString());
                buffer.append("\n");
            }
            tvListaTipoUsuario.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("SQLException", Objects.requireNonNull(e.getMessage()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
