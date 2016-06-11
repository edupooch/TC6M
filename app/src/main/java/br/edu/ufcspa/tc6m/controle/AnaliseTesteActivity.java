package br.edu.ufcspa.tc6m.controle;

import android.content.Intent;
import android.opengl.ETC1;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Paciente;
import br.edu.ufcspa.tc6m.modelo.Teste;

public class AnaliseTesteActivity extends AppCompatActivity {

    private Teste teste;

    //0=BASAL, 1-6=MINUTOS, 7=FINAL, 8=RECUPERAÇÃO
    private TextView[] textFc = new TextView[9];
    private TextView[] textDisp = new TextView[9];
    private TextView[] textFad = new TextView[9];
    private TextView[] textSp = new TextView[7];
    //0=BASAL, 1=FINAL, 2=RECUPERAÇÃO
    private TextView[] textPa = new TextView[3];
    private TextView[] textGc = new TextView[3];


    //VALORES

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analise_teste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        teste = (Teste) intent.getSerializableExtra("teste");

        iniciaComponentes();
    }

    private void iniciaComponentes() {


        int[] fcValoresXML = new int[]{R.id.resultado_fc_inicial, R.id.resultado_fc_1_minuto, R.id.resultado_fc_2_minuto, R.id.resultado_fc_3_minuto, R.id.resultado_fc_4_minuto, R.id.resultado_fc_5_minuto, R.id.ressultado_fc_6_minuto, R.id.resultado_fc_final, R.id.resultado_fc_recup};
        int[] spValoresXML = new int[]{R.id.resultado_sp_inicial, R.id.resultado_sp_1_minuto, R.id.resultado_sp_2_minuto, R.id.resultado_sp_3_minuto, R.id.resultado_sp_4_minuto, R.id.resultado_sp_5_minuto, R.id.resultado_sp_6_minuto};
        int[] dispValoresXML = new int[]{R.id.resultado_disp_inicial, R.id.resultado_disp_1_minuto, R.id.resultado_disp_2_minuto, R.id.resultado_disp_3_minuto, R.id.resultado_disp_4_minuto, R.id.resultado_disp_5_minuto, R.id.resultado_disp_6_minuto, R.id.resultado_disp_final, R.id.resultado_disp_recup};
        int[] fadValoresXML = new int[]{R.id.resultado_fad_inicial, R.id.resultado_fad_1_minuto, R.id.resultado_fad_2_minuto, R.id.resultado_fad_3_minuto, R.id.resultado_fad_4_minuto, R.id.resultado_fad_5_minuto, R.id.resultado_fad_6_minuto, R.id.resultado_fad_final, R.id.resultado_fad_recup};
        int[] paValoresXML = new int[]{R.id.resultado_pa_inicial, R.id.resultado_pa_final, R.id.resultado_pa_recup};
        int[] gcValoresXML = new int[]{R.id.resultado_gc_inicial, R.id.resultado_gc_final, R.id.resultado_gc_recup};

        for (int i = 0; i < 9; i++) {

            textFc[i] = (TextView) findViewById(fcValoresXML[i]);
            textFc[i].setText(String.valueOf(teste.getFc(i)));

            textDisp[i] = (TextView) findViewById(dispValoresXML[i]);
            textDisp[i].setText(String.valueOf(teste.getDispneia(i)));

            textFad[i] = (TextView) findViewById(fadValoresXML[i]);
            textFad[i].setText(String.valueOf(teste.getFadiga(i)));

            if (i < 7) {
                textSp[i] = (TextView) findViewById(spValoresXML[i]);
                textSp[i].setText(String.valueOf(teste.getSpO2(i)));

                if (i < 3) {
                    textPa[i] = (TextView) findViewById(paValoresXML[i]);
                    textPa[i].setText(String.valueOf(teste.getPa(i)));

                    textGc[i] = (TextView) findViewById(gcValoresXML[i]);
                    textGc[i].setText(String.valueOf(teste.getGc(i)));
                }
            }

        }

        TextView textO2Supl = (TextView) findViewById(R.id.resultado_o2_supl);
        textO2Supl.setText(String.valueOf(teste.getO2Supl()));

        TextView textObsFinal = (TextView) findViewById(R.id.resultado_obs);
        textObsFinal.setText(teste.getObsFinal());

    }
}
