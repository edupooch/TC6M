package br.edu.ufcspa.tc6m.controle;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Paciente;
import br.edu.ufcspa.tc6m.modelo.Teste;

/**
 * Created by edupooch on 30/04/16.
 */
public class TesteHelper {
    private Teste teste;
    //0=BASAL, 1-6=MINUTOS, 7=ID_FC_FINAL, 8=RECUPERAÇÃO
    private EditText[] edTextFc = new EditText[9];
    private EditText[] edTextDisp = new EditText[9];
    private EditText[] edTextFad = new EditText[9];
    private EditText[] edTextSp = new EditText[7];
    //0=BASAL, 1=ID_FC_FINAL, 2=RECUPERAÇÃO
    private EditText[] edTextPAs = new EditText[3];
    private EditText[] edTextPAd = new EditText[3];
    private EditText[] edTextGc = new EditText[3];
    //BASAL
    private EditText edTextO2Supl;
    //ID_FC_FINAL
    private EditText edTextObsFinal;
    private String text;
    //VALORES


    public TesteHelper(ValoresBasaisActivity activity, Paciente paciente) {

        teste = new Teste(paciente);

        SharedPreferences sharedPreferences = activity.getSharedPreferences("VARIAVEIS_DO_PACIENTE_" +
                paciente.getId(), Context.MODE_PRIVATE);
        //Define os valores de massa e estatura no dia do teste - durante a activity valores basais;
        teste.setEstatura(paciente.getEstatura());
        teste.setMassa(paciente.getMassa());

        //fc basal é obrigatório
        edTextFc[0] = (EditText) activity.findViewById(R.id.edTextFC0);

        edTextDisp[0] = (EditText) activity.findViewById(R.id.edTextDisp0);

        if (!sharedPreferences.getBoolean("basal_dispneia", false)) {
            activity.findViewById(R.id.layout_dispneia).setVisibility(View.GONE);
        }
        edTextFad[0] = (EditText) activity.findViewById(R.id.edTextFad0);
        if (!sharedPreferences.getBoolean("basal_fadiga", false)) {
            activity.findViewById(R.id.layout_fadiga).setVisibility(View.GONE);
        }

        edTextSp[0] = (EditText) activity.findViewById(R.id.edTextSp0);
        if (!sharedPreferences.getBoolean("basal_spo2", false)) {
            activity.findViewById(R.id.layout_spo2).setVisibility(View.GONE);
        }
        edTextGc[0] = (EditText) activity.findViewById(R.id.edTextGC0);
        if (!sharedPreferences.getBoolean("basal_gc", false)) {
            activity.findViewById(R.id.layout_gc).setVisibility(View.GONE);
        }

        edTextPAs[0] = (EditText) activity.findViewById(R.id.editTextPAS0);
        edTextPAd[0] = (EditText) activity.findViewById(R.id.editTextPAD0);
        if (!sharedPreferences.getBoolean("basal_pa", false)) {
            activity.findViewById(R.id.layout_pa).setVisibility(View.GONE);
        }

        edTextO2Supl = (EditText) activity.findViewById(R.id.edTextO2Supl);

        //edTextFc[0].addTextChangedListener(new FcWatcher());

    }

    public TesteHelper(CronometroActivity activity, Teste teste) {
        this.teste = teste;

        SharedPreferences sharedPreferences = activity.getSharedPreferences("VARIAVEIS_DO_PACIENTE_" +
                teste.getPaciente().getId(), Context.MODE_PRIVATE);

        boolean fcDurante = sharedPreferences.getBoolean("durante_fc", false);
        boolean spDurante = sharedPreferences.getBoolean("durante_spo2", false);
        boolean fadDurante = sharedPreferences.getBoolean("durante_fadiga", false);
        boolean dispDurante = sharedPreferences.getBoolean("durante_dispneia", false);

        edTextFc[1] = (EditText) activity.findViewById(R.id.edTextFC1);
        edTextFc[2] = (EditText) activity.findViewById(R.id.edTextFC2);
        edTextFc[3] = (EditText) activity.findViewById(R.id.edTextFC3);
        edTextFc[4] = (EditText) activity.findViewById(R.id.edTextFC4);
        edTextFc[5] = (EditText) activity.findViewById(R.id.edTextFC5);
        edTextFc[6] = (EditText) activity.findViewById(R.id.edTextFC6);

        edTextSp[1] = (EditText) activity.findViewById(R.id.edTextSp1);
        edTextSp[2] = (EditText) activity.findViewById(R.id.edTextSp2);
        edTextSp[3] = (EditText) activity.findViewById(R.id.edTextSp3);
        edTextSp[4] = (EditText) activity.findViewById(R.id.edTextSp4);
        edTextSp[5] = (EditText) activity.findViewById(R.id.edTextSp5);
        edTextSp[6] = (EditText) activity.findViewById(R.id.edTextSp6);

        edTextDisp[1] = (EditText) activity.findViewById(R.id.edTextDisp1);
        edTextDisp[2] = (EditText) activity.findViewById(R.id.edTextDisp2);
        edTextDisp[3] = (EditText) activity.findViewById(R.id.edTextDisp3);
        edTextDisp[4] = (EditText) activity.findViewById(R.id.edTextDisp4);
        edTextDisp[5] = (EditText) activity.findViewById(R.id.edTextDisp5);
        edTextDisp[6] = (EditText) activity.findViewById(R.id.edTextDisp6);

        edTextFad[1] = (EditText) activity.findViewById(R.id.edTextFad1);
        edTextFad[2] = (EditText) activity.findViewById(R.id.edTextFad2);
        edTextFad[3] = (EditText) activity.findViewById(R.id.edTextFad3);
        edTextFad[4] = (EditText) activity.findViewById(R.id.edTextFad4);
        edTextFad[5] = (EditText) activity.findViewById(R.id.edTextFad5);
        edTextFad[6] = (EditText) activity.findViewById(R.id.edTextFad6);


        for (int i = 1; i <= 6; i++) {
            if (!fcDurante) {
                edTextFc[i].setVisibility(View.GONE);
            } else {
                break;
            }
        }
        for (int i = 1; i <= 6; i++) {
            if (!spDurante) {
                edTextSp[i].setVisibility(View.GONE);
            } else {
                break;
            }
        }
        for (int i = 1; i <= 6; i++) {
            if (!fadDurante) {
                edTextFad[i].setVisibility(View.GONE);
            } else {
                break;
            }
        }
        for (int i = 1; i <= 6; i++) {
            if (!dispDurante) {
                edTextDisp[i].setVisibility(View.GONE);
            } else {
                break;
            }
        }

    }

    public TesteHelper(ValoresFinaisActivity activity, Teste teste) {
        this.teste = teste;
        SharedPreferences sharedPreferences = activity.getSharedPreferences("VARIAVEIS_DO_PACIENTE_" +
                teste.getPaciente().getId(), Context.MODE_PRIVATE);

        //FC final obrigatório
        edTextFc[7] = (EditText) activity.findViewById(R.id.edTextFC7);

        edTextDisp[7] = (EditText) activity.findViewById(R.id.edTextDisp7);
        if (!sharedPreferences.getBoolean("final_dispneia", false)) {
            activity.findViewById(R.id.layout_dispneia).setVisibility(View.GONE);
        }
        edTextFad[7] = (EditText) activity.findViewById(R.id.edTextFad7);
        if (!sharedPreferences.getBoolean("final_fadiga", false)) {
            activity.findViewById(R.id.layout_fadiga).setVisibility(View.GONE);
        }

        edTextGc[1] = (EditText) activity.findViewById(R.id.edTextGC1);
        if (!sharedPreferences.getBoolean("final_gc", false)) {
            activity.findViewById(R.id.layout_gc).setVisibility(View.GONE);
        }

        edTextPAs[1] = (EditText) activity.findViewById(R.id.edTextPAS1);
        edTextPAd[1] = (EditText) activity.findViewById(R.id.edTextPAD1);
        if (!sharedPreferences.getBoolean("final_pa", false)) {
            activity.findViewById(R.id.layout_pa).setVisibility(View.GONE);
        }

        edTextObsFinal = (EditText) activity.findViewById(R.id.edTextObsFinal);


    }

    public TesteHelper(ValoresRecuperacaoActivity activity, Teste teste) {

        this.teste = teste;
        SharedPreferences sharedPreferences = activity.getSharedPreferences("VARIAVEIS_DO_PACIENTE_" +
                teste.getPaciente().getId(), Context.MODE_PRIVATE);

        edTextFc[8] = (EditText) activity.findViewById(R.id.edTextFC8);
        if (!sharedPreferences.getBoolean("repouso_fc", false)) {
            activity.findViewById(R.id.layout_fc).setVisibility(View.GONE);
        }
        edTextDisp[8] = (EditText) activity.findViewById(R.id.edTextDisp8);
        if (!sharedPreferences.getBoolean("repouso_dispneia", false)) {
            activity.findViewById(R.id.layout_dispneia).setVisibility(View.GONE);
        }
        edTextFad[8] = (EditText) activity.findViewById(R.id.edTextFad8);
        if (!sharedPreferences.getBoolean("repouso_fadiga", false)) {
            activity.findViewById(R.id.layout_fadiga).setVisibility(View.GONE);
        }

        edTextGc[2] = (EditText) activity.findViewById(R.id.edTextGC3);
        if (!sharedPreferences.getBoolean("repouso_gc", false)) {
            activity.findViewById(R.id.layout_gc).setVisibility(View.GONE);
        }

        edTextPAs[2] = (EditText) activity.findViewById(R.id.edTextPAS3);
        edTextPAd[2] = (EditText) activity.findViewById(R.id.edTextPAD3);
        if (!sharedPreferences.getBoolean("repouso_pa", false)) {
            activity.findViewById(R.id.layout_pa).setVisibility(View.GONE);
        }


    }

    public Teste pegaDadosFromFields(int minuto) {

        /*           ÍNDICES
        * 0 - VALORES BASAIS ACTIVITY
        * 1 - MINUTO 1 (CRONOMETRO ACTIVITY)
        * 2 - MINUTO 2 (CRONOMETRO ACTIVITY)
        * 3 - MINUTO 3 (CRONOMETRO ACTIVITY)
        * 4 - MINUTO 4 (CRONOMETRO ACTIVITY)
        * 5 - MINUTO 5 (CRONOMETRO ACTIVITY)
        * 6 - MINUTO 6 (CRONOMETRO ACTIVITY)
        * 7 - VALORES FINAIS ACTIVITY
        * 8 - VALORES RECUPERAÇÃO ACTIVITY
        */

        //fc, disp e fad são coletadas em todas as fases
        if (!edTextFc[minuto].getText().toString().isEmpty()) {
            System.out.println("entrou no if");
            teste.setFc(minuto, Integer.parseInt(edTextFc[minuto].getText().toString()));
        }

        if (!edTextDisp[minuto].getText().toString().isEmpty())
            teste.setDispneia(minuto, Double.valueOf(edTextDisp[minuto].getText().toString()));
        if (!(edTextFad[minuto].getText().toString()).isEmpty())
            teste.setFadiga(minuto, Double.valueOf(edTextFad[minuto].getText().toString()));

        //SPO2 VAI DE VALORES BASAIS ATÉ O MINUTO 6
        if (minuto < 7 && !edTextSp[minuto].getText().toString().isEmpty())
            teste.setSpO2(minuto, Integer.parseInt(edTextSp[minuto].getText().toString()));

        if (minuto == 0 || minuto == 7 || minuto == 8) {

            if ((minuto == 7) && !edTextObsFinal.getText().toString().isEmpty())
                teste.setObsFinal(edTextObsFinal.getText().toString());

            // O O2 SUPLEMENTAR É APENAS UM VALOR BASAL
            if (minuto == 0) {
                if (!edTextO2Supl.getText().toString().isEmpty())
                    teste.setO2Supl(Double.valueOf(edTextO2Supl.getText().toString()));
            }

            //mudando o valor de 7 e 8 para 1 e 2 para se adequar aos índices declarados DE GC E PA
            if (minuto == 7 || minuto == 8)
                minuto -= 6;
            //gc e pa sao coletados apenas basais, finais e recuperação
            if (!edTextGc[minuto].getText().toString().isEmpty())
                teste.setGc(minuto, Integer.parseInt(edTextGc[minuto].getText().toString()));

            if (!edTextPAs[minuto].getText().toString().isEmpty() && !edTextPAd[minuto].getText().toString().isEmpty()) {
                teste.setPAs(minuto, Integer.parseInt(edTextPAs[minuto].getText().toString()));
                teste.setPAd(minuto, Integer.parseInt(edTextPAd[minuto].getText().toString()));
            }
            //obs final da activity valores finais
        }

        return teste;
    }

    /**
     * Preenche os campos de Frequência Cardíaca Dispneia e Fadiga da activity valores finais
     * com os valores já inseridos nos campos de 6 minutos
     */

    public void preencheCamposFinais() {
        if (teste.getFc(6) != null)
            edTextFc[7].setText(teste.getFc(6));
        if (teste.getDispneia(6) != null)
            edTextDisp[7].setText(String.valueOf(teste.getDispneia(6)));
        if (teste.getFadiga(6) != null)
            edTextFad[7].setText(String.valueOf(teste.getFadiga(6)));
    }
}
