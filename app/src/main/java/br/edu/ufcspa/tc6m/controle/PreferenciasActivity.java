package br.edu.ufcspa.tc6m.controle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.adapter.FormulasAdapter;
import br.edu.ufcspa.tc6m.dao.PreferenciasDAO;
import br.edu.ufcspa.tc6m.formulas.ListaFormulas;
import br.edu.ufcspa.tc6m.modelo.Formula;

public class PreferenciasActivity extends AppCompatActivity {

    private ListView listaFormulas;
    private com.shawnlin.numberpicker.NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        carregaLista();

        numberPicker = (com.shawnlin.numberpicker.NumberPicker) findViewById(R.id.number_picker_tamanho_volta);
        numberPicker.setValue(30);
        final TextView alerta = (TextView) findViewById(R.id.text_alerta_volta);
        numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                if (view.getValue() < 30) {
                    alerta.setVisibility(View.VISIBLE);
                } else {
                    alerta.setVisibility(View.GONE);
                }
            }
        });

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


