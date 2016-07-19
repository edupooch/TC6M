package br.edu.ufcspa.tc6m.controle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.adapter.FormulasAdapter;
import br.edu.ufcspa.tc6m.formulas.ListaFormulas;

public class PreferenciasActivity extends AppCompatActivity {

    public static final int TAMANHO_MINIMO_VOLTA = 30;
    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPref = getSharedPreferences("PREFERENCIAS",MODE_PRIVATE);

        carregaLista();

        com.shawnlin.numberpicker.NumberPicker numberPicker = (com.shawnlin.numberpicker.NumberPicker) findViewById(R.id.number_picker_tamanho_volta);
        numberPicker.setValue(sharedPref.getInt("TAMANHO_VOLTA", TAMANHO_MINIMO_VOLTA));


        final TextView alerta = (TextView) findViewById(R.id.text_alerta_volta);
        if (numberPicker.getValue() < TAMANHO_MINIMO_VOLTA) {
            alerta.setVisibility(View.VISIBLE);
        }

        numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("TAMANHO_VOLTA", view.getValue());
                editor.apply();

                //Mostra a rase de recomendação do tamanho da volta caso seja menor do que 30
                if (view.getValue() < TAMANHO_MINIMO_VOLTA) {
                    alerta.setVisibility(View.VISIBLE);
                } else {
                    alerta.setVisibility(View.GONE);
                }

            }
        });

    }

    private void carregaLista() {
        ListView listaFormulas = (ListView) findViewById(R.id.lista_formulas);
        FormulasAdapter adapter = new FormulasAdapter(this, new ListaFormulas().getFormulas(), sharedPref);
        listaFormulas.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_preferencias, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvar_preferencias:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}


