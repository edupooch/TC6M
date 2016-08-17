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

    public static final int BASAL = 0;

    private Teste teste;
    //0=BASAL, 1-5=MINUTOS, 6=FINAL, 7=RECUPERAÇÃO 1', 8=RECUPERAÇÃO2'
    private EditText[] edTextFc = new EditText[9];
    //0=BASAL, 1-5=MINUTOS, 6=ID_FC_FINAL, 7=RECUPERAÇÃO
    private EditText[] edTextDisp = new EditText[8];
    private EditText[] edTextFad = new EditText[8];
    private EditText[] edTextSp = new EditText[8];
    //0=BASAL, 1=FINAL, 2=RECUPERAÇÃO
    private EditText[] edTextPAs = new EditText[3];
    private EditText[] edTextPAd = new EditText[3];
    private EditText[] edTextGc = new EditText[3];
    //0=BASAL, 1=FINAL
    private EditText[] edTextO2Supl = new EditText[2];
    //FINAL
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
        edTextFc[BASAL] = (EditText) activity.findViewById(R.id.edTextFC0);

        edTextDisp[BASAL] = (EditText) activity.findViewById(R.id.edTextDisp0);

        if (!sharedPreferences.getBoolean("basal_dispneia", false)) {
            activity.findViewById(R.id.layout_dispneia).setVisibility(View.GONE);
        }
        edTextFad[BASAL] = (EditText) activity.findViewById(R.id.edTextFad0);
        if (!sharedPreferences.getBoolean("basal_fadiga", false)) {
            activity.findViewById(R.id.layout_fadiga).setVisibility(View.GONE);
        }

        edTextSp[BASAL] = (EditText) activity.findViewById(R.id.edTextSp0);
        if (!sharedPreferences.getBoolean("basal_spo2", false)) {
            activity.findViewById(R.id.layout_spo2).setVisibility(View.GONE);
        }
        edTextGc[BASAL] = (EditText) activity.findViewById(R.id.edTextGC0);
        if (!sharedPreferences.getBoolean("basal_gc", false)) {
            activity.findViewById(R.id.layout_gc).setVisibility(View.GONE);
        }

        edTextPAs[BASAL] = (EditText) activity.findViewById(R.id.editTextPAS0);
        edTextPAd[BASAL] = (EditText) activity.findViewById(R.id.editTextPAD0);
        if (!sharedPreferences.getBoolean("basal_pa", false)) {
            activity.findViewById(R.id.layout_pa).setVisibility(View.GONE);
        }

        edTextO2Supl[BASAL] = (EditText) activity.findViewById(R.id.edTextO2Supl);
        if (!sharedPreferences.getBoolean("basal_o2supl", false)) {
            activity.findViewById(R.id.layout_o2supl).setVisibility(View.GONE);
        }

    }

    public TesteHelper(CronometroActivity activity, Teste teste) {
        this.teste = teste;

        SharedPreferences sharedPreferences = activity.getSharedPreferences("VARIAVEIS_DO_PACIENTE_" +
                teste.getPaciente().getId(), Context.MODE_PRIVATE);

        boolean fcDurante = sharedPreferences.getBoolean("durante_fc", false);
        boolean spDurante = sharedPreferences.getBoolean("durante_spo2", false);
        boolean fadDurante = sharedPreferences.getBoolean("durante_fadiga", false);
        boolean dispDurante = sharedPreferences.getBoolean("durante_dispneia", false);

        //// TODO: 16/08/2016 Colocar dentro do for
        edTextFc[1] = (EditText) activity.findViewById(R.id.edTextFC1);
        edTextFc[2] = (EditText) activity.findViewById(R.id.edTextFC2);
        edTextFc[3] = (EditText) activity.findViewById(R.id.edTextFC3);
        edTextFc[4] = (EditText) activity.findViewById(R.id.edTextFC4);
        edTextFc[5] = (EditText) activity.findViewById(R.id.edTextFC5);

        edTextSp[1] = (EditText) activity.findViewById(R.id.edTextSp1);
        edTextSp[2] = (EditText) activity.findViewById(R.id.edTextSp2);
        edTextSp[3] = (EditText) activity.findViewById(R.id.edTextSp3);
        edTextSp[4] = (EditText) activity.findViewById(R.id.edTextSp4);
        edTextSp[5] = (EditText) activity.findViewById(R.id.edTextSp5);

        edTextDisp[1] = (EditText) activity.findViewById(R.id.edTextDisp1);
        edTextDisp[2] = (EditText) activity.findViewById(R.id.edTextDisp2);
        edTextDisp[3] = (EditText) activity.findViewById(R.id.edTextDisp3);
        edTextDisp[4] = (EditText) activity.findViewById(R.id.edTextDisp4);
        edTextDisp[5] = (EditText) activity.findViewById(R.id.edTextDisp5);

        edTextFad[1] = (EditText) activity.findViewById(R.id.edTextFad1);
        edTextFad[2] = (EditText) activity.findViewById(R.id.edTextFad2);
        edTextFad[3] = (EditText) activity.findViewById(R.id.edTextFad3);
        edTextFad[4] = (EditText) activity.findViewById(R.id.edTextFad4);
        edTextFad[5] = (EditText) activity.findViewById(R.id.edTextFad5);


        for (int i = 1; i < 6; i++) {
            if (!fcDurante) {
                edTextFc[i].setVisibility(View.GONE);
            } else {
                break;
            }
        }
        for (int i = 1; i < 6; i++) {
            if (!spDurante) {
                edTextSp[i].setVisibility(View.GONE);
            } else {
                break;
            }
        }
        for (int i = 1; i < 6; i++) {
            if (!fadDurante) {
                edTextFad[i].setVisibility(View.GONE);
            } else {
                break;
            }
        }
        for (int i = 1; i < 6; i++) {
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
        edTextFc[6] = (EditText) activity.findViewById(R.id.edTextFC6);

        edTextSp[6] = (EditText) activity.findViewById(R.id.edTextSp6);
        if (!sharedPreferences.getBoolean("final_spo2", false)) {
            activity.findViewById(R.id.layout_spo2).setVisibility(View.GONE);
        }

        edTextDisp[6] = (EditText) activity.findViewById(R.id.edTextDisp6);
        if (!sharedPreferences.getBoolean("final_dispneia", false)) {
            activity.findViewById(R.id.layout_dispneia).setVisibility(View.GONE);
        }
        edTextFad[6] = (EditText) activity.findViewById(R.id.edTextFad6);
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

        edTextO2Supl[1] = (EditText) activity.findViewById(R.id.edTextO2Supl);
        if (!sharedPreferences.getBoolean("final_o2supl", false)) {
            activity.findViewById(R.id.layout_o2supl).setVisibility(View.GONE);
        }
        edTextObsFinal = (EditText) activity.findViewById(R.id.edTextObsFinal);


    }

    public TesteHelper(ValoresRecuperacaoActivity activity, Teste teste) {

        this.teste = teste;
        SharedPreferences sharedPreferences = activity.getSharedPreferences("VARIAVEIS_DO_PACIENTE_" +
                teste.getPaciente().getId(), Context.MODE_PRIVATE);


        edTextFc[8] = (EditText) activity.findViewById(R.id.edTextFC8);
        edTextFc[7] = (EditText) activity.findViewById(R.id.edTextFC7);
        if (!sharedPreferences.getBoolean("repouso_fc", false)) {
            activity.findViewById(R.id.layout_fc).setVisibility(View.GONE);
        }
        edTextSp[7] = (EditText) activity.findViewById(R.id.edTextSp7);
        if (!sharedPreferences.getBoolean("repouso_spo2", false)) {
            activity.findViewById(R.id.layout_spo2).setVisibility(View.GONE);
        }
        edTextDisp[7] = (EditText) activity.findViewById(R.id.edTextDisp7);
        if (!sharedPreferences.getBoolean("repouso_dispneia", false)) {
            activity.findViewById(R.id.layout_dispneia).setVisibility(View.GONE);
        }
        edTextFad[7] = (EditText) activity.findViewById(R.id.edTextFad7);
        if (!sharedPreferences.getBoolean("repouso_fadiga", false)) {
            activity.findViewById(R.id.layout_fadiga).setVisibility(View.GONE);
        }

        edTextGc[2] = (EditText) activity.findViewById(R.id.edTextGC2);
        if (!sharedPreferences.getBoolean("repouso_gc", false)) {
            activity.findViewById(R.id.layout_gc).setVisibility(View.GONE);
        }

        edTextPAs[2] = (EditText) activity.findViewById(R.id.edTextPAS2);
        edTextPAd[2] = (EditText) activity.findViewById(R.id.edTextPAD2);
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
        * 6 - VALORES FINAIS ACTIVITY
        * 7 - VALORES RECUPERAÇÃO ACTIVITY
        */

        //fc, spo2, disp e fad são coletadas em todas as fases
        if (!edTextFc[minuto].getText().toString().isEmpty()) {
            teste.setFc(minuto, Integer.parseInt(edTextFc[minuto].getText().toString()));
            if (minuto == 7) // pegar o valor de recuperação 1' e 2'(fc[8])
                teste.setFc(minuto + 1, Integer.parseInt(edTextFc[minuto + 1].getText().toString()));
        }
        if (!edTextDisp[minuto].getText().toString().isEmpty())
            teste.setDispneia(minuto, Double.valueOf(edTextDisp[minuto].getText().toString()));
        if (!(edTextFad[minuto].getText().toString()).isEmpty())
            teste.setFadiga(minuto, Double.valueOf(edTextFad[minuto].getText().toString()));
        if (!edTextSp[minuto].getText().toString().isEmpty())
            teste.setSpO2(minuto, Integer.parseInt(edTextSp[minuto].getText().toString()));

        if (minuto == 0 || minuto == 6 || minuto == 7) {

            if ((minuto == 6) && !edTextObsFinal.getText().toString().isEmpty())
                teste.setObsFinal(edTextObsFinal.getText().toString());

            //mudando o valor de 7 e 8 para 1 e 2 para se adequar aos índices declarados DE GC E PA
            if (minuto == 6 || minuto == 7)
                minuto -= 5;
            // O O2 SUPLEMENTAR É APENAS BASAL e FINAL =1| SEM VALOR DE RECUPERAÇÃO = 2
            if (minuto != 2) {
                if (!edTextO2Supl[minuto].getText().toString().isEmpty())
                    teste.setO2Supl(minuto, Double.valueOf(edTextO2Supl[minuto].getText().toString()));
            }

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

}
