package br.edu.ufcspa.tc6m.controle;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.adapter.FormulasDialogAdapter;
import br.edu.ufcspa.tc6m.dao.TesteDAO;
import br.edu.ufcspa.tc6m.formulas.ListaFormulas;
import br.edu.ufcspa.tc6m.modelo.Formula;
import br.edu.ufcspa.tc6m.modelo.Teste;
import br.edu.ufcspa.tc6m.modelo.Velocidade;

public class AnaliseTesteActivity extends AppCompatActivity {

    public static final int ID_FC_FINAL = 7;
    public static final int ID_FC_INICIAL = 0;
    private Teste teste;

    //0=BASAL, 1-6=MINUTOS, 7=FINAL, 8=RECUPERAÇÃO
    private TextView[] textFc = new TextView[9];
    private TextView[] textDisp = new TextView[8];
    private TextView[] textFad = new TextView[8];
    private TextView[] textSp = new TextView[8];
    //0=BASAL, 1=FINAL, 2=RECUPERAÇÃO
    private TextView[] textPa = new TextView[3];
    private TextView[] textGc = new TextView[3];
    private CircularProgressBar circuloPercDp1;
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

        iniciaGraficoVelocidades();

        TextView textNomePaciente = (TextView) findViewById(R.id.text_nome_paciente_resultados);
        textNomePaciente.setText(teste.getPaciente().getNome());

        TextView textIdade = (TextView) findViewById(R.id.text_idade_paciente_resultados);
        String strIdade = +teste.getIdade() + " anos";
        textIdade.setText(strIdade);

        TextView textDistanciaPercorrida = (TextView) findViewById(R.id.text_dp);
        String strDp = teste.getDistanciaPercorrida() + "m";
        textDistanciaPercorrida.setText(strDp);

        final TextView textVelocidadeMedia = (TextView) findViewById(R.id.text_velocidade_media);
        final double vmMetrosSegundo = Calcula.velocidadeMedia(teste.getDistanciaPercorrida());
        final double vmKmHora = vmMetrosSegundo * 3.6;
        final double vmMetrosMin = vmMetrosSegundo * 60;

        final String strVmMetrosSegundo = String.format(Locale.getDefault(), "%.2f m/s", vmMetrosSegundo);
        textVelocidadeMedia.setText(strVmMetrosSegundo);
        textVelocidadeMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textVelocidadeMedia.getText().toString().contains("m/s")) {
                    String strVmKmHora = String.format(Locale.getDefault(), "%.2f km/h", vmKmHora);
                    textVelocidadeMedia.setText(strVmKmHora);
                } else if (textVelocidadeMedia.getText().toString().contains("km/h")) {
                    String strVmMetrosMin = String.format(Locale.getDefault(), "%.2f m/min", vmMetrosMin);
                    textVelocidadeMedia.setText(strVmMetrosMin);
                } else if (textVelocidadeMedia.getText().toString().contains("m/min")) {
                    textVelocidadeMedia.setText(strVmMetrosSegundo);
                }
            }
        });

        TextView textTamanhoVolta = (TextView) findViewById(R.id.text_tamanho_volta);
        String strTamanhoVolta = teste.getTamanhoVolta() + "m";
        textTamanhoVolta.setText(strTamanhoVolta);

        TextView textNParadas = (TextView) findViewById(R.id.text_paradas);
        textNParadas.setText(String.valueOf(teste.getnParadas()));
        if (teste.getnParadas() > 0) {
            //SÓ MOSTRA O TEMPO PARADO CASO TENHA ALGUMA PARADA
            findViewById(R.id.text_titulo_tempo_parado).setVisibility(View.VISIBLE);
            TextView textTempoParadas = (TextView) findViewById(R.id.text_tempo_paradas);
            textTempoParadas.setVisibility(View.VISIBLE);
            textTempoParadas.setText(teste.getTempoParadas());
        }

        //Pega a última formula usada das preferências, e o default é a formula 0 (Britto 1)
        SharedPreferences sharedPref = getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
        int ultimaFormula = sharedPref.getInt("ULTIMA_FORMULA", ListaFormulas.ID_BRITTO1);
        Formula formulaInicial = new ListaFormulas().getFormulas().get(ultimaFormula);
        atualizaFormula(formulaInicial);

        //Cria o listener de clique longo no circulo para trocar a fórmula de interpretação
        circuloPercDp1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Abrirá um dialog com uma listView de fórmulas para escolher
                final Dialog dialog = new Dialog(AnaliseTesteActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_formulas, null);
                ListView lista = (ListView) view.findViewById(R.id.lista_formulas_dialog);

                //Pega das preferências as fórmulas que o usuário ativou na tela de configurações
                SharedPreferences sharedPref = getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
                final List<Formula> formulas = new ListaFormulas().getFormulasSelecionadas(sharedPref);
                FormulasDialogAdapter adapter = new FormulasDialogAdapter(AnaliseTesteActivity.this, formulas);
                lista.setAdapter(adapter);

                //Item listener para atualizar a fórmula quando selecionar uma
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        atualizaFormula(formulas.get(position));
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(view);
                dialog.show();
                return false;
            }
        });


        //.............................VALORES DOS SINAIS.........................................//

        //Arrays com Resource IDs de textViews para agilizar os setTexts em um for loop
        int[] fcValoresResId = new int[]{R.id.resultado_fc_inicial, R.id.resultado_fc_1_minuto, R.id.resultado_fc_2_minuto, R.id.resultado_fc_3_minuto, R.id.resultado_fc_4_minuto, R.id.resultado_fc_5_minuto, R.id.resultado_fc_final, R.id.resultado_fc_rec2, R.id.resultado_fc_rec2};
        int[] spValoresResId = new int[]{R.id.resultado_sp_inicial, R.id.resultado_sp_1_minuto, R.id.resultado_sp_2_minuto, R.id.resultado_sp_3_minuto, R.id.resultado_sp_4_minuto, R.id.resultado_sp_5_minuto, R.id.resultado_sp_final, R.id.resultado_sp_recup};
        int[] dispValoresResId = new int[]{R.id.resultado_disp_inicial, R.id.resultado_disp_1_minuto, R.id.resultado_disp_2_minuto, R.id.resultado_disp_3_minuto, R.id.resultado_disp_4_minuto, R.id.resultado_disp_5_minuto, R.id.resultado_disp_final, R.id.resultado_disp_recup};
        int[] fadValoresResId = new int[]{R.id.resultado_fad_inicial, R.id.resultado_fad_1_minuto, R.id.resultado_fad_2_minuto, R.id.resultado_fad_3_minuto, R.id.resultado_fad_4_minuto, R.id.resultado_fad_5_minuto, R.id.resultado_fad_final, R.id.resultado_fad_recup};
        int[] paValoresResId = new int[]{R.id.resultado_pa_inicial, R.id.resultado_pa_final, R.id.resultado_pa_recup};
        int[] gcValoresResId = new int[]{R.id.resultado_gc_inicial, R.id.resultado_gc_final, R.id.resultado_gc_recup};

        //Booleans para verificar se há algum valor preenchido daquela categoria, para esconder os cards sem valor
        //FC é obrigatório o inicial e o final
        boolean temAlgumDispeneia = false;
        boolean temAlgumFadiga = false;
        boolean temAlgumSp = false;
        boolean temAlgumPa = false;
        boolean temAlgumGc = false;


        for (int i = 0; i < 9; i++) {
            if (teste.getFc(i) != null) {
                textFc[i] = (TextView) findViewById(fcValoresResId[i]);
                textFc[i].setText(String.valueOf(teste.getFc(i)));
            } else {
                switch (i) {
                    case 1:
                        findViewById(R.id.layout_fc_durante).setVisibility(View.GONE);
                        break;
                    case 8://valores de recup
                        findViewById(R.id.layout_fc_rec2).setVisibility(View.GONE);
                        findViewById(R.id.layout_fc_rec1).setVisibility(View.GONE);
                        findViewById(R.id.layout_var_final_rec1).setVisibility(View.GONE);
                        findViewById(R.id.layout_var_final_rec2).setVisibility(View.GONE);
                        break;
                }
            }
            if (i < 8) {

                if (teste.getDispneia(i) != null) {
                    textDisp[i] = (TextView) findViewById(dispValoresResId[i]);
                    textDisp[i].setText(String.valueOf(teste.getDispneia(i)));
                    temAlgumDispeneia = true;
                } else {
                    switch (i) {
                        case 0://VALORES BASAIS
                            findViewById(R.id.layout_dispneia_inicial).setVisibility(View.GONE);
                            break;
                        case 1://VALORES DURANTE O TESTE 1-6
                            findViewById(R.id.layout_dispneia_durante).setVisibility(View.GONE);
                            break;
                        case 6: //VALORES FINAIS
                            findViewById(R.id.layout_dispneia_final).setVisibility(View.GONE);
                            break;
                        case 7: //VALORES DEPOIS DA RECUPERAÇÃO
                            findViewById(R.id.layout_dispneia_recup).setVisibility(View.GONE);
                            break;
                    }
                }
                if (teste.getFadiga(i) != null) {
                    textFad[i] = (TextView) findViewById(fadValoresResId[i]);
                    textFad[i].setText(String.valueOf(teste.getFadiga(i)));
                    temAlgumFadiga = true;
                } else {
                    switch (i) {
                        case 0://VALORES BASAIS
                            findViewById(R.id.layout_fadiga_inicial).setVisibility(View.GONE);
                            break;
                        case 1://VALORES DURANTE O TESTE 1-5
                            findViewById(R.id.layout_fadiga_durante).setVisibility(View.GONE);
                            break;
                        case 7: //VALORES FINAIS
                            findViewById(R.id.layout_fadiga_final).setVisibility(View.GONE);
                            break;
                        case 8: //VALORES DEPOIS DA RECUPERAÇÃO
                            findViewById(R.id.layout_fadiga_recup).setVisibility(View.GONE);
                            break;
                    }
                }

                if (teste.getSpO2(i) != null) {
                    textSp[i] = (TextView) findViewById(spValoresResId[i]);
                    String strSp = teste.getSpO2(i) + "%";
                    textSp[i].setText(String.valueOf(strSp));
                    temAlgumSp = true;
                } else {
                    switch (i) {
                        case 0://VALORES BASAIS
                            findViewById(R.id.layout_sp_inicial).setVisibility(View.GONE);
                            break;
                        case 1://VALORES DURANTE O TESTE 1-5
                            //layout dos minutos 1-5
                            findViewById(R.id.layout_sp_durante).setVisibility(View.GONE);
                            break;
                        case 6:
                            findViewById(R.id.layout_sp_final).setVisibility(View.GONE);
                            break;
                        case 7:
                            findViewById(R.id.layout_sp_recup).setVisibility(View.GONE);
                            break;
                    }
                }
                if (i < 3) {
                    if ((teste.getPAs(i) != null) && (teste.getPAd(i) != null)) {
                        textPa[i] = (TextView) findViewById(paValoresResId[i]);
                        String strPa = teste.getPAs(i) + "/" + teste.getPAd(i);
                        textPa[i].setText(strPa);
                        temAlgumPa = true;
                    } else {
                        switch (i) {
                            case 0://VALORES BASAIS
                                findViewById(R.id.layout_pa_inicial).setVisibility(View.GONE);
                                break;
                            case 1://VALORES FINAIS
                                findViewById(R.id.layout_pa_final).setVisibility(View.GONE);
                                break;
                            case 2: //VALORES DEPOIS DA RECUPERAÇÃO
                                findViewById(R.id.layout_pa_repouso).setVisibility(View.GONE);
                                break;
                        }
                    }
                    if (teste.getGc(i) != null) {
                        textGc[i] = (TextView) findViewById(gcValoresResId[i]);
                        textGc[i].setText(String.valueOf(teste.getGc(i)));
                        temAlgumGc = true;
                    } else {
                        switch (i) {
                            case 0://VALORES BASAIS
                                findViewById(R.id.layout_gc_inicial).setVisibility(View.GONE);
                                break;
                            case 1://VALORES FINAIS
                                findViewById(R.id.layout_gc_final).setVisibility(View.GONE);
                                break;
                            case 2: //VALORES DEPOIS DA RECUPERAÇÃO
                                findViewById(R.id.layout_gc_repouso).setVisibility(View.GONE);
                                break;
                        }
                    }
                }
            }

        }
        /////////////////////////VARIAÇÕES//////////////////////////////////////////////////////////
        TextView varFinalBasal = (TextView) findViewById(R.id.resultado_var_final_basal);
        TextView varFinalRec1 = (TextView) findViewById(R.id.resultado_var_final_rec1);
        TextView varFinalRec2 = (TextView) findViewById(R.id.resultado_var_final_rec2);

        if (teste.getFc(6) != null) {
            if (teste.getFc(0) != null)
                varFinalBasal.setText(String.valueOf(teste.getFc(6) - teste.getFc(0)));
            if (teste.getFc(7) != null)
                varFinalRec1.setText(String.valueOf(teste.getFc(6) - teste.getFc(7)));
            if (teste.getFc(8) != null)
                varFinalRec2.setText(String.valueOf(teste.getFc(6) - teste.getFc(8)));
        }

        //.............................ESCONDE CARDS..........................................//
        //Se nao tem nenhum valor de x, esconde o card da análise
        if (!temAlgumDispeneia) findViewById(R.id.card_disp).setVisibility(View.GONE);
        if (!temAlgumFadiga) findViewById(R.id.card_fad).setVisibility(View.GONE);
        if (!temAlgumSp) findViewById(R.id.card_spo2).setVisibility(View.GONE);
        if (!temAlgumPa) findViewById(R.id.card_pa).setVisibility(View.GONE);
        if (!temAlgumGc) findViewById(R.id.card_gc).setVisibility(View.GONE);

        if (teste.getO2Supl(0) != null) {
            TextView textO2supl = (TextView) findViewById(R.id.resultado_o2_supl);
            textO2supl.setText(String.valueOf(teste.getO2Supl(0)));
        } else {
            findViewById(R.id.card_o2supl).setVisibility(View.GONE);
        }

        if (teste.getObsFinal() != null) {
            TextView textObsFinal = (TextView) findViewById(R.id.resultado_obs);
            textObsFinal.setText(teste.getObsFinal());
        }


        //..............................BOTAO DE FINALIZAR.........................................//


        Button btOk = (Button) findViewById(R.id.btOkAnalise);
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * Carrega o circularProgressBar com a porcentagem e os textos de distância prevista,
     * utiliza a classe Calcula e passa por parâmetro a fórmula utilizada para receber os resultados
     *
     * @param formula #Formula é usada para pegar o id e passar para Calcula() e pegar o nome da fórmula
     */
    private void atualizaFormula(Formula formula) {
        TextView dpEstimada = (TextView) findViewById(R.id.text_dp_estimada_1);
        TextView percDpEstimada = (TextView) findViewById(R.id.text_dp_porcento_1);
        int variacaoFc = 0;
        if (teste.getFc(ID_FC_FINAL) != null && teste.getFc(ID_FC_INICIAL) != null) {
            variacaoFc = teste.getFc(ID_FC_FINAL) - teste.getFc(ID_FC_INICIAL);
        }
        double dbDpEstimada1 = Calcula.dpEstimada(formula, teste.getIdade(), teste.getPaciente().getGenero(), teste.getMassa(), teste.getEstatura(), variacaoFc);
        String strDpEstimada1 = String.format(Locale.getDefault(), "de %.2fm", dbDpEstimada1);
        dpEstimada.setText(strDpEstimada1);

        double dbPercentDpEstimada1 = Calcula.porcentagem(teste.getDistanciaPercorrida(), dbDpEstimada1);
        String strPercDpEstimada1 = String.format(Locale.getDefault(), "%.1f", dbPercentDpEstimada1);
        percDpEstimada.setText(strPercDpEstimada1);

        circuloPercDp1 = (CircularProgressBar) findViewById(R.id.circulo_porcento_1);
        circuloPercDp1.setProgressWithAnimation((float) dbPercentDpEstimada1);

        TextView textTituloEquacao = (TextView) findViewById(R.id.titulo_equacao);
        textTituloEquacao.setText(formula.getAutores());

        //Salva nas preferências da activity a fórmula pedida como ultima formula usada
        SharedPreferences sharedPref = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("ULTIMA_FORMULA", formula.getIdFormula());
        editor.apply();
    }

//    private void iniciaGraficoVoltasMinuto() {
//        BarChart graficoDistanciasPercorridas = (BarChart) findViewById(R.id.grafico_voltas_minuto);
//
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            barEntries.add(new BarEntry(teste.getVoltas(i), i));
//        }
//        BarDataSet barDataSet = new BarDataSet(barEntries, "Distância Percorrida");
//        barDataSet.setColors(new int[]{Color.rgb(83, 186, 131), Color.rgb(58, 150, 101)});
//
//        ArrayList<String> stringsMinutos = new ArrayList<>();
//        for (int i = 1; i <= 6; i++) {
//            stringsMinutos.add(i + "º min");
//        }
//        graficoDistanciasPercorridas.setDrawGridBackground(false);
//        BarData dados = new BarData(stringsMinutos, barDataSet);
//        graficoDistanciasPercorridas.setData(dados);
//        graficoDistanciasPercorridas.setDescription("");
//        graficoDistanciasPercorridas.setPinchZoom(true);
//        graficoDistanciasPercorridas.setDragEnabled(true);
//        graficoDistanciasPercorridas.setScaleEnabled(true);
//        graficoDistanciasPercorridas.animateY(1000);
//
//    }

    private void iniciaGraficoVelocidades() {
        LineChart graficoVelocidades = (LineChart) findViewById(R.id.grafico_velocidades);
        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("00:00");
        for (Velocidade velocidade : teste.getVelocidades()) {
            xVals.add(velocidade.getTempo());
        }
        xVals.add("06:00");
        ArrayList<Entry> yVals = new ArrayList<>();

        ArrayList<Velocidade> velocidades = teste.getVelocidades();
        for (int i = 1; i < velocidades.size(); i++) {
            Velocidade velocidade = velocidades.get(i);
            yVals.add(new Entry(velocidade.getVelocidade(), i));
        }

        LineDataSet set = new LineDataSet(yVals, "Velocidade");
        //Gráfico cúbico
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        int COR_PRIMARIA = Color.rgb(83, 186, 131);
        set.setFillColor(COR_PRIMARIA);
//        set.setFillAlpha(100);


        set.setDrawFilled(true);
        set.setColor(COR_PRIMARIA);

        LineData dados = new LineData(xVals, set);
        graficoVelocidades.setData(dados);

        graficoVelocidades.getAxisRight().setEnabled(false);
        graficoVelocidades.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        graficoVelocidades.setDrawGridBackground(false);
        graficoVelocidades.setPinchZoom(true);
        graficoVelocidades.setDescription("");
        graficoVelocidades.setDragEnabled(true);
        graficoVelocidades.setScaleEnabled(true);
        graficoVelocidades.animateX(1000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Excluir Teste");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle() == "Excluir Teste") {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle(getString(R.string.atencao_deletar_teste));
            builder.setMessage(getString(R.string.dialog_deletar_teste));
            builder.setPositiveButton(getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TesteDAO dao = new TesteDAO(AnaliseTesteActivity.this);
                    dao.deleta(teste);
                    dao.close();
                    finish();
                    Toast.makeText(AnaliseTesteActivity.this, "Teste excluído com sucesso", Toast.LENGTH_SHORT).show();
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

        return super.onOptionsItemSelected(item);
    }
}