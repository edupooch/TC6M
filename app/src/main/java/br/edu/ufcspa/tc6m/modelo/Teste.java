package br.edu.ufcspa.tc6m.modelo;

/**
 * Created by edupooch on 22/12/15.
 */
public class Teste {

    private int dataTeste;
    private double PAantes;
    private double PAdepois;
    private double FCantes;
    private double FCdepois;
    private double SpO2antes;
    private double SpO2depois;
    private double borgDispneiaAntes;
    private double borgDispneiaDepois;
    private double borgFadigaAntes;
    private double borgFadigaDepois;
    private int voltas;
    private double distancia;
    private double percentilDistPrevista;
    private boolean testeConcluido;
    private String motivo;
    private String sinais;
    private double capacidadeFuncional;
    private int nivelCaminhada;
    private double picoVO2max;
    private double met;
    private String parametrosCV;

    public Teste() {

    }

    public Teste(int dataTeste, double PAantes, double PAdepois, double FCantes, double FCdepois, double spO2antes, double spO2depois, double borgDispneiaAntes, double borgDispneiaDepois, double borgFadigaAntes, double borgFadigaDepois, int voltas, double distancia, double percentilDistPrevista, boolean testeConcluido, String motivo, String sinais, double capacidadeFuncional, int nivelCaminhada, double picoVO2max, double met, String parametrosCV) {
        this.dataTeste = dataTeste;
        this.PAantes = PAantes;
        this.PAdepois = PAdepois;
        this.FCantes = FCantes;
        this.FCdepois = FCdepois;
        SpO2antes = spO2antes;
        SpO2depois = spO2depois;
        this.borgDispneiaAntes = borgDispneiaAntes;
        this.borgDispneiaDepois = borgDispneiaDepois;
        this.borgFadigaAntes = borgFadigaAntes;
        this.borgFadigaDepois = borgFadigaDepois;
        this.voltas = voltas;
        this.distancia = distancia;
        this.percentilDistPrevista = percentilDistPrevista;
        this.testeConcluido = testeConcluido;
        this.motivo = motivo;
        this.sinais = sinais;
        this.capacidadeFuncional = capacidadeFuncional;
        this.nivelCaminhada = nivelCaminhada;
        this.picoVO2max = picoVO2max;
        this.met = met;
        this.parametrosCV = parametrosCV;
    }




    public int getDataTeste() {
        return dataTeste;
    }

    public void setDataTeste(int dataTeste) {
        this.dataTeste = dataTeste;
    }

    public double getPAantes() {
        return PAantes;
    }

    public void setPAantes(double PAantes) {
        this.PAantes = PAantes;
    }

    public double getPAdepois() {
        return PAdepois;
    }

    public void setPAdepois(double PAdepois) {
        this.PAdepois = PAdepois;
    }

    public double getFCantes() {
        return FCantes;
    }

    public void setFCantes(double FCantes) {
        this.FCantes = FCantes;
    }

    public double getFCdepois() {
        return FCdepois;
    }

    public void setFCdepois(double FCdepois) {
        this.FCdepois = FCdepois;
    }

    public double getSpO2antes() {
        return SpO2antes;
    }

    public void setSpO2antes(double spO2antes) {
        SpO2antes = spO2antes;
    }

    public double getSpO2depois() {
        return SpO2depois;
    }

    public void setSpO2depois(double spO2depois) {
        SpO2depois = spO2depois;
    }

    public double getBorgDispneiaAntes() {
        return borgDispneiaAntes;
    }

    public void setBorgDispneiaAntes(double borgDispneiaAntes) {
        this.borgDispneiaAntes = borgDispneiaAntes;
    }

    public double getBorgDispneiaDepois() {
        return borgDispneiaDepois;
    }

    public void setBorgDispneiaDepois(double borgDispneiaDepois) {
        this.borgDispneiaDepois = borgDispneiaDepois;
    }

    public double getBorgFadigaAntes() {
        return borgFadigaAntes;
    }

    public void setBorgFadigaAntes(double borgFadigaAntes) {
        this.borgFadigaAntes = borgFadigaAntes;
    }

    public double getBorgFadigaDepois() {
        return borgFadigaDepois;
    }

    public void setBorgFadigaDepois(double borgFadigaDepois) {
        this.borgFadigaDepois = borgFadigaDepois;
    }

    public int getVoltas() {
        return voltas;
    }

    public void setVoltas(int voltas) {
        this.voltas = voltas;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getPercentilDistPrevista() {
        return percentilDistPrevista;
    }

    public void setPercentilDistPrevista(double percentilDistPrevista) {
        this.percentilDistPrevista = percentilDistPrevista;
    }

    public boolean isTesteConcluido() {
        return testeConcluido;
    }

    public void setTesteConcluido(boolean testeConcluido) {
        this.testeConcluido = testeConcluido;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getSinais() {
        return sinais;
    }

    public void setSinais(String sinais) {
        this.sinais = sinais;
    }

    public double getCapacidadeFuncional() {
        return capacidadeFuncional;
    }

    public void setCapacidadeFuncional(double capacidadeFuncional) {
        this.capacidadeFuncional = capacidadeFuncional;
    }

    public int getNivelCaminhada() {
        return nivelCaminhada;
    }

    public void setNivelCaminhada(int nivelCaminhada) {
        this.nivelCaminhada = nivelCaminhada;
    }

    public double getPicoVO2max() {
        return picoVO2max;
    }

    public void setPicoVO2max(double picoVO2max) {
        this.picoVO2max = picoVO2max;
    }

    public double getMet() {
        return met;
    }

    public void setMet(double met) {
        this.met = met;
    }

    public String getParametrosCV() {
        return parametrosCV;
    }

    public void setParametrosCV(String parametrosCV) {
        this.parametrosCV = parametrosCV;
    }
}
