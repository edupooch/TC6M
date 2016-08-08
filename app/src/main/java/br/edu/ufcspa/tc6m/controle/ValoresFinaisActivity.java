package br.edu.ufcspa.tc6m.controle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.dao.TesteDAO;
import br.edu.ufcspa.tc6m.modelo.Teste;

public class ValoresFinaisActivity extends AppCompatActivity {

    private TesteHelper helper;
    private Teste teste;
    private boolean temAlgumValorDeRepouso;
    private Chronometer cronometro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valores_finais2);
        Intent intent = getIntent();

        teste = (Teste) intent.getSerializableExtra("teste");
        helper = new TesteHelper(this, teste);

        verificaValores();
        iniciaComponentes();

    }


    private void verificaValores() {

        SharedPreferences sharedPreferences = getSharedPreferences("VARIAVEIS_DO_PACIENTE_" +
                teste.getPaciente().getId(), Context.MODE_PRIVATE);

        boolean fcRepouso = sharedPreferences.getBoolean("repouso_fc", false);
        boolean fadRepouso = sharedPreferences.getBoolean("repouso_fadiga", false);
        boolean dispRepouso = sharedPreferences.getBoolean("repouso_dispneia", false);
        boolean paRepouso = sharedPreferences.getBoolean("repouso_pa", false);
        boolean gcRepouso = sharedPreferences.getBoolean("repouso_gc", false);
        temAlgumValorDeRepouso = fcRepouso || dispRepouso || fadRepouso || paRepouso || gcRepouso;


    }

    private void iniciaComponentes() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        helper.preencheCamposFinais();
        TextView titulo = (TextView) findViewById(R.id.tituloFinais);
        titulo.setText(teste.getPaciente().getNome());

        Button btSalvar = (Button) findViewById(R.id.btSalvarFinal);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teste = helper.pegaDadosFromFields(7);
                //Verifica se mostra a tela para pegar valores de recuperação
                if (temAlgumValorDeRepouso) {
                    teste.setBaseCronometroRec(cronometro.getBase());
                    Intent intentVaiProValoresRecuperacao = new Intent(ValoresFinaisActivity.this, ValoresRecuperacaoActivity.class);
                    intentVaiProValoresRecuperacao.putExtra("teste", teste);
                    startActivity(intentVaiProValoresRecuperacao);

                } else {//Pula a recuperação e vai direto pra análise
                    TesteDAO dao = new TesteDAO(getApplicationContext());
                    dao.insere(teste);
                    dao.close();


                    Intent intentVaiPraAnalise = new Intent(ValoresFinaisActivity.this, AnaliseTesteActivity.class);
                    intentVaiPraAnalise.putExtra("teste", teste);
                    startActivity(intentVaiPraAnalise);
                }
                finish();
            }

        });


        if (temAlgumValorDeRepouso) {
            cronometro = (Chronometer) findViewById(R.id.cronometro_finais);
            cronometro.setVisibility(View.VISIBLE);
            cronometro.setContentDescription(getString(R.string.cronometro_de_recuperacao));
            cronometro.setBase(SystemClock.elapsedRealtime());
            cronometro.start();
            cronometro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                //Transforma o cronometro em vermelho após 1:40
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    int tempo = Integer.parseInt(cronometro.getText().toString().replace(":", "")); //TRANSFORMA O RELÓGIO EM UM INTEIRO (01:32 = 132)
                    if (tempo >= 140) {
                        cronometro.setTextColor(Color.rgb(217, 80, 66));//Collor accent
                    }
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
        savedInstanceState.putLong("cronometro", cronometro.getBase());
        super.onSaveInstanceState(savedInstanceState);
    }

    //onRestoreInstanceState
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Recuperar as informações perdidas após virar a tela
        cronometro.setBase(savedInstanceState.getLong("cronometro"));
    }
}
