package br.edu.ufcspa.tc6m.controle;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import br.edu.ufcspa.tc6m.R;

public class CronometroActivity extends AppCompatActivity {

    private FloatingActionButton btAdicionarVolta;
    private Chronometer crono;
    private LinearLayout layoutFrase;
    private LinearLayout layoutDados;
    private TextView textDistancia;
    private TextView textFrase;
    private TextView textDadosMinuto;
    private TextView btSalvar;
    ////////////////////////////////
    private long miliseconds;
    private int metros;
    private int volta;
    ///////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);
        iniciaComponentes();


    }

    private void iniciaComponentes() {
        ////////////////////////////////////////////////////////////////////////////////////////////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        ////////////////////////////////////////////////////////////////////////////////////////////
        textFrase = (TextView) findViewById(R.id.textApoio);
        textDadosMinuto = (TextView) findViewById(R.id.textDadosMinuto);
        btSalvar = (TextView) findViewById(R.id.btSalvarDados);
        textDistancia = (TextView) findViewById(R.id.textMetros);

        ////////////////////////////////////////////////////////////////////////////////////////////
        miliseconds = 0;
        metros = 0;
        volta = 20;
        crono = (Chronometer) findViewById(R.id.cronometro);
        crono.setBase(SystemClock.elapsedRealtime() - miliseconds);
        crono.start();
        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                miliseconds = SystemClock.elapsedRealtime() - crono.getBase();

                if (chronometer.getText().toString().equals("01:00")) {
                    umMinuto();
                }
                if (chronometer.getText().toString().equals("02:00")) {
                    doisMinutos();
                }
                if (chronometer.getText().toString().equals("03:00")) {
                    tresMinutos();
                }
                if (chronometer.getText().toString().equals("04:00")) {
                    quatroMinutos();
                }
                if (chronometer.getText().toString().equals("05:00")) {
                    cincoMinutos();
                }

                if (chronometer.getText().toString().equals("06:00")) {
                    chronometer.stop();
                    seisMinutos();
                }


            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////

        btAdicionarVolta = (FloatingActionButton) findViewById(R.id.btAdicionarVolta);
        btAdicionarVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplication(), "" + Integer.parseInt(crono.getText().toString().replace(":", "")), Toast.LENGTH_LONG).show();
                metros += volta;
                textDistancia.setText(String.valueOf(metros));
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        layoutFrase = (LinearLayout) findViewById(R.id.layoutFrase);
        layoutFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutFrase.setVisibility(View.GONE);
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        layoutDados = (LinearLayout) findViewById(R.id.layoutDados);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutDados.setVisibility(View.GONE);
            }
        });
    }


    //////////////////////////////////////////MINUTOS///////////////////////////////////////////////
    private void umMinuto() {
        textFrase.setText(R.string.frase1);
        textDadosMinuto.setText(R.string.dados1);
        layoutFrase.setVisibility(View.VISIBLE);
    }


    private void doisMinutos() {
        textFrase.setText(R.string.frase2);
        textDadosMinuto.setText(R.string.dados2);
        layoutFrase.setVisibility(View.VISIBLE);
    }

    private void tresMinutos() {
        textFrase.setText(R.string.frase3);
        textDadosMinuto.setText(R.string.dados3);
        layoutFrase.setVisibility(View.VISIBLE);
    }


    private void quatroMinutos() {
        textFrase.setText(R.string.frase4);
        textDadosMinuto.setText(R.string.dados4);
        layoutFrase.setVisibility(View.VISIBLE);
    }


    private void cincoMinutos() {
        textFrase.setText(R.string.frase5);
        textDadosMinuto.setText(R.string.dados5);
        layoutFrase.setVisibility(View.VISIBLE);
    }


    private void seisMinutos() {
        textDadosMinuto.setText(R.string.dados6);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
}
