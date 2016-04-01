package br.edu.ufcspa.tc6m.controle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Paciente;

public class PerfilActivity extends AppCompatActivity {
    private FormularioHelper helper;
    private PerfilActivity activity = this;


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
        ////////////////////////////////////////////////////////////////////////////////////////////
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnPlayTeste);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.btAntigos);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        escreveDados();
    }

    private void escreveDados() {

        Intent intent = getIntent();
        Paciente paciente = (Paciente) intent.getSerializableExtra("paciente");

        TextView textoPeso = (TextView) findViewById(R.id.text_peso);
        TextView textoAltura = (TextView) findViewById(R.id.text_altura);
        TextView textoIMC = (TextView) findViewById(R.id.text_imc);
        TextView textoTelefone = (TextView) findViewById(R.id.text_telefone);
        TextView textoEmail = (TextView) findViewById(R.id.text_email);
        TextView textoData = (TextView) findViewById(R.id.text_data);
        TextView textoObs = (TextView) findViewById(R.id.text_obs);

        activity.setTitle(paciente.getNome());
        textoPeso.setText(String.format("%.2f kg",paciente.getPeso()));
        textoAltura.setText(String.format("%.0f cm",paciente.getAltura()));
        textoIMC.setText(String.format("IMC %.2f",paciente.getPeso() / Math.pow(paciente.getAltura()/100, 2)));
        textoTelefone.setText(paciente.getTelefone());
        textoEmail.setText(paciente.getEmail());
        textoData.setText(paciente.getDataNascimento());
        textoObs.setText("Obs: " + paciente.getObs());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_perfil, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
