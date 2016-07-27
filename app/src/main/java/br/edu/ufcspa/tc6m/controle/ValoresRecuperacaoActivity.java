package br.edu.ufcspa.tc6m.controle;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.dao.TesteDAO;
import br.edu.ufcspa.tc6m.modelo.Teste;

public class ValoresRecuperacaoActivity extends AppCompatActivity {


    private static final float DURATION_TOTAL = 120000; //2 minutos em milisegundos

    private Teste teste;
    private TesteHelper helper;

    private Chronometer crono;
    private CircularProgressBar circularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valores_recuperacao);
        iniciaComponentes();
    }

    private void iniciaComponentes() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        teste = (Teste) intent.getSerializableExtra("teste");
        helper = new TesteHelper(this, teste);

        barraDeProgresso(0);

        TextView titulo = (TextView) findViewById(R.id.tituloRecuperacao);
        titulo.setText(teste.getPaciente().getNome());

        crono = (Chronometer) findViewById(R.id.cronometroRecuperacao);
        crono.setBase(SystemClock.elapsedRealtime());
        crono.start();

        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                int tempo = Integer.parseInt(crono.getText().toString().replace(":", "")); //TRANSFORMA O RELÓGIO EM UM INTEIRO (01:32 = 132)
                if (tempo >= 200) {
                    escondeCronometro();
                }
            }
        });

        Button btPular = (Button) findViewById(R.id.btPularTempo);
        btPular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escondeCronometro();
            }
        });

        Button btSalvar = (Button) findViewById(R.id.btSalvarRecuperacao);
        assert btSalvar != null;
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teste = helper.pegaDadosFromFields(8);

                TesteDAO dao = new TesteDAO(getApplicationContext());
                dao.insere(teste);
                dao.close();

                Intent intentVaiPraAnalise = new Intent(ValoresRecuperacaoActivity.this, AnaliseTesteActivity.class);
                intentVaiPraAnalise.putExtra("teste", teste);
                startActivity(intentVaiPraAnalise);

                finish();

            }
        });
    }

    private void escondeCronometro() {
        crono.stop();
        findViewById(R.id.layoutAguarde).setVisibility(View.GONE);
        findViewById(R.id.layoutDadosRecuperacao).setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //Salvar o tempo no cronometro quando vira a tela
        savedInstanceState.putLong("cronometro", crono.getBase());
        //Salva o progresso atual da barra, para retornar de onde parou
        savedInstanceState.putFloat("progresso", circularProgressBar.getProgress());

        super.onSaveInstanceState(savedInstanceState);
    }

    //onRestoreInstanceState
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Recuperar as informações perdidas após virar a tela
        crono.setBase(savedInstanceState.getLong("cronometro"));
        barraDeProgresso(savedInstanceState.getFloat("progresso"));


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

}
