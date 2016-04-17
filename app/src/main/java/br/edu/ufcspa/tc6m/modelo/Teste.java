package br.edu.ufcspa.tc6m.modelo;

import java.io.Serializable;

/**
 * Created by edupooch on 22/12/15.
 */
public class Teste implements Serializable {

    private Long idPaciente;

    private String horario;
    private String data;

    private String fcBasal;
    private String fcFinal;
    private String fcRecuperacao;
    private String fc1;
    private String fc2;
    private String fc3;
    private String fc4;
    private String fc5;
    private String fc6;

    private String paBasal;
    private String paFinal;
    private String paRecuperacao;

    private String gcBasal;
    private String gcFinal;
    private String gcRecuperacao;

    private String spoBasal;
    private String spo1;
    private String spo2;
    private String spo3;
    private String spo4;
    private String spo5;
    private String spo6;

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

    public Teste(Long idPaciente, String horario, String data, String fcBasal, String fcFinal, String fcRecuperacao, String fc1, String fc2, String fc3, String fc4, String fc5, String fc6, String paBasal, String paFinal, String paRecuperacao, String gcBasal, String gcFinal, String gcRecuperacao, String spoBasal, String spo1, String spo2, String spo3, String spo4, String spo5, String spo6, String o2SuplBasal, String dispneiaBasal, String dispneiaFinal, String dispneiaRecuperacao, String dispneia1, String dispneia2, String dispneia3, String dispneia4, String dispneia5, String dispneia6, String fadigaBasal, String fadigaFinal, String fadigaRecuperacao, String fadiga1, String fadiga2, String fadiga3, String fadiga4, String fadiga5, String fadiga6, int voltas1, int voltas2, int voltas3, int voltas4, int voltas5, int voltas6, int nParadas, String tempoParadas, String motivoParadas, double distanciaPercorrida, double dpEstimada1, double dpEstimada2, double dpPorcento1, double dpPorcento2) {
        this.idPaciente = idPaciente;
        this.horario = horario;
        this.data = data;
        this.fcBasal = fcBasal;
        this.fcFinal = fcFinal;
        this.fcRecuperacao = fcRecuperacao;
        this.fc1 = fc1;
        this.fc2 = fc2;
        this.fc3 = fc3;
        this.fc4 = fc4;
        this.fc5 = fc5;
        this.fc6 = fc6;
        this.paBasal = paBasal;
        this.paFinal = paFinal;
        this.paRecuperacao = paRecuperacao;
        this.gcBasal = gcBasal;
        this.gcFinal = gcFinal;
        this.gcRecuperacao = gcRecuperacao;
        this.spoBasal = spoBasal;
        this.spo1 = spo1;
        this.spo2 = spo2;
        this.spo3 = spo3;
        this.spo4 = spo4;
        this.spo5 = spo5;
        this.spo6 = spo6;
        this.o2SuplBasal = o2SuplBasal;
        this.dispneiaBasal = dispneiaBasal;
        this.dispneiaFinal = dispneiaFinal;
        this.dispneiaRecuperacao = dispneiaRecuperacao;
        this.dispneia1 = dispneia1;
        this.dispneia2 = dispneia2;
        this.dispneia3 = dispneia3;
        this.dispneia4 = dispneia4;
        this.dispneia5 = dispneia5;
        this.dispneia6 = dispneia6;
        this.fadigaBasal = fadigaBasal;
        this.fadigaFinal = fadigaFinal;
        this.fadigaRecuperacao = fadigaRecuperacao;
        this.fadiga1 = fadiga1;
        this.fadiga2 = fadiga2;
        this.fadiga3 = fadiga3;
        this.fadiga4 = fadiga4;
        this.fadiga5 = fadiga5;
        this.fadiga6 = fadiga6;
        this.voltas1 = voltas1;
        this.voltas2 = voltas2;
        this.voltas3 = voltas3;
        this.voltas4 = voltas4;
        this.voltas5 = voltas5;
        this.voltas6 = voltas6;
        this.nParadas = nParadas;
        this.tempoParadas = tempoParadas;
        this.motivoParadas = motivoParadas;
        this.distanciaPercorrida = distanciaPercorrida;
        this.dpEstimada1 = dpEstimada1;
        this.dpEstimada2 = dpEstimada2;
        this.dpPorcento1 = dpPorcento1;
        this.dpPorcento2 = dpPorcento2;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFcBasal() {
        return fcBasal;
    }

    public void setFcBasal(String fcBasal) {
        this.fcBasal = fcBasal;
    }

    public String getFcFinal() {
        return fcFinal;
    }

    public void setFcFinal(String fcFinal) {
        this.fcFinal = fcFinal;
    }

    public String getFcRecuperacao() {
        return fcRecuperacao;
    }

    public void setFcRecuperacao(String fcRecuperacao) {
        this.fcRecuperacao = fcRecuperacao;
    }

    public String getFc1() {
        return fc1;
    }

    public void setFc1(String fc1) {
        this.fc1 = fc1;
    }

    public String getFc2() {
        return fc2;
    }

    public void setFc2(String fc2) {
        this.fc2 = fc2;
    }

    public String getFc3() {
        return fc3;
    }

    public void setFc3(String fc3) {
        this.fc3 = fc3;
    }

    public String getFc4() {
        return fc4;
    }

    public void setFc4(String fc4) {
        this.fc4 = fc4;
    }

    public String getFc5() {
        return fc5;
    }

    public void setFc5(String fc5) {
        this.fc5 = fc5;
    }

    public String getFc6() {
        return fc6;
    }

    public void setFc6(String fc6) {
        this.fc6 = fc6;
    }

    public String getPaBasal() {
        return paBasal;
    }

    public void setPaBasal(String paBasal) {
        this.paBasal = paBasal;
    }

    public String getPaFinal() {
        return paFinal;
    }

    public void setPaFinal(String paFinal) {
        this.paFinal = paFinal;
    }

    public String getPaRecuperacao() {
        return paRecuperacao;
    }

    public void setPaRecuperacao(String paRecuperacao) {
        this.paRecuperacao = paRecuperacao;
    }

    public String getGcBasal() {
        return gcBasal;
    }

    public void setGcBasal(String gcBasal) {
        this.gcBasal = gcBasal;
    }

    public String getGcFinal() {
        return gcFinal;
    }

    public void setGcFinal(String gcFinal) {
        this.gcFinal = gcFinal;
    }

    public String getGcRecuperacao() {
        return gcRecuperacao;
    }

    public void setGcRecuperacao(String gcRecuperacao) {
        this.gcRecuperacao = gcRecuperacao;
    }

    public String getSpoBasal() {
        return spoBasal;
    }

    public void setSpoBasal(String spoBasal) {
        this.spoBasal = spoBasal;
    }

    public String getSpo1() {
        return spo1;
    }

    public void setSpo1(String spo1) {
        this.spo1 = spo1;
    }

    public String getSpo2() {
        return spo2;
    }

    public void setSpo2(String spo2) {
        this.spo2 = spo2;
    }

    public String getSpo3() {
        return spo3;
    }

    public void setSpo3(String spo3) {
        this.spo3 = spo3;
    }

    public String getSpo4() {
        return spo4;
    }

    public void setSpo4(String spo4) {
        this.spo4 = spo4;
    }

    public String getSpo5() {
        return spo5;
    }

    public void setSpo5(String spo5) {
        this.spo5 = spo5;
    }

    public String getSpo6() {
        return spo6;
    }

    public void setSpo6(String spo6) {
        this.spo6 = spo6;
    }

    public String getO2SuplBasal() {
        return o2SuplBasal;
    }

    public void setO2SuplBasal(String o2SuplBasal) {
        this.o2SuplBasal = o2SuplBasal;
    }

    public String getDispneiaBasal() {
        return dispneiaBasal;
    }

    public void setDispneiaBasal(String dispneiaBasal) {
        this.dispneiaBasal = dispneiaBasal;
    }

    public String getDispneiaFinal() {
        return dispneiaFinal;
    }

    public void setDispneiaFinal(String dispneiaFinal) {
        this.dispneiaFinal = dispneiaFinal;
    }

    public String getDispneiaRecuperacao() {
        return dispneiaRecuperacao;
    }

    public void setDispneiaRecuperacao(String dispneiaRecuperacao) {
        this.dispneiaRecuperacao = dispneiaRecuperacao;
    }

    public String getDispneia1() {
        return dispneia1;
    }

    public void setDispneia1(String dispneia1) {
        this.dispneia1 = dispneia1;
    }

    public String getDispneia2() {
        return dispneia2;
    }

    public void setDispneia2(String dispneia2) {
        this.dispneia2 = dispneia2;
    }

    public String getDispneia3() {
        return dispneia3;
    }

    public void setDispneia3(String dispneia3) {
        this.dispneia3 = dispneia3;
    }

    public String getDispneia4() {
        return dispneia4;
    }

    public void setDispneia4(String dispneia4) {
        this.dispneia4 = dispneia4;
    }

    public String getDispneia5() {
        return dispneia5;
    }

    public void setDispneia5(String dispneia5) {
        this.dispneia5 = dispneia5;
    }

    public String getDispneia6() {
        return dispneia6;
    }

    public void setDispneia6(String dispneia6) {
        this.dispneia6 = dispneia6;
    }

    public String getFadigaBasal() {
        return fadigaBasal;
    }

    public void setFadigaBasal(String fadigaBasal) {
        this.fadigaBasal = fadigaBasal;
    }

    public String getFadigaFinal() {
        return fadigaFinal;
    }

    public void setFadigaFinal(String fadigaFinal) {
        this.fadigaFinal = fadigaFinal;
    }

    public String getFadigaRecuperacao() {
        return fadigaRecuperacao;
    }

    public void setFadigaRecuperacao(String fadigaRecuperacao) {
        this.fadigaRecuperacao = fadigaRecuperacao;
    }

    public String getFadiga1() {
        return fadiga1;
    }

    public void setFadiga1(String fadiga1) {
        this.fadiga1 = fadiga1;
    }

    public String getFadiga2() {
        return fadiga2;
    }

    public void setFadiga2(String fadiga2) {
        this.fadiga2 = fadiga2;
    }

    public String getFadiga3() {
        return fadiga3;
    }

    public void setFadiga3(String fadiga3) {
        this.fadiga3 = fadiga3;
    }

    public String getFadiga4() {
        return fadiga4;
    }

    public void setFadiga4(String fadiga4) {
        this.fadiga4 = fadiga4;
    }

    public String getFadiga5() {
        return fadiga5;
    }

    public void setFadiga5(String fadiga5) {
        this.fadiga5 = fadiga5;
    }

    public String getFadiga6() {
        return fadiga6;
    }

    public void setFadiga6(String fadiga6) {
        this.fadiga6 = fadiga6;
    }

    public int getVoltas1() {
        return voltas1;
    }

    public void setVoltas1(int voltas1) {
        this.voltas1 = voltas1;
    }

    public int getVoltas2() {
        return voltas2;
    }

    public void setVoltas2(int voltas2) {
        this.voltas2 = voltas2;
    }

    public int getVoltas3() {
        return voltas3;
    }

    public void setVoltas3(int voltas3) {
        this.voltas3 = voltas3;
    }

    public int getVoltas4() {
        return voltas4;
    }

    public void setVoltas4(int voltas4) {
        this.voltas4 = voltas4;
    }

    public int getVoltas5() {
        return voltas5;
    }

    public void setVoltas5(int voltas5) {
        this.voltas5 = voltas5;
    }

    public int getVoltas6() {
        return voltas6;
    }

    public void setVoltas6(int voltas6) {
        this.voltas6 = voltas6;
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
}
