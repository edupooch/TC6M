package br.edu.ufcspa.tc6m.controle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.shinelw.library.ColorArcProgressBar;

import br.edu.ufcspa.tc6m.R;

public class TesteActivity extends AppCompatActivity {


private Chronometer crono;

    public TesteActivity(Chronometer crono) {
        this.crono = crono;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        crono.start();
        Button btn = (Button)findViewById(R.id.btn_get_time);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Tempo: " + crono.getBase(), Toast.LENGTH_LONG).show();
            }
        });



    }

}
