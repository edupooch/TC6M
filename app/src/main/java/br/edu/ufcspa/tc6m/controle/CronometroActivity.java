package br.edu.ufcspa.tc6m.controle;

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
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import br.edu.ufcspa.tc6m.R;
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

    private boolean parado;
    //TESTE
    private TesteHelper helper;
    private Teste teste;
    private CircularProgressBar circularProgressBar;
    private boolean primeiraVez;//que clicou em parar
    private long milis;
    private Chronometer cronometroParadas;

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

        circularProgressBar = (CircularProgressBar) findViewById(R.id.progressoCirculo);
        circularProgressBar.setProgressWithAnimation(17, 60000);
        //Para a barra de progresso ir até o 17%, depois ela é manipulada dentro do método mostraCampos chamado a cada minuto.

        Button btConfirma = (Button) findViewById(R.id.btConfirma);
        //Essa declaração do botao é apenas para testes
        btConfirma.setVisibility(View.VISIBLE);
        btConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dpVoltas = Integer.parseInt(textDistancia.getText().toString());
                teste.setDistanciaPercorrida(dpVoltas);
                Intent intentVaiProValoresFinais = new Intent(CronometroActivity.this, ValoresFinaisActivity.class);
                intentVaiProValoresFinais.putExtra("teste", teste);
                startActivity(intentVaiProValoresFinais);
                finish();

            }
        }); //apagar depois

        iniciaCronometro();

        Intent intent = getIntent();
        teste = (Teste) intent.getSerializableExtra("teste");
        helper = new TesteHelper(this, teste);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        TextView textNome = (TextView) findViewById(R.id.textNomePaciente);
        textNome.setText(teste.getPaciente().getNome());

        textDistancia = (TextView) findViewById(R.id.textMetros);
        //BOTAO DA VOLTA
        FloatingActionButton btAdicionarVolta = (FloatingActionButton) findViewById(R.id.btAdicionarVolta);
        btAdicionarVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metros += volta;
                textDistancia.setText(String.valueOf(metros));
                teste.setVoltas(fase, teste.getVoltas(fase) + volta); //Salva metros percorridos separados por minuto

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
                        LinearLayout layoutCrParada = (LinearLayout) findViewById(R.id.layoutCronometroParadas);
                        assert layoutCrParada != null;
                        layoutCrParada.setVisibility(View.VISIBLE);
                    }
                }

            }
        });


        //BOTAO FECHAR
        ImageButton btFechar = (ImageButton) findViewById(R.id.btFechar);
        assert btFechar != null;
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
        metros = fase = 0;

        //Pega o valor definido nas preferências para o tamanho da volta, default = 30m
        SharedPreferences sharedPref = getSharedPreferences("PREFERENCIAS",MODE_PRIVATE);
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

        final TextView textDistanciaFinal = (TextView) findViewById(R.id.textDistanciaFinal);
        textDistanciaFinal.setText(textDistancia.getText().toString());

        final EditText edTextDistanciaRestante = (EditText) findViewById(R.id.edTextDistanciaRestante);

        for (int i = 0; i < 6; i++) {
            layoutsDados[i].setVisibility(View.VISIBLE);
            botoesSalvar[i].setVisibility(View.GONE);
        }

        //BOTAO FINALIZA ACTIVITY
        btConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dpVoltas = Integer.parseInt(textDistanciaFinal.getText().toString()); //voltas completas durante o teste
                int dpRestante = 0;
                if (!edTextDistanciaRestante.getText().toString().isEmpty()) {
                    dpRestante = Integer.parseInt(edTextDistanciaRestante.getText().toString()); //valor adicionado no final quando o paciente para
                }
                teste.setVoltas(5, teste.getVoltas(5) + dpRestante); //salva o restante da ultima volta(onde o paciente parou)
                teste.setDistanciaPercorrida(dpVoltas + dpRestante);//salva a distancia percorrida total

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
        if (minuto < 6) {
            fase = minuto + 1; // fase é usado para o setVoltas
        }

        if (minuto < 5) { // frase do sexto minuto é exibida num AlertDialog
            layoutFrase.setVisibility(View.VISIBLE);
            textFrase.setText(frasesId[minuto]);
        }

        circularProgressBar.setProgressWithAnimation((minuto + 2) * 17, 60000);

        layoutsDados[minuto] = (LinearLayout) findViewById(layoutsDadosXML[minuto]); //card para botar os valores do minuto que aparece
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
