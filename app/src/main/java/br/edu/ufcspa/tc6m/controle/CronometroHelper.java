package br.edu.ufcspa.tc6m.controle;

import android.widget.EditText;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Teste;

/**
 * Created by edupooch on 30/04/16.
 */
public class CronometroHelper {

    private int[] i = new int[2];
    private EditText[] Fc = new EditText[7];
    private EditText[] Sp = new EditText[7];
    private EditText[] Disp = new EditText[7];
    private EditText[] Fad = new EditText[7];

    public CronometroHelper(CronometroActivity activity){
        Teste teste = new Teste();

        Fc[1] = (EditText) activity.findViewById(R.id.edTextFC1);
        Fc[2] = (EditText) activity.findViewById(R.id.edTextFC2);
        Fc[3] = (EditText) activity.findViewById(R.id.edTextFC3);
        Fc[4] = (EditText) activity.findViewById(R.id.edTextFC4);
        Fc[5] = (EditText) activity.findViewById(R.id.edTextFC5);
        Fc[6] = (EditText) activity.findViewById(R.id.edTextFC6);

        Sp[1] = (EditText) activity.findViewById(R.id.edTextSp1);
        Sp[2] = (EditText) activity.findViewById(R.id.edTextSp2);
        Sp[3] = (EditText) activity.findViewById(R.id.edTextSp3);
        Sp[4] = (EditText) activity.findViewById(R.id.edTextSp4);
        Sp[5] = (EditText) activity.findViewById(R.id.edTextSp5);
        Sp[6] = (EditText) activity.findViewById(R.id.edTextSp6);

        Disp[1] = (EditText)activity.findViewById(R.id.edTextDisp1);
        Disp[2] = (EditText)activity.findViewById(R.id.edTextDisp2);
        Disp[3] = (EditText)activity.findViewById(R.id.edTextDisp3);
        Disp[4] = (EditText)activity.findViewById(R.id.edTextDisp4);
        Disp[5] = (EditText)activity.findViewById(R.id.edTextDisp5);
        Disp[6] = (EditText)activity.findViewById(R.id.edTextDisp6);

        Fad[1] = (EditText) activity.findViewById(R.id.edTextFad1);
        Fad[2] = (EditText) activity.findViewById(R.id.edTextFad2);
        Fad[3] = (EditText) activity.findViewById(R.id.edTextFad3);
        Fad[4] = (EditText) activity.findViewById(R.id.edTextFad4);
        Fad[5] = (EditText) activity.findViewById(R.id.edTextFad5);
        Fad[6] = (EditText) activity.findViewById(R.id.edTextFad6);

    }

    public void salvarDados(int minuto){


    }
}
