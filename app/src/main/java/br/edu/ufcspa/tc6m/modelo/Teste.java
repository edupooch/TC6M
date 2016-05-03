package br.edu.ufcspa.tc6m.modelo;

import java.io.Serializable;
import java.sql.Time;

import java.util.GregorianCalendar;

/**
 * Created by edupooch on 22/12/15.
 */
public class Teste implements Serializable {

    private Paciente paciente;

    private Long idPaciente;

    private Time horario;

    private GregorianCalendar data;

    // SIGNIFICADO DOS ÍNDICES DOS ARRAYS COMENTADOS AO LADO DE CADA UM

    private String[] fc = new String[9]; //0=BASAL, 1-6=MINUTOS, 7=FINAL, 8=RECUPERAÇÃO
    private String[] dispneia = new String[9]; //0=BASAL, 1-6=MINUTOS, 7=FINAL, 8=RECUPERAÇÃO
    private String[] fadiga = new String[9]; //0=BASAL, 1-6=MINUTOS, 7=FINAL, 8=RECUPERAÇÃO

    private String[] spO2 = new String[7]; //0=BASAL, 1-6=MINUTOS

    private String[] pa = new String[3]; //0=BASAL, 1=FINAL, 2=RECUPERAÇÃO
    private String[] gc = new String[3]; //0=BASAL, 1=FINAL, 2=RECUPERAÇÃO

    private String o2Supl;//BASAL

    private int[] voltas = new int[5]; //0-5=MINUTOS

    private int nParadas;
    private String tempoParadas;
    private String motivoParadas;

    private double massa;
    private double estatura;

    private String obsFinal;

    private double distanciaPercorrida;
    private double dpEstimada1;
    private double dpEstimada2;
    private double dpPorcento1;
    private double dpPorcento2;

    public Teste(Paciente paciente) {
        this.paciente = paciente;
        this.idPaciente = paciente.getId();
    }


    public Paciente getPaciente() {
        return paciente;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public Time getHorario() {
        return horario;
    }

    public void setHorario(Time horario) {
        this.horario = horario;
    }

    public GregorianCalendar getData() {
        return data;
    }

    public void setData(GregorianCalendar data) {
        this.data = data;
    }

    public String getFc(int i) {
        return fc[i];
    }

    public void setFc(int i, String fc) {
        this.fc[i] = fc;
    }

    public String getDispneia(int i) {
        return dispneia[i];
    }

    public void setDispneia(int i, String dispneia) {
        this.dispneia[i] = dispneia;
    }

    public String getFadiga(int i) {
        return fadiga[i];
    }

    public void setFadiga(int i, String fadiga) {
        this.fadiga[i] = fadiga;
    }

    public String getSpO2(int i) {
        return spO2[i];
    }

    public void setSpO2(int i, String spO2) {
        this.spO2[i] = spO2;
    }

    public String getPa(int i) {
        return pa[i];
    }

    public void setPa(int i, String pa) {
        this.pa[i] = pa;
    }

    public String getGc(int i) {
        return gc[i];
    }

    public void setGc(int i, String gc) {
        this.gc[i] = gc;
    }

    public String getO2Supl() {
        return o2Supl;
    }

    public void setO2Supl(String o2Supl) {
        this.o2Supl = o2Supl;
    }

    public int getVoltas(int i) {
        return voltas[i];
    }

    public void setVoltas(int i, int voltas) {
        this.voltas[i] = voltas;
    }

    public int getnParadas() {
        return nParadas;
    }

    public void setnParadas(int nParadas) {
        this.nParadas = nParadas;
    }

    public String getTempoParadas() {
        return tempoParadas;
    }

    public void setTempoParadas(String tempoParadas) {
        this.tempoParadas = tempoParadas;
    }

    public String getMotivoParadas() {
        return motivoParadas;
    }

    public void setMotivoParadas(String motivoParadas) {
        this.motivoParadas = motivoParadas;
    }

    public double getMassa() {
        return massa;
    }

    public void setMassa(double massa) {
        this.massa = massa;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public double getDistanciaPercorrida() {
        return distanciaPercorrida;
    }

    public void setDistanciaPercorrida(double distanciaPercorrida) {
        this.distanciaPercorrida = distanciaPercorrida;
    }

    public double getDpEstimada1() {
        return dpEstimada1;
    }

    public void setDpEstimada1(double dpEstimada1) {
        this.dpEstimada1 = dpEstimada1;
    }

    public double getDpEstimada2() {
        return dpEstimada2;
    }

    public void setDpEstimada2(double dpEstimada2) {
        this.dpEstimada2 = dpEstimada2;
    }

    public double getDpPorcento1() {
        return dpPorcento1;
    }

    public void setDpPorcento1(double dpPorcento1) {
        this.dpPorcento1 = dpPorcento1;
    }

    public double getDpPorcento2() {
        return dpPorcento2;
    }

    public void setDpPorcento2(double dpPorcento2) {
        this.dpPorcento2 = dpPorcento2;
    }

    public String getObsFinal() {
        return obsFinal;
    }

    public void setObsFinal(String obsFinal) {
        this.obsFinal = obsFinal;
    }
}
