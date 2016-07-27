package br.edu.ufcspa.tc6m.controle;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Teste;
import br.edu.ufcspa.tc6m.modelo.Velocidade;

public class CronometroActivity extends AppCompatActivity {
    public static final int DURATION_TOTAL = 360000;
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
    private boolean parado;
    boolean temAlgumValor;
    //TESTE
    private TesteHelper helper;
    private Teste teste;
    private CircularProgressBar circularProgressBar;
    private boolean primeiraVez;//que clicou em parar
    private long milis;
    private Chronometer cronometroParadas;
    private int progresso;

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

        //Não deixa a tela apagar durante o teste
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        teste = (Teste) intent.getSerializableExtra("teste");
        helper = new TesteHelper(this, teste);

        verificaValores();
        iniciaComponentes();


    }

    private void barraDeProgresso(float progressoAtual) {
        circularProgressBar = (CircularProgressBar) findViewById(R.id.progressoCirculo);
        //Verificando o progresso atual, no caso de o método ser chamado após virar a tela
        circularProgressBar.setProgress(progressoAtual);
        float tempoPassado = (DURATION_TOTAL * progressoAtual / 100);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(circularProgressBar, "progress", 100);
        long duration = (long) (DURATION_TOTAL - tempoPassado);
        objectAnimator.setDuration(duration);
        //Interpolator null - animação linear
        objectAnimator.setInterpolator(null);
        objectAnimator.start();

    }

    private void verificaValores() {
        new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("VARIAVEIS_DO_PACIENTE_" +
                        teste.getPaciente().getId(), Context.MODE_PRIVATE);

                boolean fcDurante = sharedPreferences.getBoolean("durante_fc", true);
                boolean spDurante = sharedPreferences.getBoolean("durante_spo2", true);
                boolean fadDurante = sharedPreferences.getBoolean("durante_fadiga", true);
                boolean dispDurante = sharedPreferences.getBoolean("durante_dispneia", true);
                temAlgumValor = fcDurante || spDurante || dispDurante || fadDurante;
            }
        }).start();

    }


    private void iniciaComponentes() {

        barraDeProgresso(0);
        Button btConfirma = (Button) findViewById(R.id.btConfirma);
        //Essa declaração do botao é apenas para testes
        btConfirma.setVisibility(View.GONE);
        btConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dpVoltas = Integer.parseInt(textDistancia.getText().toString());
                teste.setDistanciaPercorrida(dpVoltas);
                //.......................SALVA O TEMPO PARADO.......................................//
                teste.setTempoParadas(cronometroParadas.getText().toString());

                Intent intentVaiProValoresFinais = new Intent(CronometroActivity.this, ValoresFinaisActivity.class);
                intentVaiProValoresFinais.putExtra("teste", teste);
                startActivity(intentVaiProValoresFinais);


                finish();

            }
        }); //apagar depois

        iniciaCronometro();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        TextView textNome = (TextView) findViewById(R.id.textNomePaciente);
        textNome.setText(teste.getPaciente().getNome());

        textDistancia = (TextView) findViewById(R.id.textMetros);
        teste.setUltimaVolta(System.currentTimeMillis());
        //BOTAO DA VOLTA
        FloatingActionButton btAdicionarVolta = (FloatingActionButton) findViewById(R.id.btAdicionarVolta);
        btAdicionarVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //Incrementa o valor da distancia percorrida total e roda na thread principal
                        //a atualização do valor no campo textDistancia
                        metros += volta;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textDistancia.setText(String.valueOf(metros));
                            }
                        });

                        //Salva metros percorridos separados por minuto
                        teste.setVoltas(fase, teste.getVoltas(fase) + volta);

                        //Salva velocidade em m/s na lista de Velocidades
                        float tempoDaVoltaSeg = (float) ((System.currentTimeMillis() - teste.getUltimaVolta()) / 1000);
                        float velocidade = volta / tempoDaVoltaSeg;
                        System.out.println("Velociddade" + velocidade + crono.getText().toString());


                        //Adiciona na lista de velocidades a velocidade e o tempo atual no cronometro para fazer o gráfico
                        teste.getVelocidades().add(new Velocidade(velocidade, crono.getText().toString()));
                        //Marca valor do tempo da ultima volta
                        teste.setUltimaVolta(System.currentTimeMillis());

                    }
                }).start();

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
        //CRONOMETRO DO TEMPO PARADO
        primeiraVez = true;
        parado = false;
        milis = 0;
        cronometroParadas = (Chronometer) findViewById(R.id.cronometroParadas);
        assert btParar != null;
        btParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parado) {
                    //função do botao play
                    parado = false;
                    btParar.setBackgroundResource(R.drawable.icon_pause);
                    milis = SystemClock.elapsedRealtime() - cronometroParadas.getBase();
                    cronometroParadas.stop();
                } else {
                    //função do botao pause
                    teste.setnParadas(teste.getnParadas() + 1);
                    parado = true;
                    String strParadas = String.valueOf(teste.getnParadas());
                    textParadas.setText(strParadas);
                    btParar.setBackgroundResource(R.drawable.icon_play);
                    cronometroParadas.setBase(SystemClock.elapsedRealtime() - milis);
                    cronometroParadas.start();

                    if (primeiraVez) {
                        primeiraVez = false;
                        findViewById(R.id.layoutCronometroParadas).setVisibility(View.VISIBLE);
                    }
                }

            }
        });

    }

    private void iniciaCronometro() {
        metros = fase = 0;

        //Pega o valor definido nas preferências para o tamanho da volta, default = 30m
        SharedPreferences sharedPref = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        volta = sharedPref.getInt("TAMANHO_VOLTA", PreferenciasActivity.TAMANHO_MINIMO_VOLTA);

        crono = (Chronometer) findViewById(R.id.cronometro);
        crono.setBase(SystemClock.elapsedRealtime());
        crono.start();

        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                // miliseconds = SystemClock.elapsedRealtime() - crono.getBase();
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
                if (tempo > 600) {
                    chronometer.stop();
                    mostraCampos(5);
                    seisMinutos();
                }
            }
        });
    }

    private void seisMinutos() {
        //..........................ALERTA DE MANDE O PACIENTE PARAR..............................//
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
        //.......................TROCA DE LAYOUT..................................................//
        findViewById(R.id.layoutCronometro).setVisibility(View.GONE);
        findViewById(R.id.layoutBotoes).setVisibility(View.GONE);


        Button btConfirma = (Button) findViewById(R.id.btConfirma);
        btConfirma.setVisibility(View.VISIBLE);

        findViewById(R.id.layoutDp).setVisibility(View.VISIBLE);

        final TextView textDistanciaFinal = (TextView) findViewById(R.id.textDistanciaFinal);
        textDistanciaFinal.setText(textDistancia.getText().toString());

        final EditText edTextDistanciaRestante = (EditText) findViewById(R.id.edTextDistanciaRestante);

        //.............................MOSTRA OS LAYOUTS DE DADOS DURANTE.........................//
        if (temAlgumValor) { //Verifica se o usuário selecionou algum valor de "durante"
            for (int i = 0; i < 6; i++) {
                System.out.println("erro em + " + i);
                findViewById(layoutsDadosXML[i]).setVisibility(View.VISIBLE);
//                layoutsDados[i].setVisibility(View.VISIBLE);
                findViewById(botoesSalvarXML[i]).setVisibility(View.GONE);
                //botoesSalvar[i].setVisibility(View.GONE);
            }
        }
        //............................BOTAO QUE FINALIZA ACTIVITY.................................//
        btConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dpVoltas = Integer.parseInt(textDistanciaFinal.getText().toString()); //voltas completas durante o teste
                int dpRestante = 0;
                if (!edTextDistanciaRestante.getText().toString().isEmpty()) {
                    dpRestante = Integer.parseInt(edTextDistanciaRestante.getText().toString()); //valor adicionado no final quando o paciente para
                    teste.setVoltas(5, teste.getVoltas(5) + dpRestante); //salva o restante da ultima volta(onde o paciente parou)
                    teste.setDistanciaPercorrida(dpVoltas + dpRestante);//salva a distancia percorrida total

                    //Salva a velocidade caso tenha distancia restante
                    float tempoDaVoltaSeg = (float) ((System.currentTimeMillis() - teste.getUltimaVolta()) / 1000);
                    float velocidade = dpRestante / tempoDaVoltaSeg;
                    teste.getVelocidades().add(new Velocidade(velocidade, crono.getText().toString()));
                }


                Intent intentVaiProValoresFinais = new Intent(CronometroActivity.this, ValoresFinaisActivity.class);
                intentVaiProValoresFinais.putExtra("teste", teste);
                startActivity(intentVaiProValoresFinais);
                finish();
            }
        });

        //.......................SALVA O TEMPO PARADO.......................................//
        teste.setTempoParadas(cronometroParadas.getText().toString());


    }

    private void someFrase() {
        layoutFrase.setVisibility(View.GONE);
    }


    private void mostraCampos(final int minuto) {
        if (minuto < 6) {
            fase = minuto + 1; // fase é usado para o setVoltas
        }

        if (minuto < 5) { // frase do sexto minuto é exibida num AlertDialog
            layoutFrase.setVisibility(View.VISIBLE);
            textFrase.setText(frasesId[minuto]);
        }


        //Só mostra o layout de valores caso pelo menos um deles esteja ativos nas preferências
        System.out.println("Tem algum valor:" + temAlgumValor);
        if (temAlgumValor) {

            //card para botar os valores do minuto que aparece
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
    }


    //DIALOGO DO BOTÃO VOLTAR
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.atencao_sair);
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


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //Salvar o tempo no cronometro quando vira a tela
        savedInstanceState.putLong("cronometro", crono.getBase());
        //Salva o progresso atual da barra, para retornar de onde parou
        savedInstanceState.putFloat("progresso", circularProgressBar.getProgress());

        savedInstanceState.putBoolean("ja_parou",cronometroParadas.getVisibility() == View.VISIBLE);
        savedInstanceState.putString("distancia", textDistancia.getText().toString());
        savedInstanceState.putString("paradas", textParadas.getText().toString());
        savedInstanceState.putLong("tempo_parado", cronometroParadas.getBase());

        super.onSaveInstanceState(savedInstanceState);
    }

    //onRestoreInstanceState
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Recuperar as informações perdidas após virar a tela
        crono.setBase(savedInstanceState.getLong("cronometro"));
        barraDeProgresso(savedInstanceState.getFloat("progresso"));
        textDistancia.setText(savedInstanceState.getString("distancia"));
        metros = Integer.parseInt(savedInstanceState.getString("distancia"));
        if (savedInstanceState.getBoolean("ja_parou")) {
            primeiraVez=false;
            cronometroParadas.setVisibility(View.VISIBLE);
            textParadas.setText(savedInstanceState.getString("paradas"));
            cronometroParadas.setBase(savedInstanceState.getLong("tempo_parado"));
        }

    }

}
