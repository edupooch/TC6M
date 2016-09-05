package br.edu.ufcspa.tc6m.formulas;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.controle.PreferenciasActivity;
import br.edu.ufcspa.tc6m.modelo.Formula;

/**
 * Created by edupooch on 12/07/2016.
 */
public class ListaFormulas {



    private List<Formula> formulas;

    private static final int ATIVADA = 1;
    private static final int DESATIVADA = 0;

    public static final int ID_BRITTO1 = 0;
    public static final int ID_BRITTO2 = 1;
    public static final int ID_DOURADO = 2;
    public static final int ID_SOARES_PEREIRA = 3;
    public static final int ID_IWAMA = 4;

    public ListaFormulas() {
        formulas = new ArrayList<>();

        /**
         * Para adicionar uma fórmula à lista:
         *
         * Formula nomeFormula = new Formula(
         * id,
         * autores,
         * equação,
         * coeficiente de exmplicação,
         * drawable da bandeira do país de origem
         );
         */

        Formula britto1 = new Formula(
                ID_BRITTO1,
                "Britto e cols., 2013",
                "890.46 - (6.11 * idade) + (0.0345 * idade²) + (48.87 * gênero) - (4.87 * IMC)",
                0.46,
                R.drawable.bandeira_brasil
        );
        formulas.add(ID_BRITTO1, britto1);

        Formula britto2 = new Formula(
                ID_BRITTO2,
                "Britto e cols., 2013",
                "356,658 - (2.303 * idade) + (36.648 * gênero) - (1.704 * estatura) + (1.305 * variação de FC)",
                0.62,
                R.drawable.bandeira_brasil
        );
        formulas.add(ID_BRITTO2, britto2);

        Formula dourado = new Formula(
                ID_DOURADO,
                "Dourado e cols., 2011",
                "299.296 - (2.728 * idade) - (2.16 * massa) + (361.731 * estatura) + (56.386 * genero)",
                0.54,
                R.drawable.bandeira_brasil
        );
        formulas.add(ID_DOURADO, dourado);

   Formula soaresPereira = new Formula(
                ID_SOARES_PEREIRA,
                "Soares e Pereira, 2011",
                "511 + (0.0066 * estatura²) - (0.030 * idade²) - (0.068 * IMC²)",
                0.55,
                R.drawable.bandeira_brasil
        );
        formulas.add(ID_SOARES_PEREIRA, soaresPereira);

        Formula iwama = new Formula(
                ID_IWAMA,
                "Iwama e cols. 2009",
                "622.461 - (1.846 * idade) + (61.503 * gênero)",
                0.55,
                R.drawable.bandeira_brasil
        );
        formulas.add(ID_IWAMA, iwama);



    }

    public List<Formula> getFormulas() {
        return formulas;
    }
    public List<Formula> getFormulasSelecionadas(SharedPreferences sharedPref) {
        for (int i = 0; i<formulas.size(); i++){
            if (sharedPref.getInt("FORMULA_" + i, ATIVADA ) == DESATIVADA){
                formulas.remove(i);
            }
        }
        return formulas;
    }
}
