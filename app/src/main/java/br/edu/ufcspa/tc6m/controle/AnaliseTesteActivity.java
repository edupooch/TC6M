package br.edu.ufcspa.tc6m.controle;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.custom.MyYAxisValueFormatter;
import br.edu.ufcspa.tc6m.modelo.Paciente;
import br.edu.ufcspa.tc6m.modelo.Teste;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

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
    private BarChart mChart;
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
        textNomePaciente.setText(teste.getPaciente().getNome());

        TextView textIdade = (TextView) findViewById(R.id.text_idade_resultado);
        textIdade.setText(String.valueOf(teste.getIdade()));

        TextView textDistanciaPercorrida = (TextView) findViewById(R.id.text_dp);
        textDistanciaPercorrida.setText(String.valueOf(teste.getDistanciaPercorrida()));

        System.out.println("DP estimada 1: " + Calcula.dpEstimadaBritto1(teste.getIdade(), teste.getPaciente().getGenero(), teste.getMassa(), teste.getEstatura()));

        TextView dpEstimada1 = (TextView) findViewById(R.id.text_dp_estimada_1);
        TextView percDpEstimada1 = (TextView) findViewById(R.id.text_dp_porcento_1);

        double dbDpEstimada1 = Calcula.dpEstimadaBritto1(teste.getIdade(), teste.getPaciente().getGenero(), teste.getMassa(), teste.getEstatura());
        String strDpEstimada1 = String.format(Locale.getDefault(), "%.2f", dbDpEstimada1);
        dpEstimada1.setText(strDpEstimada1);

        double dbPercentDpEstimada1 = Calcula.porcentagem(teste.getDistanciaPercorrida(), dbDpEstimada1);
        String strPercDpEstimada1 = String.format(Locale.getDefault(), "%.1f", dbPercentDpEstimada1);
        percDpEstimada1.setText(strPercDpEstimada1);

        CircularProgressBar circuloPercDp1 = (CircularProgressBar) findViewById(R.id.circulo_porcento_1);
        circuloPercDp1.setProgress((float) dbPercentDpEstimada1);

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

    private void iniciaGrafico() {
        mChart = (BarChart) findViewById(R.id.chart1);


        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            barEntries.add(new BarEntry(teste.getVoltas(i), i));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "Distância Percorrida");
        barDataSet.setColors(new int[]{R.color.corPrimaria, R.color.corPrimariaEscura});

        ArrayList<String> stringsMinutos = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            stringsMinutos.add(i + "º min");
        }
        mChart.setDrawGridBackground(false);
        BarData dados = new BarData(stringsMinutos, barDataSet);
        mChart.setData(dados);
        mChart.setPinchZoom(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.animateY(1000);

    }
//
//    private void geraGraficoDistancias() {
//        new Thread() {
//            public void run() {
//
//                final LineChartView graficoDp = (LineChartView) findViewById(R.id.grafico_dp_minuto);
//                List<PointValue> values = new ArrayList<PointValue>();
//                for (int i = 0; i < 6; i++) values.add(new PointValue(i, teste.getVoltas(i)));
//
//                Line line = new Line(values).setColor(Color.GREEN).setCubic(true);
//                List<Line> lines = new ArrayList<Line>();
//                lines.add(line);
//
//                final LineChartData data = new LineChartData();
//                data.setLines(lines);
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //MÉTODOS QUE ALTERAM A VIEW
//                        graficoDp.setInteractive(true);
//                        graficoDp.setLineChartData(data);
//                    }
//                });
//
//            }
//        }.start();
//    }


//    private void setData() {
//
//        ArrayList<String> xVals = new ArrayList<String>();
//        for (int i = 0; i < 6; i++) {
//            int minuto = i + 1;
//            xVals.add(minuto + "º min");
//        }
//
//        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//
//        for (int i = 0; i < 6; i++) {
//            float val = (float) (teste.getVoltas(i));
//            yVals1.add(new BarEntry(val, i));
//        }
//
//        BarDataSet set1;
//
//        if (mChart.getData() != null &&
//                mChart.getData().getDataSetCount() > 0) {
//            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
//            set1.setYVals(yVals1);
//            mChart.getData().setXVals(xVals);
//            mChart.getData().notifyDataChanged();
//            mChart.notifyDataSetChanged();
//        } else {
//            set1 = new BarDataSet(yVals1, "DataSet");
//            set1.setBarSpacePercent(35f);
//            set1.setColors(ColorTemplate.MATERIAL_COLORS);
//
//            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//            dataSets.add(set1);
//
//            BarData data = new BarData(xVals, dataSets);
//            data.setValueTextSize(10f);
//            data.setValueTypeface(mTf);
//
//            mChart.setData(data);
//        }
//    }
}