package br.edu.ufcspa.tc6m.controle;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Paciente;
import br.edu.ufcspa.tc6m.modelo.Teste;

public class CronometroActivity extends AppCompatActivity {
    //CRONOMETRO
    private Chronometer crono;
    //LAYOUTS
    private int[] layoutsDadosXML;
    private LinearLayout[] layoutsDados;
    private LinearLayout layoutFrase;
    //TEXTOS
    private int[] frasesId;
    private TextView textDistancia;
    private TextView textFrase;
    private TextView textParadas;
    //BOTOES
    private int[] botoesSalvarXML;
    private TextView[] botoesSalvar;
    //VALORES
    private long miliseconds;
    private int metros;
    private int volta;
    private int tempo;
    private int fase;
    private int dpTotal;//distancia depois de adicionar valor restante no fim
    private boolean parado;
    //TESTE
    private TesteHelper helper;
    private Teste teste;

    public CronometroActivity() {
        botoesSalvarXML = new int[]{R.id.btSalvarDados1, R.id.btSalvarDados2, R.id.btSalvarDados3, R.id.btSalvarDados4, R.id.btSalvarDados5, R.id.btSalvarDados6,};
        frasesId = new int[]{R.string.frase1, R.string.frase2, R.string.frase3, R.string.frase4, R.string.frase5};
        layoutsDadosXML = new int[]{R.id.layoutDados1, R.id.layoutDados2, R.id.layoutDados3, R.id.layoutDados4, R.id.layoutDados5, R.id.layoutDados6};
        layoutsDados = new LinearLayout[6];
        botoesSalvar = new TextView[6];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);
        iniciaComponentes();

    }


    private void iniciaComponentes() {


        Button btConfirma = (Button) findViewById(R.id.btConfirma);
        btConfirma.setVisibility(View.VISIBLE);
        btConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiProValoresFinais = new Intent(CronometroActivity.this, ValoresFinaisActivity.class);
                intentVaiProValoresFinais.putExtra("teste", teste);
                startActivity(intentVaiProValoresFinais);
                finish();
            }
        });

        iniciaCronometro();

        Intent intent = getIntent();
        teste = (Teste) intent.getSerializableExtra("teste");
        helper = new TesteHelper(this, teste);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        ImageView sublinhado = (ImageView) findViewById(R.id.sublinhado);

        TextView textNome = (TextView) findViewById(R.id.textNomePaciente);
        textNome.setText(teste.getPaciente().getNome());
        sublinhado.setMaxWidth(textNome.getWidth());
        sublinhado.setMinimumWidth(textNome.getWidth());

        textDistancia = (TextView) findViewById(R.id.textMetros);


        //BOTAO DA VOLTA
        FloatingActionButton btAdicionarVolta = (FloatingActionButton) findViewById(R.id.btAdicionarVolta);
        btAdicionarVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metros += volta;
                textDistancia.setText(String.valueOf(metros));
                teste.setVoltas(fase, teste.getVoltas(fase) + volta); //Salva metros percorridos separados por minuto
                Toast.makeText(getApplicationContext(), "Volta atual: " + teste.getVoltas(fase), Toast.LENGTH_SHORT).show();


            }
        });
        //FRASE DE APOIO
        layoutFrase = (LinearLayout) findViewById(R.id.layoutFrase);
        textFrase = (TextView) findViewById(R.id.textApoio);
        layoutFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutFrase.setVisibility(View.GONE);
            }
        });

        //BOTAO PARAR
        textParadas = (TextView) findViewById(R.id.textParadas);
        final ImageButton btParar = (ImageButton) findViewById(R.id.btParar);

        parado = false;
        btParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parado){
                    //função do botao play
                    parado = false;
                    btParar.setBackgroundResource(R.drawable.icon_pause);
                }else{
                    //função do botao pause
                    teste.setnParadas(teste.getnParadas() + 1);
                    parado = true;
                    String strParadas = String.valueOf(teste.getnParadas());
                    textParadas.setText(strParadas);
                    btParar.setBackgroundResource(R.drawable.icon_play);
                }

            }
        });


        //BOTAO FECHAR
        ImageButton btFechar = (ImageButton) findViewById(R.id.btFechar);
        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setCancelable(false);
                builder.setMessage(getString(R.string.dialog_abandonar_voltar));
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
        });
    }

    private void iniciaCronometro() {
        fase = 0;
        metros = 0;
        volta = 20; //pegar das preferências do usuário

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
                         someFrase();
                        break;
                    case 200:
                        mostraCampos(1);
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
                        mostraCampos(4);
                        break;
                    case 510:
                        someFrase();
                        break;
                    case 600:
                        chronometer.stop();
                        mostraCampos(5);
                        seisMinutos();


                }
            }
        });
    }

    private void seisMinutos() {

        //ALERTA DE MANDE O PACIENTE PARAR
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

        //TROCA DE LAYOUT
        RelativeLayout layoutCronometro = (RelativeLayout) findViewById(R.id.layoutCronometro);
        layoutCronometro.setVisibility(View.GONE);

        RelativeLayout layoutBarraInferior = (RelativeLayout) findViewById(R.id.layoutBotoes);
        layoutBarraInferior.setVisibility(View.GONE);

        Button btConfirma = (Button) findViewById(R.id.btConfirma);
        btConfirma.setVisibility(View.VISIBLE);

        LinearLayout layoutDp = (LinearLayout) findViewById(R.id.layoutDp);
        layoutDp.setVisibility(View.VISIBLE);

        TextView textDistanciaFinal = (TextView) findViewById(R.id.textDistanciaFinal);
        textDistanciaFinal.setText(textDistancia.getText().toString());

        for (int i = 0; i < 6; i++) {
            layoutsDados[i].setVisibility(View.VISIBLE);
            botoesSalvar[i].setVisibility(View.GONE);
        }

        //BOTAO FINALIZA ACTIVITY
        btConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiProValoresFinais = new Intent(CronometroActivity.this, ValoresFinaisActivity.class);
                intentVaiProValoresFinais.putExtra("teste", teste);
                startActivity(intentVaiProValoresFinais);
                finish();
            }
        });

    }

    private void someFrase() {
        layoutFrase.setVisibility(View.GONE);
    }

    private void mostraCampos(final int minuto) {
        if (minuto < 6) fase = minuto + 1;

        if (minuto < 5) { // frase do sexto minuto é exibida num AlertDialog
            layoutFrase.setVisibility(View.VISIBLE);
            textFrase.setText(frasesId[minuto]);
        }

        layoutsDados[minuto] = (LinearLayout) findViewById(layoutsDadosXML[minuto]);
        layoutsDados[minuto].setVisibility(View.VISIBLE);

        botoesSalvar[minuto] = (TextView) findViewById(botoesSalvarXML[minuto]);
        botoesSalvar[minuto].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teste = helper.pegaDadosFromFields(minuto + 1); //minuto 1 = 0 aqui e no helper é 1
                layoutsDados[minuto].setVisibility(View.GONE);
            }
        });
    }

    //DIALOGO DO BOTÃO VOLTAR
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.dialog_abandonar_voltar));
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
