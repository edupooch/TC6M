package br.edu.ufcspa.tc6m.controle;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.adapter.FormulasAdapter;
import br.edu.ufcspa.tc6m.adapter.FormulasDialogAdapter;
import br.edu.ufcspa.tc6m.dao.TesteDAO;
import br.edu.ufcspa.tc6m.formulas.ListaFormulas;
import br.edu.ufcspa.tc6m.modelo.Formula;
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

        iniciaGrafico();

        TextView textNomePaciente = (TextView) findViewById(R.id.text_nome_paciente_resultados);
        String strNomeIdade = teste.getPaciente().getNome() + ", " + teste.getIdade() + " anos";
        textNomePaciente.setText(strNomeIdade);

        TextView textDistanciaPercorrida = (TextView) findViewById(R.id.text_dp);
        textDistanciaPercorrida.setText(String.valueOf(teste.getDistanciaPercorrida()));

        System.out.println("DP estimada 1: " + Calcula.dpEstimadaBritto1(teste.getIdade(), teste.getPaciente().getGenero(), teste.getMassa(), teste.getEstatura()));

        TextView dpEstimada1 = (TextView) findViewById(R.id.text_dp_estimada_1);
        TextView percDpEstimada1 = (TextView) findViewById(R.id.text_dp_porcento_1);

        double dbDpEstimada1 = Calcula.dpEstimadaBritto1(teste.getIdade(), teste.getPaciente().getGenero(), teste.getMassa(), teste.getEstatura());
        String strDpEstimada1 = String.format(Locale.getDefault(), "de %.2fm", dbDpEstimada1);
        dpEstimada1.setText(strDpEstimada1);

        double dbPercentDpEstimada1 = Calcula.porcentagem(teste.getDistanciaPercorrida(), dbDpEstimada1);
        String strPercDpEstimada1 = String.format(Locale.getDefault(), "%.1f", dbPercentDpEstimada1);
        percDpEstimada1.setText(strPercDpEstimada1);

        CircularProgressBar circuloPercDp1 = (CircularProgressBar) findViewById(R.id.circulo_porcento_1);
        circuloPercDp1.setProgress((float) dbPercentDpEstimada1);
        circuloPercDp1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(AnaliseTesteActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_formulas, null);

                ListView lista = (ListView) view.findViewById(R.id.lista_formulas_dialog);

                SharedPreferences sharedPref = getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
                final List<Formula> formulas =  new ListaFormulas().getFormulasSelecionadas(sharedPref);
                FormulasDialogAdapter adapter = new FormulasDialogAdapter(AnaliseTesteActivity.this,formulas);

                lista.setAdapter(adapter);

                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        atualizaFormula(formulas.get(position));
                    }
                });

                dialog.setContentView(view);
                dialog.show();

                return false;
            }
        });



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
                String strSp = teste.getSpO2(i) + "%";
                textSp[i].setText(String.valueOf(strSp));

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

    private void atualizaFormula(Formula formula) {
       
    }

    private void iniciaGrafico() {
        BarChart graficoDistanciasPercorridas = (BarChart) findViewById(R.id.grafico_voltas_minuto);


        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            barEntries.add(new BarEntry(teste.getVoltas(i), i));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "Distância Percorrida");
        barDataSet.setColors(new int[]{Color.rgb(83,186,131), Color.rgb(58,150,101)});

        ArrayList<String> stringsMinutos = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            stringsMinutos.add(i + "º min");
        }
        graficoDistanciasPercorridas.setDrawGridBackground(false);
        BarData dados = new BarData(stringsMinutos, barDataSet);
        graficoDistanciasPercorridas.setData(dados);
        graficoDistanciasPercorridas.setPinchZoom(true);
        graficoDistanciasPercorridas.setDragEnabled(true);
        graficoDistanciasPercorridas.setScaleEnabled(true);
        graficoDistanciasPercorridas.animateY(1000);

    }
}