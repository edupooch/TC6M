package br.edu.ufcspa.tc6m.formulas;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Formula;

/**
 * Created by Particular on 12/07/2016.
 */
public class ListaFormulas {


    private List<Formula> formulas;

    public static final int ID_BRITTO1 = 0;
    public static final int ID_BRITTO2 = 1;
    public static final int ID_DOURADO = 2;

    public ListaFormulas() {
        formulas = new ArrayList<>();

        /**
         * Para adicionar uma fórmula à lista:
         *
         * Formula nomeFormula = new Formula(
         * autores,
         * equação,
         * coeficiente de exmplicação,
         * drawable da bandeira do país de origem
         );
         */

        Formula britto1 = new Formula(
                "Britto e cols., 2013",
                "890.46 - (6.11 * idade) + (0.0345 * idade²) + (48.87 * genero) - (4.87 * imc)",
                0.46,
                R.drawable.bandeira_brasil
        );
        formulas.add(ID_BRITTO1, britto1);

        Formula britto2 = new Formula(
                "Britto e cols., 2013",
                "356,658 - (2.303 * idade) + (36.648 * genero) - (1.704 * estatura) + (1.305 * variação de FC)",
                0.62,
                R.drawable.bandeira_brasil
        );
        formulas.add(ID_BRITTO2, britto2);

        Formula dourado = new Formula(
                "Dourado e cols., 2011",
                "299.296 - (2.728 * idade) - (2.16 * massa) + (361.731 * estatura) + (56.386 * genero)",
                0.54,
                R.drawable.bandeira_brasil
        );
        formulas.add(ID_DOURADO, dourado);


    }

    public List<Formula> getFormulas() {
        return formulas;
    }
}
