package br.edu.ufcspa.tc6m.controle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.adapter.FormulasAdapter;
import br.edu.ufcspa.tc6m.dao.PreferenciasDAO;
import br.edu.ufcspa.tc6m.formulas.ListaFormulas;
import br.edu.ufcspa.tc6m.modelo.Formula;

public class PreferenciasActivity extends AppCompatActivity {

    private ListView listaFormulas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        carregaLista();
    }

    private void carregaLista() {

        listaFormulas = (ListView) findViewById(R.id.lista_formulas);


        PreferenciasDAO dao = new PreferenciasDAO(this);
        //List<Formula> formulas = dao.buscaFormulasEscolhidas();


        FormulasAdapter adapter = new FormulasAdapter(this, new ListaFormulas().getFormulas());
        listaFormulas.setAdapter(adapter);


        dao.close();
    }
}


