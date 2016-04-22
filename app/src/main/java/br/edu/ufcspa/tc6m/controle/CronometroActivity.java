package br.edu.ufcspa.tc6m.controle;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
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
    private ImageButton btFechar;
    ////////////////////////////////
    private long miliseconds;
    private int metros;
    private int volta;
    private int tempo;
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
        btFechar = (ImageButton) findViewById(R.id.btFechar);
        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
                tempo = Integer.parseInt(crono.getText().toString().replace(":", ""));//TRANSFORMA O RELÓGIO EM UM INTEIRO (01:32 = 132)

                switch (tempo) {
                    case 100:
                        umMinuto();
                        break;
                    case 110:
                        someFrase();
                        break;
                    case 200:
                        doisMinutos();
                        break;
                    case 210:
                        someFrase();
                        break;
                    case 300:
                        tresMinutos();
                        break;
                    case 310:
                        someFrase();
                        break;
                    case 400:
                        quatroMinutos();
                        break;
                    case 410:
                        someFrase();
                        break;
                    case 500:
                        cincoMinutos();
                        break;
                    case 510:
                        someFrase();
                        break;
                    case 600:
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

    private void someFrase() {
        layoutFrase.setVisibility(View.GONE);
    }


    //////////////////////////////////////////MINUTOS///////////////////////////////////////////////
    private void umMinuto() {
        textFrase.setText(R.string.frase1);
        textDadosMinuto.setText(R.string.dados1);
        layoutFrase.setVisibility(View.VISIBLE);
        layoutDados.setVisibility(View.VISIBLE);
    }


    private void doisMinutos() {
        textFrase.setText(R.string.frase2);
        textDadosMinuto.setText(R.string.dados2);
        layoutFrase.setVisibility(View.VISIBLE);
        layoutDados.setVisibility(View.VISIBLE);
    }

    private void tresMinutos() {
        textFrase.setText(R.string.frase3);
        textDadosMinuto.setText(R.string.dados3);
        layoutFrase.setVisibility(View.VISIBLE);
        layoutDados.setVisibility(View.VISIBLE);
    }


    private void quatroMinutos() {
        textFrase.setText(R.string.frase4);
        textDadosMinuto.setText(R.string.dados4);
        layoutFrase.setVisibility(View.VISIBLE);
        layoutDados.setVisibility(View.VISIBLE);
    }


    private void cincoMinutos() {
        textFrase.setText(R.string.frase5);
        textDadosMinuto.setText(R.string.dados5);
        layoutFrase.setVisibility(View.VISIBLE);
        layoutDados.setVisibility(View.VISIBLE);
    }


    private void seisMinutos() {
        textDadosMinuto.setText(R.string.dados6);
        layoutDados.setVisibility(View.VISIBLE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.concluido));
        builder.setMessage(getString(R.string.dialog_parar));
        builder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.dialog_abandonar));
        builder.setPositiveButton(getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
