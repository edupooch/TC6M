package br.edu.ufcspa.tc6m.controle;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Years;

import java.util.Calendar;
import java.util.GregorianCalendar;

import br.edu.ufcspa.tc6m.formulas.ListaFormulas;
import br.edu.ufcspa.tc6m.modelo.Formula;

/**
 * Created by edupooch on 12/06/2016.
 * Classe para efetuar os cálculos necessários durante o teste, como idade e fórmulas de dp estimada.
 */

public class Calcula {

    /**
     * Calcula a idade em anos
     * @param dataNasc recebe a data de nascimento de paciente.getData()
     * @return idade em anos
     */
    public static int idade(java.util.Date dataNasc) {

        Calendar calDataNascimento = new GregorianCalendar();
        calDataNascimento.setTime(dataNasc);
        Calendar hoje = Calendar.getInstance();

        int idadeAnos = hoje.get(Calendar.YEAR) - calDataNascimento.get(Calendar.YEAR);
        calDataNascimento.add(Calendar.YEAR, idadeAnos);
        if (hoje.before(calDataNascimento)) idadeAnos--;

        return idadeAnos;

    }

    /**
     * Retorna a idade em anos meses e dias como String para o perfil do paciente
     * @param dataNasc data de nascimento
     * @return Idade em anos meses e dias
     */

    public static String idadeCompleta(java.util.Date dataNasc) {

        LocalDate birthdate = new LocalDate(dataNasc);
        LocalDate now = new LocalDate();

        Period period = new Period(birthdate, now, PeriodType.yearMonthDay());

        String anos = period.getYears() + " anos, ";
        if (period.getYears() == 1){
            anos = period.getYears() + " ano, ";
        }

        String meses = period.getMonths() + " meses e ";
        if (period.getMonths() == 1){
            meses = period.getMonths() + "mês  ";
        }

        String dias = period.getDays() + " dias";
        if (period.getDays() == 1){
            dias = period.getDays() + " dia";
        }

        return anos + meses + dias;

    }

    /**
     * Cálculo do imc do paciente
     *
     * @param massa em kg
     * @param estatura em cm
     * @return imc
     */

    public static double imc(double massa, double estatura) {
        return massa / Math.pow(estatura / 100, 2);
    }

    /**
     * Calcula as distâncias percorrida estimadas de acordo com cada fórmula
     *
     * @param formula objeto do tipo formula usado para identificar a equação usada
     * @param idade em anos
     * @param genero 0 feminino e 1 masculino
     * @param massa em kg
     * @param estatura em cm
     * @param variacaoFc fc final - basal (7 - 0)
     * @return dpEstimada em metros
     */
    public static double dpEstimada(Formula formula, int idade, int genero, double massa, double estatura, int variacaoFc) {
        switch (formula.getIdFormula()) {
            /**
             * Cálculo da distancia percorrida estimada de 6 minutos - Equação 1: Britto e cols., 2013
             * Utiliza a idade, o gênero 1 para masculino e 0 para feminino e o imc
             * Coeficiente de explicação = 0,46
             *
             * Britto RR, Probst VS, Andrade AF, Samora GA, Hernandes NA, Marinho PE, et al. Reference equations
             * for the six-minute walk distance based on a Brazilian multicenter study. Braz J Phys Ther. 2013 Nov-
             * Dec;17(6):556-63.
             */
            case ListaFormulas.ID_BRITTO1:
                return (890.46 - (6.11 * idade) + (0.0345 * idade * idade) + (48.87 * genero) - (4.87 * imc(massa, estatura)));


            /**
             * Cálculo da distancia percorrida estimada de 6 minutos - Equação 2: Britto e cols., 2013
             * Utiliza a idade, o gênero 1 para masculino e 0 para feminino, a estatura em cm e a variação de FC (final - basal)
             * Coeficiente de explicação = 0,62
             *
             * Britto RR, Probst VS, Andrade AF, Samora GA, Hernandes NA, Marinho PE, et al. Reference equations
             * for the six-minute walk distance based on a Brazilian multicenter study. Braz J Phys Ther. 2013 Nov-
             * Dec;17(6):556-63.
             */
            case ListaFormulas.ID_BRITTO2:
                return (356.658 - (2.303 * idade) + (36.648 * genero) + (1.704 * estatura) + (1.365 * variacaoFc));

            /**
             * Cálculo da distancia percorrida estimada de 6 minutos - Dourado
             * Utiliza a idade, o gênero 1 para masculino e 0 para feminino, massa em kg e estatura em metros
             * Coeficiente de explicação = 0,54
             *
             * Dourado VZ, Vidotto MC, Guerra RL. Equações de referência para os testes de caminhada
             * de campo em adultos saudáveis. J.Bras Pneumol 2011;37(5):607-14.
             */
            case ListaFormulas.ID_DOURADO:
                return (299.296 - (2.728 * idade) - (2.16 * massa) + (361.731 * (estatura / 100)) + (56.386 * genero));

            /**
             * Cálculo da distancia percorrida estimada de 6 minutos - Soares e Pereira
             * Utiliza a idade, altura em cm e o imc
             * Coeficiente de explicação = 0,55
             *
             * Soares MR, Pereira CAC. Six-minute walk test: reference values for healthy adults in Brazil. J Bras Pneu-
             * mol. 2011 Sep-Oct;37(5):576-83.
             */
            case ListaFormulas.ID_SOARES_PEREIRA:
                return (511 + (0.0066 * Math.pow(estatura, 2)) - (0.030 * Math.pow(idade, 2)) - (0.068 * Math.pow(imc(massa, estatura), 2)));

            /**
             * Cálculo da distancia percorrida estimada de 6 minutos - Iwama e colaboradores.
             * Utiliza a idade em anos e o gênero.
             * Coeficiente de explicação = 0,30
             *
             * Iwama AM, Andrade GN, Shima P, Tanni SE, Godoy I, Dourado VZ. The six-minute walk test and body
             * weight-walk distance product in healthy Brazilian subjects. Braz J Med Biol Res. 2009 Nov;42(11):1080-5.
             *
             */
            case ListaFormulas.ID_IWAMA:
                return (622.461 - (1.846 * idade) + (61.503 * genero));

        }


        return 0;
    }


    public static double porcentagem(double distanciaPercorrida, double distanciaEstimada) {
        return (distanciaPercorrida / distanciaEstimada) * 100;
    }

    /**
     * Calcula a velocidade média com5 base no tempo de 6 minutos(360s)
     *
     * @param distanciaPercorrida em metros no teste
     * @return velocidadeMedia em m/s
     */
    public static double velocidadeMedia(int distanciaPercorrida) {
        return (double) distanciaPercorrida / 360;
    }
}
