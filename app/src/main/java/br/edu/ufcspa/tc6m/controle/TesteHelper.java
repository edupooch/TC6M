package br.edu.ufcspa.tc6m.controle;

import android.renderscript.Double2;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Paciente;
import br.edu.ufcspa.tc6m.modelo.Teste;

/**
 * Created by edupooch on 30/04/16.
 */
public class TesteHelper {
    private Teste teste;
    //0=BASAL, 1-6=MINUTOS, 7=FINAL, 8=RECUPERAÇÃO
    private EditText[] edTextFc = new EditText[9];
    private EditText[] edTextDisp = new EditText[9];
    private EditText[] edTextFad = new EditText[9];
    private EditText[] edTextSp = new EditText[7];
    //0=BASAL, 1=FINAL, 2=RECUPERAÇÃO
    private EditText[] edTextPa = new EditText[3];
    private EditText[] edTextGc = new EditText[3];
    //BASAL
    private EditText edTextO2Supl;
    //FINAL
    private EditText edTextObsFinal;
    //VALORES


    public TesteHelper(ValoresBasaisActivity activity, Paciente paciente) {

        teste = new Teste(paciente);
        //Define os valores de massa e estatura no dia do teste - durante a activity valores basais;
        teste.setEstatura(paciente.getEstatura());
        teste.setMassa(paciente.getMassa());

        edTextFc[0] = (EditText) activity.findViewById(R.id.edTextFC0);
        edTextSp[0] = (EditText) activity.findViewById(R.id.edTextSp0);
        edTextDisp[0] = (EditText) activity.findViewById(R.id.edTextDisp0);
        edTextFad[0] = (EditText) activity.findViewById(R.id.edTextFad0);
        edTextGc[0] = (EditText) activity.findViewById(R.id.edTextGC0);
        edTextPa[0] = (EditText) activity.findViewById(R.id.editTextPA0);
        edTextO2Supl = (EditText) activity.findViewById(R.id.edTextO2Supl);

        //edTextFc[0].addTextChangedListener(new FcWatcher());

    }

    public TesteHelper(CronometroActivity activity, Teste teste) {
        this.teste = teste;

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

    }

    public TesteHelper(ValoresFinaisActivity activity, Teste teste) {
        this.teste = teste;

        edTextFc[7] = (EditText) activity.findViewById(R.id.edTextFc7);
        edTextDisp[7] = (EditText) activity.findViewById(R.id.edTextDisp7);
        edTextFad[7] = (EditText) activity.findViewById(R.id.edTextFad7);
        edTextGc[1] = (EditText) activity.findViewById(R.id.edTextGc1);
        edTextPa[1] = (EditText) activity.findViewById(R.id.edTextPa1);
        edTextObsFinal = (EditText) activity.findViewById(R.id.edTextObsFinal);

    }

    public TesteHelper(ValoresRecuperacaoActivity activity, Teste teste) {

        this.teste = teste;

        edTextFc[8] = (EditText) activity.findViewById(R.id.edTextFC8);
        edTextDisp[8] = (EditText) activity.findViewById(R.id.edTextDisp8);
        edTextFad[8] = (EditText) activity.findViewById(R.id.edTextFad8);
        edTextGc[2] = (EditText) activity.findViewById(R.id.edTextGc2);
        edTextPa[2] = (EditText) activity.findViewById(R.id.edTextPa2);

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
        if (!edTextFc[minuto].toString().isEmpty()) {
            try {
                teste.setFc(minuto, Integer.parseInt(edTextFc[minuto].getText().toString()));
            } catch (NumberFormatException e) {
            }
        }
        try {teste.setDispneia(minuto, Double.valueOf(edTextDisp[minuto].getText().toString()));} catch (NumberFormatException e) {}

        try {teste.setFadiga(minuto, Double.valueOf(edTextFad[minuto].getText().toString()));} catch (NumberFormatException e) {}

        if (minuto < 7) //SPO2 VAI DE VALORES BASAIS ATÉ O MINUTO 6
            try {teste.setSpO2(minuto, Integer.parseInt(edTextSp[minuto].getText().toString()));} catch (NumberFormatException e) {}

        if (minuto == 0 || minuto == 7 || minuto == 8) {
            if (minuto == 0) {

                try {
                    // O O2 SUPLEMENTAR É APENAS UM VALOR BASAL
                    teste.setO2Supl(Double.valueOf(edTextO2Supl.getText().toString()));
                } catch (NumberFormatException e) {
                }
            }
            if (minuto == 7 || minuto == 8) {//gc e pa sao coletados apenas basais, finais e recuperação
                minuto -= 6; //mudando o valor de 7 e 8 para 1 e 2 para se adequar aos índices declarados DE GC E PA
                try {teste.setGc(minuto, Integer.parseInt(edTextGc[minuto].getText().toString()));} catch (NumberFormatException e) {}
                teste.setPa(minuto, edTextPa[minuto].getText().toString());

                //obs final da activity valores finais
                if (minuto == 7) teste.setObsFinal(edTextObsFinal.getText().toString());
            }
        }

        return teste;
    }

    /**
     * Preenche os campos de Frequência Cardíaca Dispneia e Fadiga da activity valores finais
     * com os valores já inseridos nos campos de 6 minutos
     */

    public void preencheCamposFinais() {

        edTextFc[7].setText(String.valueOf(teste.getFc(6)));
        edTextDisp[7].setText(String.valueOf(teste.getDispneia(6)));
        if(teste.getFadiga(6) != null)
        edTextFad[7].setText(String.valueOf(teste.getFadiga(6)));


    }
}
