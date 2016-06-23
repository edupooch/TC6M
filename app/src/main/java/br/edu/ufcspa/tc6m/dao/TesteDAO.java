package br.edu.ufcspa.tc6m.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import br.edu.ufcspa.tc6m.controle.Calcula;
import br.edu.ufcspa.tc6m.modelo.Paciente;
import br.edu.ufcspa.tc6m.modelo.Teste;

/**
 * Created by edupooch on 30/04/16.
 * FC: número inteiro, podendo variar de 40 à 220;
 * PA: número inteiro, podendo variar de 50 à 260; nesse campo é necessário incluir espaço para dois numerais, pois a pressão arterial é composta por um valor superior (sistólica) e outro inferior (diastólica). Ex.: 156/78;
 * GC: número inteiro, podendo variar de 50 à 300;
 * SpO2: número inteiro, podendo variar de 80 à 100;
 * O2 supl.: número decimal, com uma casa após a vírgula, podendo variar de 0 à 10;
 * alguns dos intervalos acima podem assumir valores maiores ou menores. Os valores que passei estão dentro de uma faixa aceitável para a realização do TC6min. Contudo, ainda podemos ajustar melhor esses intervalos e incluir alertas para que o avaliador não realize o teste caso o avaliado apresente valores fora de uma faixa considerada segura clinicamente. Ok?
 * O intervalo dos campos abaixo varia de acordo com a escala empregada. Assim, sugiro que o intervalo inclua os valores das duas mais comuns (0-10 e 6-20):
 * Dispneia: número decimal, com uma casa após a vírgula, podendo variar de 0,0 à 20,0;
 * Fadiga MMII: número decimal, com uma casa após a vírgula, podendo variar de 0,0 à 20,0.
 * DP: número inteiro, podendo variar de 0 à 999;
 * Estatura: número decimal, com duas casas após a vírgula, podendo variar de 1,00 à 2,20;
 * Massa corporal: número decimal, com uma casa após a vírgula, podendo variar de 20,0 à 200,0;
 * IMC: número decimal, com uma casa após a vírgula, podendo variar de 15,0 à 50,0.
 * Idade: seria interessante incluir um campo para data de nascimento, a partir da qual se calcularia a idade em anos, meses e dias;
 * Número de paradas: número inteiro, podendo variar de 0 à 20;
 * Tempo das paradas: número inteiro, devendo incluir um campo para minutos (0-10) e outro para segundos (0-60);
 * DP estimada: número decimal, com uma casa após a vírgula, podendo variar de 0,0 à 999,9.
 * % DP estimada: número decimal, com uma casa após a vírgula, podendo variar de 0,0 à 199,9.
 */


public class TesteDAO extends SQLiteOpenHelper {

    String[] strKeyFc = {"fc_0", "fc_1", "fc_2", "fc_3", "fc_4", "fc_5", "fc_6", "fc_7", "fc_8",};
    String[] strKeyDisp = {"disp_0", "disp_1", "disp_2", "disp_3", "disp_4", "disp_5", "disp_6", "disp_7", "disp_8",};
    String[] strKeyFad = {"fad_0", "fad_1", "fad_2", "fad_3", "fad_4", "fad_5", "fad_6", "fad_7", "fad_8",};
    String[] strKeySp = {"spo2_0", "spo2_1", "spo2_2", "spo2_3", "spo2_4", "spo2_5", "spo2_6"};
    String[] strKeyPa = {"pa_0", "pa_1", "pa_2"};
    String[] strKeyGc = {"gc_0", "gc_1", "gc_2"};
    String[] strKeyVoltas = {"voltas_0", "voltas_1", "voltas_2", "voltas_3", "voltas_4", "voltas_5",};

    public TesteDAO(Context context) {
        super(context, "Testes", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS Testes ( id_teste INTEGER PRIMARY KEY,\n" +
                "id_paciente INTEGER NOT NULL,\n" +
                "dia_hora datetime,\n" +
                "idade_paciente INTEGER,\n" +
                "fc_0 INTEGER, fc_1 INTEGER, fc_2 INTEGER, fc_3 INTEGER, fc_4 INTEGER, fc_5 INTEGER, fc_6 INTEGER, fc_7 INTEGER, fc_8 INTEGER,\n" +
                "spo2_0 INTEGER, spo2_1 INTEGER, spo2_2 INTEGER, spo2_3 INTEGER, spo2_4 INTEGER, spo2_5 INTEGER, spo2_6 INTEGER,\n" +
                "disp_0 REAL, disp_1 REAL, disp_2 REAL, disp_3 REAL, disp_4 REAL, disp_5 REAL, disp_6 REAL, disp_7 REAL, disp_8 REAL,\n" +
                "fad_0 REAL, fad_1 REAL, fad_2 REAL, fad_3 REAL, fad_4 REAL, fad_5 REAL, fad_6 REAL, fad_7 REAL, fad_8 REAL,\n" +
                "o2supl_0 REAL,\n" +
                "pa_0 TEXT, pa_1 TEXT, pa_2 TEXT,\n" +
                "gc_0 INTEGER, gc_1 INTEGER, gc_2 INTEGER,\n" +
                "voltas_0 INTEGER, voltas_1 INTEGER, voltas_2 INTEGER, voltas_3 INTEGER, voltas_4 INTEGER, voltas_5 INTEGER,\n" +
                "n_paradas INTEGER,\n" +
                "tempo_paradas TEXT,\n" +
                "motivo_parada TEXT,\n" +
                "massa REAL,\n" +
                "estatura REAL,\n" +
                "obs_final TEXT,\n" +
                "distancia_percorrida REAL,\n" +
                "FOREIGN KEY(id_paciente) REFERENCES Pacientes(id));";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Testes";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Teste teste) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getContentValuesTeste(teste);
        db.insert("Testes", null, dados);
    }

    @NonNull
    private ContentValues getContentValuesTeste(Teste teste) {
        ContentValues dados = new ContentValues();
        dados.put("id_paciente", teste.getIdPaciente());
        //DATA DO TESTE
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dados.put("dia_hora", dtFormat.format(new Date()));
        dados.put("idade_paciente", Calcula.idade(teste.getPaciente().getDataNascimento()));
        //INSERE VALORES DE FC
        for (int i = 0; i < 9; i++) dados.put(strKeyFc[i], teste.getFc(i));
        //SPO2
        for (int i = 0; i < 7; i++) dados.put(strKeySp[i], teste.getSpO2(i));
        //DISPNEIA1
        for (int i = 0; i < 9; i++) dados.put(strKeyDisp[i], teste.getDispneia(i));
        //FADIGA MMII
        for (int i = 0; i < 9; i++) dados.put(strKeyFad[i], teste.getFadiga(i));
        //O2 SUPLEMENTAR
        dados.put("o2supl_0", teste.getO2Supl());
        //PA
        for (int i = 0; i < 3; i++) dados.put(strKeyPa[i], teste.getPa(i));
        //GC
        for (int i = 0; i < 3; i++) dados.put(strKeyGc[i], teste.getGc(i));
        //VOLTAS
        for (int i = 0; i < 6; i++) dados.put(strKeyVoltas[i], teste.getVoltas(i));
        //VALORES ÚNICOS
        dados.put("n_paradas", teste.getnParadas());
        dados.put("tempo_paradas", teste.getTempoParadas());
        dados.put("motivo_parada", teste.getMotivoParadas());
        dados.put("massa", teste.getMassa());
        dados.put("estatura", teste.getEstatura());
        dados.put("obs_final", teste.getObsFinal());
        dados.put("distancia_percorrida", teste.getDistanciaPercorrida());
        return dados;
    }


    public List<Teste> buscaTestes(Paciente paciente) {
        String sql = "SELECT * FROM Testes WHERE id_paciente = " + paciente.getId() + ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Teste> testes = new ArrayList<Teste>();

        while (c.moveToNext()) {
            Teste teste = new Teste(paciente);
            teste.setIdTeste(c.getLong(c.getColumnIndex("id_teste")));
            teste.setData(java.sql.Date.valueOf(c.getString(c.getColumnIndex("dia_hora"))));
            teste.setIdade(c.getInt(c.getColumnIndex("idade_paciente")));
            for (int i = 0; i < 9; i++)
                teste.setFc(i, c.getInt(c.getColumnIndex(strKeyFc[i])));
            for (int i = 0; i < 7; i++)
                teste.setSpO2(i, c.getInt(c.getColumnIndex(strKeySp[i])));
            //DISPNEIA1
            for (int i = 0; i < 9; i++)
                teste.setDispneia(i, c.getDouble(c.getColumnIndex(strKeyDisp[i])));
            //FADIGA MMII
            for (int i = 0; i < 9; i++)
                teste.setFadiga(i, c.getDouble(c.getColumnIndex(strKeyFad[i])));
            //O2 SUPLEMENTAR
            teste.setO2Supl(c.getDouble(c.getColumnIndex("o2supl_0")));
            //PA
            for (int i = 0; i < 3; i++)
                teste.setPa(i, c.getString(c.getColumnIndex(strKeyPa[i])));
            //GC
            for (int i = 0; i < 3; i++)
                teste.setGc(i, c.getInt(c.getColumnIndex(strKeyGc[i])));
            //VOLTAS
            for (int i = 0; i < 6; i++)
                teste.setVoltas(i, c.getInt(c.getColumnIndex(strKeyVoltas[i])));
            teste.setnParadas(c.getInt(c.getColumnIndex("n_paradas")));
            teste.setTempoParadas(c.getString(c.getColumnIndex("tempo_paradas")));
            teste.setMotivoParadas(c.getString(c.getColumnIndex("motivo_parada")));
            teste.setMassa(c.getDouble(c.getColumnIndex("massa")));
            teste.setEstatura(c.getDouble(c.getColumnIndex("estatura")));
            teste.setObsFinal(c.getString(c.getColumnIndex("obs_final")));
            teste.setDistanciaPercorrida(c.getInt(c.getColumnIndex("distancia_percorrida")));

            testes.add(teste);

        }
        c.close();

        return testes;
    }

    public int contarTestesDoPaciente(Paciente paciente) {
        String sql = "SELECT * FROM Testes WHERE id_paciente = " + paciente.getId() + ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        int numeroTestes = 0;
        if (c.moveToLast())
            numeroTestes = c.getCount();//Move o cursor para a última posição e pega o valor

        c.close();

        return numeroTestes;
    }

    public void deletaTodosDoPaciente(Paciente paciente) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parametros = {paciente.getId().toString()};
        db.delete("Testes","id_paciente = ?",parametros);
    }

    public void deleta(Teste teste) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parametros = {teste.getIdTeste().toString()};
        db.delete("Testes", "id_teste = ?", parametros);
    }
}
