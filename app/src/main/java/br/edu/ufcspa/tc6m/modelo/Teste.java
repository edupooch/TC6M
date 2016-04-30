package br.edu.ufcspa.tc6m.modelo;

import java.io.Serializable;

/**
 * Created by edupooch on 22/12/15.
 */
public class Teste implements Serializable {

    private Long idPaciente;

    private String horario;
    private String data;

    // SIGNIFICADO DOS ÍNDICES DOS ARRAYS COMENTADOS AO LADO DE CADA UM

    private String[] fc = new String[9]; //0=BASAL, 1-6=MINUTOS, 7=FINAL, 8=RECUPERAÇÃO
    private String[] pa = new String[3]; //0=BASAL, 1=FINAL, 2=RECUPERAÇÃO
    private String[] gc = new String[3]; //0=BASAL, 1=FINAL, 2=RECUPERAÇÃO
    private String[] SpO2 = new String[3]; //0=BASAL, 1-6=MINUTOS

    private String o2SuplBasal;

    private String dispneiaBasal;
    private String dispneiaFinal;
    private String dispneiaRecuperacao;
    private String dispneia1;
    private String dispneia2;
    private String dispneia3;
    private String dispneia4;
    private String dispneia5;
    private String dispneia6;

    private String fadigaBasal;
    private String fadigaFinal;
    private String fadigaRecuperacao;
    private String fadiga1;
    private String fadiga2;
    private String fadiga3;
    private String fadiga4;
    private String fadiga5;
    private String fadiga6;

    private int voltas1;
    private int voltas2;
    private int voltas3;
    private int voltas4;
    private int voltas5;
    private int voltas6;

    private int nParadas;
    private String tempoParadas;
    private String motivoParadas;

    private double distanciaPercorrida;
    private double dpEstimada1;
    private double dpEstimada2;
    private double dpPorcento1;
    private double dpPorcento2;

    public Teste() {

    }

}

