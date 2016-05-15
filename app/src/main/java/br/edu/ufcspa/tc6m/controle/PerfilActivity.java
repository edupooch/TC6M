package br.edu.ufcspa.tc6m.controle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Paciente;

public class PerfilActivity extends AppCompatActivity {
    private FormularioHelper helper;
    private PerfilActivity activity = this;
    private Paciente paciente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        iniciaComponentes();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void iniciaComponentes() {
        escreveDados();
        ////////////////////////////////////////////////////////////////////////////////////////////
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnPlayTeste);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProValoresBasais = new Intent(PerfilActivity.this, ValoresBasaisActivity.class);
                intentVaiProValoresBasais.putExtra("paciente", paciente);
                startActivity(intentVaiProValoresBasais);
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.btAntigos);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProListaTestes = new Intent(PerfilActivity.this, ListaTestesActivity.class);
                intentVaiProListaTestes.putExtra("paciente", paciente);
                startActivity(intentVaiProListaTestes);
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

    }

    private void escreveDados() {

        Intent intent = getIntent();
        paciente = (Paciente) intent.getSerializableExtra("paciente");

        TextView textoPeso = (TextView) findViewById(R.id.text_peso);
        TextView textoAltura = (TextView) findViewById(R.id.text_altura);
        TextView textoIMC = (TextView) findViewById(R.id.text_imc);
        TextView textoTelefone = (TextView) findViewById(R.id.text_telefone);
        TextView textoEmail = (TextView) findViewById(R.id.text_email);
        TextView textoData = (TextView) findViewById(R.id.text_data);
        TextView textoObs = (TextView) findViewById(R.id.text_obs);

        activity.setTitle(paciente.getNome());
        textoPeso.setText(String.format(Locale.US, "%.2f kg", paciente.getMassa()));
        textoAltura.setText(String.format(Locale.US, "%.0f cm", paciente.getEstatura()));
        textoIMC.setText(String.format(Locale.US, "IMC %.2f", paciente.getMassa() / Math.pow(paciente.getEstatura() / 100, 2)));
        textoTelefone.setText(paciente.getTelefone());
        textoEmail.setText(paciente.getEmail());
        textoData.setText(paciente.getDataNascimento());
        String obs = "Obs: " + paciente.getObs();
        textoObs.setText(obs);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_perfil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_editar:
                Intent intentVaiProFormulario = new Intent(PerfilActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("paciente", paciente);
                startActivity(intentVaiProFormulario);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
