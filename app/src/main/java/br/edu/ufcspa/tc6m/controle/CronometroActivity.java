package br.edu.ufcspa.tc6m.controle;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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


import java.util.TimerTask;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Teste;

public class CronometroActivity extends AppCompatActivity {
    //CRONOMETRO
    private Chronometer crono;
    //LAYOUTS
    private LinearLayout layoutFrase;
    private LinearLayout[] layoutsDados = new LinearLayout[6];
    private int[] layoutsDadosXML = {R.id.layoutDados1, R.id.layoutDados2, R.id.layoutDados3, R.id.layoutDados4, R.id.layoutDados5, R.id.layoutDados6};
    private TextView[] botoesSalvar = new TextView[6];
    private int[] botoesSalvarXML = {R.id.btSalvarDados1, R.id.btSalvarDados2, R.id.btSalvarDados3, R.id.btSalvarDados4, R.id.btSalvarDados5, R.id.btSalvarDados6,};
    private int[] frasesId = {R.string.frase1, R.string.frase2, R.string.frase3, R.string.frase4, R.string.frase5};
    //TEXTOS
    private TextView textDistancia;
    private TextView textFrase;
    //BOTOES
    private ImageButton btFechar;
    private FloatingActionButton btAdicionarVolta;
    //VALORES
    private long miliseconds;
    private int metros;
    private int volta;
    private int tempo;

    private TesteHelper helper;
    private Teste teste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);
        iniciaCronometro();
        iniciaComponentes();
    }

    private void iniciaCronometro() {
        metros = 0;//pegar das preferências do usuário
        volta = 20;//pegar das preferências do usuário

        crono = (Chronometer) findViewById(R.id.cronometro);
        crono.setBase(SystemClock.elapsedRealtime());
        crono.start();

        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                miliseconds = SystemClock.elapsedRealtime() - crono.getBase();
                tempo = Integer.parseInt(crono.getText().toString().replace(":", "")); //TRANSFORMA O RELÓGIO EM UM INTEIRO (01:32 = 132)

                switch (tempo) {
                    case 100:
                        mostraCampos(0);
                        break;
                    case 110:
                        someFrase(); //mudar isso para um metodo com timer
                        break;
                    case 200:
                        mostraCampos(1);;
                        break;
                    case 210:
                        someFrase();
                        break;
                    case 300:
                        mostraCampos(2);
                        break;
                    case 310:
                        someFrase();
                        break;
                    case 400:
                        mostraCampos(3);
                        break;
                    case 410:
                        someFrase();
                        break;
                    case 500:
                        mostraCampos(4);;
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
    }

    private void seisMinutos() {
        mostraCampos(5);
        
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


    private void iniciaComponentes() {
        helper = new TesteHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);


        TextView textNome = (TextView) findViewById(R.id.textNomePaciente);
        textDistancia = (TextView) findViewById(R.id.textMetros);
        btFechar = (ImageButton) findViewById(R.id.btFechar);


        btAdicionarVolta = (FloatingActionButton) findViewById(R.id.btAdicionarVolta);
        layoutFrase = (LinearLayout) findViewById(R.id.layoutFrase);
        ////////////////////////////////////////////////////////////////////////////////////////////
        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        btAdicionarVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metros += volta;
                textDistancia.setText(String.valueOf(metros));

            }
        });
        //FRASE DE APOIO////////////////////////////////////////////////////////////////////////////
        textFrase = (TextView) findViewById(R.id.textApoio);
        layoutFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutFrase.setVisibility(View.GONE);
            }
        });

    }

    private void someFrase() {
        layoutFrase.setVisibility(View.GONE);
    }


    //////////////////////////////////////////MINUTOS///////////////////////////////////////////////


    private void mostraCampos(final int minuto){
        layoutFrase.setVisibility(View.VISIBLE);
        textFrase.setText(frasesId[minuto]);
        layoutsDados[minuto] = (LinearLayout) findViewById(layoutsDadosXML[minuto]);
        layoutsDados[minuto].setVisibility(View.VISIBLE);

        botoesSalvar[minuto] = (TextView) findViewById(botoesSalvarXML[minuto]);
        botoesSalvar[minuto].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutsDados[minuto].setVisibility(View.GONE);
            }
        });
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
