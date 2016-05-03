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
import android.widget.Toast;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Teste;

public class ValoresRecuperacaoActivity extends AppCompatActivity {


    private Teste teste;
    private TesteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valores_recuperacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        iniciaComponentes();
    }

    private void iniciaComponentes() {

        Intent intent = getIntent();
        teste = (Teste) intent.getSerializableExtra("teste");
        helper = new TesteHelper(this,teste);

        final Chronometer crono = (Chronometer) findViewById(R.id.cronometroRecuperacao);
        crono.setBase(SystemClock.elapsedRealtime());
        crono.start();

        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                int tempo = Integer.parseInt(crono.getText().toString().replace(":", "")); //TRANSFORMA O RELÓGIO EM UM INTEIRO (01:32 = 132)

                if (tempo == 2) {
                    RelativeLayout layoutCronometro = (RelativeLayout) findViewById(R.id.layoutCronometroRecup);
                    LinearLayout layoutDados = (LinearLayout) findViewById(R.id.layoutDadosRecuperacao);

                    layoutCronometro.setVisibility(View.GONE);
                    layoutDados.setVisibility(View.VISIBLE);
                }
            }
        });

        Button btSalvar = (Button) findViewById(R.id.btSalvarRecuperacao);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teste = helper.pegaDadosFromFields(8);
                //dao.salvar(teste);
                Toast.makeText(getApplicationContext(),"FC 3= "+teste.getFc(3)+"FC 7= "+teste.getFc(7),Toast.LENGTH_LONG).show();
                finish();

            }
        });
    }

}
