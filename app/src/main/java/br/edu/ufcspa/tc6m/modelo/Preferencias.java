package br.edu.ufcspa.tc6m.modelo;

import java.io.Serializable;

/**
 * Created by nilton-pc on 18/07/2016.
 */
public class Preferencias implements Serializable {

    private int tamanhoVolta;
    private int britto1;
    private int britto2;
    private int dourado;
    private int soaresPereira;
    private int iwama;
    private int[] formulas;

    public Preferencias() {
    }

    public Preferencias(int tamanhoVolta, int britto1, int britto2, int dourado, int soaresPereira, int iwama) {
        this.tamanhoVolta = tamanhoVolta;
        this.britto1 = britto1;
        this.britto2 = britto2;
        this.dourado = dourado;
        this.soaresPereira = soaresPereira;
        this.iwama = iwama;
    }


    public int getTamanhoVolta() {
        return tamanhoVolta;
    }

    public void setTamanhoVolta(int tamanhoVolta) {
        this.tamanhoVolta = tamanhoVolta;
    }

    public int getBritto1() {
        return britto1;
    }

    public void setBritto1(int britto1) {
        this.britto1 = britto1;
    }

    public int getBritto2() {
        return britto2;
    }

    public void setBritto2(int britto2) {
        this.britto2 = britto2;
    }

    public int getDourado() {
        return dourado;
    }

    public void setDourado(int dourado) {
        this.dourado = dourado;
    }

    public int getSoaresPereira() {
        return soaresPereira;
    }

    public void setSoaresPereira(int soaresPereira) {
        this.soaresPereira = soaresPereira;
    }

    public int getIwama() {
        return iwama;
    }

    public void setIwama(int iwama) {
        this.iwama = iwama;
    }
}
