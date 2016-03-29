package br.edu.ufcspa.tc6m.modelo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by edupooch on 21/12/15.
 */
public class Paciente implements Serializable {

    private Long id = null;
    private String nome;
    private String telefone;
    private String email;
    private double peso;
    private double altura;
    private String dataNascimento;
    private int genero;


    public Paciente(Long id, String nome, String telefone, String email, double peso, double altura, String dataNascimento, int genero) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.peso = peso;
        this.altura = altura;
        this.dataNascimento = dataNascimento;
        this.genero = genero;

    }

    public Paciente() {
    }

    /*
    public JSONObject toJSon() {
        JSONObject paciente = new JSONObject();

        String encodedImage = " ";

        if (foto!= null){
            BitmapFactory.Options options0 = new BitmapFactory.Options();
            options0.inSampleSize = 2;
            //options.inJustDecodeBounds = true;
            options0.inScaled = false;
            options0.inDither = false;
            options0.inPreferredConfig = Bitmap.Config.ARGB_8888;

            Bitmap bmp = BitmapFactory.decodeFile(getFoto());

            ByteArrayOutputStream baos0 = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.JPEG,100,baos0);
            byte[] imageBytes0 = baos0.toByteArray();

            encodedImage = Base64.encodeToString(imageBytes0, Base64.DEFAULT);
        }




        try {
            paciente.put("nome",getNome());
            paciente.put("telefone",getTelefone());
            paciente.put("email", getEmail());
            paciente.put("peso", getPeso());
            paciente.put("altura", getAltura());
            paciente.put("nascimento", getDataNascimento());



            if(!encodedImage.isEmpty()){ // se nao tiver vazia adicionar a foto do paciente
                paciente.put("foto",encodedImage);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paciente;
    }

*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return getNome();
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }
}
