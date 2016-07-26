package br.edu.ufcspa.tc6m.modelo;

import java.io.Serializable;

/**
 * Created by cliente on 25/07/2016.
 */
public class Velocidade implements Serializable {

    private Long idTeste;
    private Long idPaciente;

    private float Velocidade;
    private String tempo;

    public Velocidade(float velocidade, String tempo) {
        Velocidade = velocidade;
        this.tempo = tempo;
    }


    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdTeste() {
        return idTeste;
    }

    public void setIdTeste(Long idTeste) {
        this.idTeste = idTeste;
    }

    public float getVelocidade() {
        return Velocidade;
    }

    public String getTempo() {
        return tempo;
    }

}
