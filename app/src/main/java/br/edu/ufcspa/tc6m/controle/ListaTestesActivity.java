package br.edu.ufcspa.tc6m.controle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.dao.TesteDAO;
import br.edu.ufcspa.tc6m.modelo.Paciente;
import br.edu.ufcspa.tc6m.modelo.Teste;

public class ListaTestesActivity extends AppCompatActivity {

    private ListView listaTestes;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_testes);
//       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iniciaComponentes();


    }

    private void iniciaComponentes() {
        Intent intent = getIntent();
        paciente = (Paciente) intent.getSerializableExtra("paciente");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ///
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ///
        carregaLista();
    }

    private void carregaLista() {
        listaTestes = (ListView) findViewById(R.id.lista_testes);


        TesteDAO dao = new TesteDAO(this);
        List<Teste> testes = dao.buscaTestes(paciente);

        ArrayAdapter<Teste> adapter =
                new ArrayAdapter<Teste>(this, android.R.layout.simple_list_item_1, testes);
        listaTestes.setAdapter(adapter);
        dao.close();
    }


}
