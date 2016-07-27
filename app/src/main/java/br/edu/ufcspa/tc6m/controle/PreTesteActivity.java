package br.edu.ufcspa.tc6m.controle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Paciente;

public class PreTesteActivity extends AppCompatActivity {

    Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_teste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        paciente = (Paciente) getIntent().getSerializableExtra("paciente");

        findViewById(R.id.bt_basal_fc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PreTesteActivity.this, "Valor obrigatório", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.bt_final_fc).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Valor obrigatório", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        int[] checkBoxes = {
                R.id.bt_durante_fc,
                R.id.bt_repouso_fc,

                R.id.bt_basal_dispneia,
                R.id.bt_durante_dispneia,
                R.id.bt_final_dispneia,
                R.id.bt_repouso_dispneia,

                R.id.bt_basal_fadiga,
                R.id.bt_durante_fadiga,
                R.id.bt_final_fadiga,
                R.id.bt_repouso_fadiga,

                R.id.bt_basal_spo2,
                R.id.bt_durante_spo2,

                R.id.bt_basal_pa,
                R.id.bt_final_pa,
                R.id.bt_repouso_pa,

                R.id.bt_basal_gc,
                R.id.bt_final_gc,
                R.id.bt_repouso_gc
        };

        //Preferencias são diferentes para cada paciente
        SharedPreferences sharedPreferences = getSharedPreferences("VARIAVEIS_DO_PACIENTE_" +
                paciente.getId(), MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        //Para cada valor no array de checkbox o for executa uma vez, iniciando as checkboxes com o
        //valor das preferencias e criando um listener que muda as preferencias
        for (final int resId : checkBoxes) {
            //Esse valor representa o id de um checkBox que é iniciado com o findViewById
            final CheckBox checkbox = (CheckBox) findViewById(resId);
            //A key das sharedPreferences será o id do botao no layout, ex: durante_fc
            final String key = getResources().getResourceName(resId).replace("br.edu.ufcspa.tc6m:id/bt_", "");
            checkbox.setChecked(sharedPreferences.getBoolean(key, true));

            //Valor é alterado no banco no clique
            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putBoolean(key, checkbox.isChecked());
                    editor.apply();
                }
            });
        }

        Button btOk = (Button) findViewById(R.id.btOk);
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiProValoresBasais = new Intent(PreTesteActivity.this, ValoresBasaisActivity.class);
                intentVaiProValoresBasais.putExtra("paciente", paciente);
                startActivity(intentVaiProValoresBasais);
                finish();
            }
        });

    }
}
