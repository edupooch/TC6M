package br.edu.ufcspa.tc6m.controle;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by edupooch on 12/06/2016.
 * Classe para efetuar os cálculos necessários durante o teste, como idade e fórmulas de dp estimada.
 */

public class Calcula {

     /*
     * Calculo da idade do paciente com base no dia do nascimento e no dia de hoje
     */

    public static int idade(java.util.Date dataNasc) {

        Calendar calendarData = new GregorianCalendar();
        calendarData.setTime(dataNasc);
        Calendar hoje = Calendar.getInstance();

        int idade = hoje.get(Calendar.YEAR) - calendarData.get(Calendar.YEAR);
        calendarData.add(Calendar.YEAR, idade);
        if (hoje.before(calendarData)) idade--;

        return idade;

    }

    /*
     * Calculo do imc com base na massa em kg e estatura em cm
     */

    public static double imc(double massa, double estatura) {
        return massa / Math.pow(estatura / 100, 2);
    }

    /*
     * Cálculo da distancia percorrida estimada de 6 minutos - Equação 1: Britto e cols., 2013
     * Utiliza a idade, o gênero 1 para masculino e 0 para feminino e o imc
     * Coeficiente de explicação = 0,46
     *
     * Britto RR, Probst VS, Andrade AF, Samora GA, Hernandes NA, Marinho PE, et al. Reference equations
     * for the six-minute walk distance based on a Brazilian multicenter study. Braz J Phys Ther. 2013 Nov-
     * Dec;17(6):556-63.
     *
     */
    public static double dpEstimadaBritto1(int idade, int genero, double massa, double estatura) {
        return (890.46 - (6.11 * idade) + (0.0345 * idade * idade) + (48.87 * genero) - (4.87 * imc(massa, estatura)));
    }

    /*
     * Cálculo da distancia percorrida estimada de 6 minutos - Equação 2: Britto e cols., 2013
     * Utiliza a idade, o gênero 1 para masculino e 0 para feminino, a estatura em cm e a variação de FC (final - basal)
     * Coeficiente de explicação = 0,62
     *
     * Britto RR, Probst VS, Andrade AF, Samora GA, Hernandes NA, Marinho PE, et al. Reference equations
     * for the six-minute walk distance based on a Brazilian multicenter study. Braz J Phys Ther. 2013 Nov-
     * Dec;17(6):556-63.
     */

    public static double dpEstimadaBritto2(int idade, int genero, double estatura, int variacaoFc) {
        return (356.658 - (2.303 * idade) + (36.648 * genero) - (1.704 * estatura) + (1.305 * variacaoFc));
    }


    /* Cálculo da distancia percorrida estimada de 6 minutos - Dourado
     * Utiliza a idade, o gênero 1 para masculino e 0 para feminino, massa em kg e estatura em metros
     * Coeficiente de explicação = 0,54
     *
     * Dourado VZ, Vidotto MC, Guerra RL. Equações de referência para os testes de caminhada
     * de campo em adultos saudáveis. J.Bras Pneumol 2011;37(5):607-14.
     *
     */

    public static double dpEstimadaDourado(int idade, int genero, double massa, double estatura) {
        return (299.296 - (2.728 * idade) - (2.16 * massa) + (361.731 * (estatura / 100)) + (56.386 * genero));
    }


    /*
     * Cálculo da distancia percorrida estimada de 6 minutos - Soares e Pereira
     * Utiliza a idade, altura em cm e o imc
     * Coeficiente de explicação = 0,55
     *
     * Soares MR, Pereira CAC. Six-minute walk test: reference values for healthy adults in Brazil. J Bras Pneu-
     * mol. 2011 Sep-Oct;37(5):576-83.
     */

    public static double dpEstimadaSoaresPereira(int idade, double massa, double estatura) {
        return (511 + (0.0066 * Math.pow(estatura, 2)) - (0.030 * Math.pow(idade, 2)) - (0.068 * Math.pow(imc(massa, estatura), 2)));
    }

     /*
     * Cálculo da distancia percorrida estimada de 6 minutos - Iwama e colaboradores.
     * Utiliza a idade em anos e o gênero.
     * Coeficiente de explicação = 0,30
     *
     */

    public static double dpEstimadaIwama(int idade, int genero) {
        return (622.461 - (1.846 * idade) + (61.503 * genero));
    }

    public static double porcentagem(double distanciaPercorrida, double distanciaEstimada) {
        return (distanciaPercorrida/distanciaEstimada)*100;
    }
}
