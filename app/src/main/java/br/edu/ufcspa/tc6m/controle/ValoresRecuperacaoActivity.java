package br.edu.ufcspa.tc6m.controle;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.dao.TesteDAO;
import br.edu.ufcspa.tc6m.modelo.Teste;

public class ValoresRecuperacaoActivity extends AppCompatActivity {


    private Teste teste;
    private TesteHelper helper;
    private Chronometer crono;

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

        TextView titulo = (TextView) findViewById(R.id.tituloRecuperacao);
        titulo.setText(teste.getPaciente().getNome());

        crono = (Chronometer) findViewById(R.id.cronometroRecuperacao);
        crono.setBase(SystemClock.elapsedRealtime());
        crono.start();

        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                int tempo = Integer.parseInt(crono.getText().toString().replace(":", "")); //TRANSFORMA O RELÓGIO EM UM INTEIRO (01:32 = 132)
                if (tempo == 200) {
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
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teste = helper.pegaDadosFromFields(8);
                TesteDAO dao = new TesteDAO(getApplicationContext());
                dao.insere(teste);
                dao.close();
                Toast.makeText(getApplicationContext(), "FC 3= " + teste.getFc(3) + "FC 7= " + teste.getFc(7), Toast.LENGTH_LONG).show();
                finish();

            }
        });
    }

    private void escondeCronometro() {
        crono.stop();
        RelativeLayout layoutAguarde = (RelativeLayout) findViewById(R.id.layoutAguarde);
        LinearLayout layoutDados = (LinearLayout) findViewById(R.id.layoutDadosRecuperacao);
        layoutAguarde.setVisibility(View.GONE);
        layoutDados.setVisibility(View.VISIBLE);
    }
}
