package br.edu.ufcspa.tc6m.controle;

import android.widget.EditText;

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
    private EditText[] edTextSp = new EditText[7];
    private EditText[] edTextDisp = new EditText[9];
    private EditText[] edTextFad = new EditText[9];
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

        edTextFc[0] = (EditText) activity.findViewById(R.id.edTextFC0);
        edTextSp[0] = (EditText) activity.findViewById(R.id.edTextSp0);
        edTextDisp[0] = (EditText) activity.findViewById(R.id.edTextDisp0);
        edTextFad[0] = (EditText) activity.findViewById(R.id.edTextFad0);
        edTextGc[0] = (EditText) activity.findViewById(R.id.edTextGC0);
        edTextPa[0] = (EditText) activity.findViewById(R.id.editTextPA0);
        edTextO2Supl = (EditText) activity.findViewById(R.id.edTextO2Supl);

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

    public TesteHelper(ValoresFinaisActivity activity, Teste teste){
        this.teste = teste;

        edTextFc[7] = (EditText) activity.findViewById(R.id.edTextFc7);
        edTextDisp[7] = (EditText) activity.findViewById(R.id.edTextDisp7);
        edTextFad[7] = (EditText) activity.findViewById(R.id.edTextFad7);
        edTextGc[1] = (EditText) activity.findViewById(R.id.edTextGc1);
        edTextPa[1] = (EditText) activity.findViewById(R.id.edTextPa1);
        edTextObsFinal = (EditText) activity.findViewById(R.id.edTextObsFinal);

    }

    public Teste pegaDadosFromFields(int minuto) {
        teste.setFc(minuto, edTextFc[minuto].getText().toString());
        teste.setSpO2(minuto, edTextSp[minuto].getText().toString());
        teste.setDispneia(minuto, edTextDisp[minuto].getText().toString());
        teste.setFadiga(minuto, edTextFad[minuto].getText().toString());

        if (minuto == 0) {
            teste.setGc(minuto, edTextGc[minuto].getText().toString());
            teste.setPa(minuto, edTextPa[minuto].getText().toString());
            teste.setO2Supl(edTextO2Supl.getText().toString());
        }
        return teste;
    }
}
