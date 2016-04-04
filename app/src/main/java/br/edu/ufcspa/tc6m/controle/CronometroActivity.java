package br.edu.ufcspa.tc6m.controle;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.text.DateFormat;

import br.edu.ufcspa.tc6m.R;

public class CronometroActivity extends AppCompatActivity {

    private FloatingActionButton btStart;
    private Chronometer crono;
    long miliseconds;
    long milisecondsStop;
    boolean rodando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        miliseconds = 0;
        milisecondsStop = 0;
        rodando = false;

        crono = (Chronometer) findViewById(R.id.cronometro);


        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                miliseconds = SystemClock.elapsedRealtime() - crono.getBase();

                if (miliseconds >= 360000) {
                    chronometer.stop();
                    crono.stop();
                    Toast.makeText(getApplicationContext(), "tempo: " + miliseconds, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btStart = (FloatingActionButton) findViewById(R.id.btStart);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rodando) {
                    rodando = false;
                    btStart.setImageResource(R.drawable.icon_play);
                    crono.stop();


                } else {
                    rodando = true;
                    crono.setBase(SystemClock.elapsedRealtime() - miliseconds);
                    crono.start();
                    btStart.setImageResource(R.drawable.icon_stop);

                }
            }
        });


    }

}
