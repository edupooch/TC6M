package br.edu.ufcspa.tc6m.modelo;

import java.io.Serializable;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.controle.Calcula;

/**
 * Created by edupooch on 12/07/2016.
 */

public class Formula implements Serializable {

    private int idFormula;
    private int ativada;
    private String autores;
    private String referencia;
    private String formula;
    private double coeficienteExplicacao;
    private int bandeiraPaisDeOrigem;

    public Formula(int idFormula, String autores, String formula, double coeficienteExplicacao, int bandeiraPaisDeOrigem, String referencia) {
        this.idFormula = idFormula;
        this.autores = autores;
        this.formula = formula;
        this.coeficienteExplicacao = coeficienteExplicacao;
        this.bandeiraPaisDeOrigem = bandeiraPaisDeOrigem;
        this.referencia = referencia;
    }

    public int getIdFormula() {
        return idFormula;
    }

    public void setIdFormula(int idFormula) {
        this.idFormula = idFormula;
    }

    public int isAtivada() {
        return ativada;
    }

    public void setAtivada(int ativada) {
        this.ativada = ativada;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getAutores() {
        return autores;
    }

    public String getFormula() {
        return formula;
    }

    public double getCoeficienteExplicacao() {
        return coeficienteExplicacao;
    }

    public int getBandeiraPaisDeOrigem() {
        return bandeiraPaisDeOrigem;
    }

    @Override
    public String toString() {
        return getAutores();
    }
}
