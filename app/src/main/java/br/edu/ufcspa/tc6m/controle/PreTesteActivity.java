package br.edu.ufcspa.tc6m.controle;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

        //Checkbox obrigatórias true
        CheckBox btBasalFc = (CheckBox) findViewById(R.id.bt_basal_fc);
        CheckBox btFinalFc = (CheckBox) findViewById(R.id.bt_final_fc);
        //Listener para aparecer alerta de que FC final e basal são obrigatórios
        View.OnClickListener listenerAlertaFcObrigatorio = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                findViewById(R.id.text_fc_obrigatorio).setVisibility(View.VISIBLE);
                ((CheckBox) v).setChecked(true);
            }
        };
        btFinalFc.setOnClickListener(listenerAlertaFcObrigatorio);
        btBasalFc.setOnClickListener(listenerAlertaFcObrigatorio);

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
                R.id.bt_final_spo2,
                R.id.bt_repouso_spo2,

                R.id.bt_basal_fr,
                R.id.bt_final_fr,
                R.id.bt_repouso_fr,

                R.id.bt_basal_pa,
                R.id.bt_final_pa,
                R.id.bt_repouso_pa,

                R.id.bt_basal_gc,
                R.id.bt_final_gc,
                R.id.bt_repouso_gc
        };

        int[] layouts = {
                R.id.layout_dispneia,
                R.id.layout_fadiga,
                R.id.layout_spo2,
                R.id.layout_fr,
                R.id.layout_pa,
                R.id.layout_gc
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
            checkbox.setChecked(sharedPreferences.getBoolean(key, false));

            //Valor é alterado no banco no clique
            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putBoolean(key, checkbox.isChecked());
                    editor.apply();
                }
            });
        }

        for (final int resId : layouts) {
            final LinearLayout layout = (LinearLayout) findViewById(resId);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                        TransitionManager.beginDelayedTransition(layout);

                    if (layout.getLayoutParams().height == LinearLayout.LayoutParams.WRAP_CONTENT) {
                        LinearLayout.LayoutParams posicao = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
                        posicao.height = height;
                        layout.setLayoutParams(posicao);
                    } else {
                        LinearLayout.LayoutParams posicao = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layout.setLayoutParams(posicao);
                    }
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
