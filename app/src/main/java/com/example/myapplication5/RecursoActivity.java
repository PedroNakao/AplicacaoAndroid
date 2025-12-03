package com.example.myapplication5;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication5.controller.RecursoController;
import com.example.myapplication5.controller.TipoUsuarioController;
import com.example.myapplication5.model.Recurso;
import com.example.myapplication5.model.TipoUsuario;
import com.example.myapplication5.persistence.RecursoDao;
import com.example.myapplication5.persistence.TipoUsuarioDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class RecursoActivity extends AppCompatActivity {
    private Button btnBuscarRecurso;
    private Button btnCriarRecurso;
    private Button btnMostrarRecurso;
    private Button btnAtualizarRecurso;
    private Button btnRemoverRecurso;
    private EditText etIDRecurso;
    private EditText etNomeRecurso;
    private EditText etDescricaoRecurso;
    private CheckBox cbEmManutencaoRecurso;
    private TextView tvListaRecurso;
    private RecursoController recursoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recurso);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnBuscarRecurso = findViewById(R.id.btnBuscarRecurso);
        btnCriarRecurso = findViewById(R.id.btnCriarRecurso);
        btnMostrarRecurso = findViewById(R.id.btnMostrarRecurso);
        btnAtualizarRecurso = findViewById(R.id.btnAtualizarRecurso);
        btnRemoverRecurso = findViewById(R.id.btnRemoverRecurso);


        etNomeRecurso = findViewById(R.id.etNomeRecurso);
        etIDRecurso = findViewById(R.id.etIDRecurso);
        etDescricaoRecurso = findViewById(R.id.etDescricaoRecurso);
        cbEmManutencaoRecurso = findViewById(R.id.cbEmManutencaoRecurso);
        tvListaRecurso = findViewById(R.id.tvListaRecurso); // Inicializa a TextView
        tvListaRecurso.setMovementMethod(new ScrollingMovementMethod());


        recursoController = new RecursoController(new RecursoDao(this));


        btnCriarRecurso.setOnClickListener(click -> acaoInserir());
        btnAtualizarRecurso.setOnClickListener(click -> acaoModificar());
        btnRemoverRecurso.setOnClickListener(click -> acaoExcluir());
        btnBuscarRecurso.setOnClickListener(click -> acaoBuscar());
        btnMostrarRecurso.setOnClickListener(click -> acaoListar());
    }

    // --- MÉTODOS DE MANIPULAÇÃO DE DADOS ---




    private Recurso montaRecurso() {
        Recurso recurso = new Recurso();


        String idText = etIDRecurso.getText().toString();
        if (!idText.isEmpty()) {
            recurso.setId(Integer.parseInt(idText));
        }

        recurso.setNome(etNomeRecurso.getText().toString());
        recurso.setDescricao(etDescricaoRecurso.getText().toString());


        recurso.setEmManutencao(cbEmManutencaoRecurso.isChecked());

        return recurso;
    }



    private void preencheCampos(Recurso recurso) {
        etIDRecurso.setText(String.valueOf(recurso.getId()));
        etNomeRecurso.setText(recurso.getNome());
        etDescricaoRecurso.setText(recurso.getDescricao());


        cbEmManutencaoRecurso.setChecked(recurso.getisEmManutencao());
    }


    private void limpaCampos() {
        etIDRecurso.setText("");
        etNomeRecurso.setText("");
        etDescricaoRecurso.setText("");
        cbEmManutencaoRecurso.setChecked(false);
    }

    // --- MÉTODOS DE AÇÃO ---

    private void acaoInserir() {
        Recurso recurso = montaRecurso();
        try {
            recursoController.inserir(recurso);
            Toast.makeText(this, "Recurso inserido com sucesso!", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("SQLException", Objects.requireNonNull(e.getMessage()));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID deve ser um número válido!", Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoModificar() {
        Recurso recurso = montaRecurso();
        try {
            recursoController.modificar(recurso);
            Toast.makeText(this, "Recurso modificado com sucesso!", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("SQLException", Objects.requireNonNull(e.getMessage()));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID deve ser um número válido!", Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoExcluir() {
        Recurso recurso = montaRecurso();
        try {
            recursoController.excluir(recurso);
            Toast.makeText(this, "Recurso excluído com sucesso!", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("SQLException", Objects.requireNonNull(e.getMessage()));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID deve ser um número válido!", Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoBuscar() {
        Recurso recurso = montaRecurso();
        try {
            recurso = recursoController.buscar(recurso);
            if (recurso.getNome() != null) {
                preencheCampos(recurso);
                Toast.makeText(this, "Recurso encontrado!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Recurso não encontrado.",Toast.LENGTH_SHORT).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("SQLException", Objects.requireNonNull(e.getMessage()));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID deve ser um número válido!", Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Recurso> listaRecursos = recursoController.listar();
            StringBuffer buffer = new StringBuffer();
            for (Recurso r : listaRecursos) {
                buffer.append(r.toString());
                buffer.append("\n");
            }
            tvListaRecurso.setText(buffer.toString());
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
