package br.edu.ufcspa.tc6m.controle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.dao.PacienteDAO;
import br.edu.ufcspa.tc6m.modelo.Paciente;

public class PreTesteActivity extends AppCompatActivity {

    Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_teste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        paciente = (Paciente) getIntent().getSerializableExtra("paciente");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_preteste, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check_preteste:
                Intent intentVaiProValoresBasais = new Intent(PreTesteActivity.this, ValoresBasaisActivity.class);
                intentVaiProValoresBasais.putExtra("paciente", paciente);
                startActivity(intentVaiProValoresBasais);
        }
        return super.onOptionsItemSelected(item);
    }





}
