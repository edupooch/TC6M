package br.edu.ufcspa.tc6m.controle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPref = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);


        findViewById(R.id.layout_selecionar_equacoes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findViewById(R.id.lista_formulas).getVisibility() == View.VISIBLE) {
                    findViewById(R.id.lista_formulas).setVisibility(View.GONE);
                } else {
                    carregaLista();
                }

            }
        });


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
        listaFormulas.setVisibility(View.VISIBLE);
        FormulasAdapter adapter = new FormulasAdapter(this, new ListaFormulas().getFormulas(), sharedPref);
        listaFormulas.setAdapter(adapter);
        listaFormulas.getCount();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferencias, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavUtils.navigateUpFromSameTask(this);
        return super.onOptionsItemSelected(item);
    }

}


