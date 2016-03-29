package br.edu.ufcspa.tc6m.controle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import br.edu.ufcspa.tc6m.dao.PacienteDAO;
import br.edu.ufcspa.tc6m.modelo.Paciente;
import br.edu.ufcspa.tc6m.R;

public class FormularioActivity extends AppCompatActivity {


    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        iniciaComponentes();


    }


    private void iniciaComponentes() {

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Paciente paciente = (Paciente) intent.getSerializableExtra("paciente");
        if (paciente != null) {
            helper.preencheFormulário(paciente);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario:
                if (helper.validateFields()) {

                    Paciente paciente = helper.pegaPaciente();
                    PacienteDAO dao = new PacienteDAO(this);

                    if (paciente.getId() == null) {
                        dao.insere(paciente);
                        Toast.makeText(getApplicationContext(), "Paciente " + paciente.getNome() + " adicionado!", Toast.LENGTH_LONG).show();

                    } else {
                        dao.altera(paciente);
                        Toast.makeText(getApplicationContext(), "Paciente " + paciente.getNome() + " alterado!", Toast.LENGTH_LONG).show();
                    }
                    dao.close();


                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();
                }
                break;

        }


        return super.onOptionsItemSelected(item);
    }

}
