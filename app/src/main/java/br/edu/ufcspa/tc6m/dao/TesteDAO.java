package br.edu.ufcspa.tc6m.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.edu.ufcspa.tc6m.modelo.Paciente;
import br.edu.ufcspa.tc6m.modelo.Teste;
import br.edu.ufcspa.tc6m.modelo.Velocidade;

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
    String[] strKeyDisp = {"disp_0", "disp_1", "disp_2", "disp_3", "disp_4", "disp_5", "disp_6", "disp_7"};
    String[] strKeyFad = {"fad_0", "fad_1", "fad_2", "fad_3", "fad_4", "fad_5", "fad_6", "fad_7"};
    String[] strKeySp = {"spo2_0", "spo2_1", "spo2_2", "spo2_3", "spo2_4", "spo2_5", "spo2_6", "spo2_7"};
    String[] strKeyPas = {"pas_0", "pas_1", "pas_2"};
    String[] strKeyPad = {"pad_0", "pad_1", "pad_2"};
    String[] strKeyGc = {"gc_0", "gc_1", "gc_2"};
    String[] strKeyO2Supl = {"o2supl_0", "o2supl_1"};

    public TesteDAO(Context context) {
        super(context, "Testes", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE IF NOT EXISTS Testes ( id_teste INTEGER PRIMARY KEY,\n" +
                        "id_paciente INTEGER NOT NULL,\n" +
                        "dia_hora datetime,\n" +
                        "idade_paciente INTEGER,\n" +
                        "fc_0 INTEGER, fc_1 INTEGER, fc_2 INTEGER, fc_3 INTEGER, fc_4 INTEGER, fc_5 INTEGER, fc_6 INTEGER, fc_7 INTEGER, fc_8 INTEGER,\n" +
                        "spo2_0 INTEGER, spo2_1 INTEGER, spo2_2 INTEGER, spo2_3 INTEGER, spo2_4 INTEGER, spo2_5 INTEGER, spo2_6 INTEGER,  spo2_7 INTEGER,\n" +
                        "disp_0 REAL, disp_1 REAL, disp_2 REAL, disp_3 REAL, disp_4 REAL, disp_5 REAL, disp_6 REAL, disp_7 REAL,\n" +
                        "fad_0 REAL, fad_1 REAL, fad_2 REAL, fad_3 REAL, fad_4 REAL, fad_5 REAL, fad_6 REAL, fad_7 REAL,\n" +
                        "o2supl_0 REAL, o2supl_1 REAL,\n" +
                        "pas_0 TEXT, pas_1 TEXT, pas_2 TEXT,\n" +
                        "pad_0 TEXT, pad_1 TEXT, pad_2 TEXT,\n" +
                        "gc_0 INTEGER, gc_1 INTEGER, gc_2 INTEGER,\n" +
                        "n_paradas INTEGER,\n" +
                        "tempo_paradas TEXT,\n" +
                        "motivo_parada TEXT,\n" +
                        "massa REAL,\n" +
                        "estatura REAL,\n" +
                        "obs_final TEXT,\n" +
                        "distancia_percorrida REAL,\n" +
                        "tamanho_volta INTEGER,\n" +
                        "obs_teste TEXT,\n" +
                        "FOREIGN KEY(id_paciente) REFERENCES Pacientes(id));";


        String sqlVelocidades =
                "CREATE TABLE IF NOT EXISTS Velocidades ( id_velocidade INTEGER PRIMARY KEY,\n" +
                        "id_teste INTEGER,\n" +
                        "id_paciente INTEGER,\n" +
                        "velocidade REAL,\n" +
                        "tempo TEXT,\n" +
                        "FOREIGN KEY(id_paciente) REFERENCES Testes(id_paciente),\n" +
                        "FOREIGN KEY(id_teste) REFERENCES Testes(id_teste));";

        db.execSQL(sql);
        db.execSQL(sqlVelocidades);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Testes";
        String sqlVelocidades = "DROP TABLE IF EXISTS Velocidades";
        db.execSQL(sql);
        db.execSQL(sqlVelocidades);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Testes";
        String sqlVelocidades = "DROP TABLE IF EXISTS Velocidades";

        db.execSQL(sql);
        db.execSQL(sqlVelocidades);

        onCreate(db);
    }

    public void insere(Teste teste) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dadosTeste = getContentValuesTeste(teste);
        db.insert("Testes", null, dadosTeste);

        //Pega o id do teste que acabou de ser inserido para inserir as velocidades
        teste.setIdTeste(buscaIdUltimoTeste(teste.getPaciente()));

        //Insere as velocidades
        for (Velocidade velocidade : teste.getVelocidades()) {
            velocidade.setIdTeste(teste.getIdTeste());
            velocidade.setIdPaciente(teste.getIdPaciente());
            ContentValues dadosVelocidade = getContentValuesVelocidade(velocidade);
            db.insert("Velocidades", null, dadosVelocidade);
            System.out.println("Iserido com sucesso" + teste.getIdTeste() + " " + teste.getIdPaciente());
        }
    }

    @NonNull
    private ContentValues getContentValuesTeste(Teste teste) {
        ContentValues dados = new ContentValues();
        dados.put("id_paciente", teste.getIdPaciente());
        //DATA DO TESTE
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dados.put("dia_hora", dtFormat.format(new Date()));
        dados.put("idade_paciente", teste.getIdade());
        //INSERE VALORES DE FC
        for (int i = 0; i < 9; i++) dados.put(strKeyFc[i], teste.getFc(i));
        //SPO2
        for (int i = 0; i < 8; i++) dados.put(strKeySp[i], teste.getSpO2(i));
        //DISPNEIA1
        for (int i = 0; i < 8; i++) dados.put(strKeyDisp[i], teste.getDispneia(i));
        //FADIGA MMII
        for (int i = 0; i < 8; i++) dados.put(strKeyFad[i], teste.getFadiga(i));
        //O2 SUPLEMENTAR
        for (int i = 0; i < 2; i++) dados.put(strKeyO2Supl[i], teste.getO2Supl(i));
        //PAs
        for (int i = 0; i < 3; i++) dados.put(strKeyPas[i], teste.getPAs(i));
        //PAd
        for (int i = 0; i < 3; i++) dados.put(strKeyPad[i], teste.getPAd(i));
        //GC
        for (int i = 0; i < 3; i++) dados.put(strKeyGc[i], teste.getGc(i));
        //VALORES ÚNICOS
        dados.put("n_paradas", teste.getnParadas());
        dados.put("tempo_paradas", teste.getTempoParadas());
        dados.put("motivo_parada", teste.getMotivoParadas());
        dados.put("massa", teste.getMassa());
        dados.put("estatura", teste.getEstatura());
        dados.put("obs_final", teste.getObsFinal());
        dados.put("distancia_percorrida", teste.getDistanciaPercorrida());
        dados.put("tamanho_volta", teste.getTamanhoVolta());
        dados.put("obs_teste",teste.getObsTeste());
        return dados;
    }

    @NonNull
    private ContentValues getContentValuesVelocidade(Velocidade velocidade) {
        ContentValues dados = new ContentValues();
        dados.put("id_teste", velocidade.getIdTeste());
        dados.put("id_paciente", velocidade.getIdPaciente());
        dados.put("velocidade", velocidade.getVelocidade());
        dados.put("tempo", velocidade.getTempo());
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
                if (!c.isNull(c.getColumnIndex(strKeyFc[i])))
                    teste.setFc(i, c.getInt(c.getColumnIndex(strKeyFc[i])));
            for (int i = 0; i < 8; i++)
                if (!c.isNull(c.getColumnIndex(strKeySp[i])))
                    teste.setSpO2(i, c.getInt(c.getColumnIndex(strKeySp[i])));
            //DISPNEIA1
            for (int i = 0; i < 8; i++)
                if (!c.isNull(c.getColumnIndex(strKeyDisp[i])))
                    teste.setDispneia(i, c.getDouble(c.getColumnIndex(strKeyDisp[i])));
            //FADIGA MMII
            for (int i = 0; i < 8; i++)
                if (!c.isNull(c.getColumnIndex(strKeyFad[i])))
                    teste.setFadiga(i, c.getDouble(c.getColumnIndex(strKeyFad[i])));
            //O2 SUPLEMENTAR
            for (int i = 0; i < 2; i++)
                if (!c.isNull(c.getColumnIndex(strKeyO2Supl[i])))
                    teste.setO2Supl(i, c.getDouble(c.getColumnIndex(strKeyO2Supl[i])));
            //PAs
            for (int i = 0; i < 3; i++)
                if (!c.isNull(c.getColumnIndex(strKeyPas[i])))
                    teste.setPAs(i, c.getInt(c.getColumnIndex(strKeyPas[i])));
            //PAd
            for (int i = 0; i < 3; i++)
                if (!c.isNull(c.getColumnIndex(strKeyPad[i])))
                    teste.setPAd(i, c.getInt(c.getColumnIndex(strKeyPad[i])));
            //GC
            for (int i = 0; i < 3; i++)
                if (!c.isNull(c.getColumnIndex(strKeyGc[i])))
                    teste.setGc(i, c.getInt(c.getColumnIndex(strKeyGc[i])));
            teste.setnParadas(c.getInt(c.getColumnIndex("n_paradas")));
            teste.setTempoParadas(c.getString(c.getColumnIndex("tempo_paradas")));
            teste.setMotivoParadas(c.getString(c.getColumnIndex("motivo_parada")));

            teste.setMassa(c.getDouble(c.getColumnIndex("massa")));
            teste.setEstatura(c.getDouble(c.getColumnIndex("estatura")));
            teste.setObsFinal(c.getString(c.getColumnIndex("obs_final")));
            teste.setDistanciaPercorrida(c.getInt(c.getColumnIndex("distancia_percorrida")));
            teste.setTamanhoVolta(c.getInt(c.getColumnIndex("tamanho_volta")));
            teste.setObsTeste(c.getString(c.getColumnIndex("obs_teste")));

            ArrayList<Velocidade> velocidades = buscaVelocidades(teste);
            teste.setVelocidades(velocidades);

            testes.add(teste);

        }
        c.close();

        return testes;
    }

    public ArrayList<Velocidade> buscaVelocidades(Teste teste) {
        String sql = "SELECT velocidade,tempo FROM Velocidades WHERE id_teste = " + teste.getIdTeste() + ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        ArrayList<Velocidade> velocidades = new ArrayList<>();

        while (c.moveToNext()) {
            Velocidade velocidade =
                    new Velocidade(c.getFloat(c.getColumnIndex("velocidade")),
                            c.getString(c.getColumnIndex("tempo")));
            velocidades.add(velocidade);
        }
        c.close();

        return velocidades;
    }

    /**
     * @param paciente último teste desse paciente que será pego o id
     * @return id do último teste adicionado pelo paciente passado por parâmetro
     */
    public Long buscaIdUltimoTeste(Paciente paciente) {
        String sql = "SELECT id_teste FROM Testes WHERE id_paciente = " + paciente.getId() + ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        Long id = null;
        if (c.moveToLast()) {
            id = c.getLong(c.getColumnIndex("id_teste"));
        }
        c.close();
        return id;
    }


    public int contarTestesDoPaciente(Paciente paciente) {
        String sql = "SELECT * FROM Testes WHERE id_paciente = " + paciente.getId() + ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        int numeroTestes = c.getCount();//Move o cursor para a última posição e pega o valor
        c.close();

        return numeroTestes;
    }

    public void deletaTodosDoPaciente(Paciente paciente) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parametros = {paciente.getId().toString()};
        db.delete("Testes", "id_paciente = ?", parametros);
        db.delete("Velocidades", "id_paciente = ?", parametros);
    }

    public void deleta(Teste teste) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parametrosTeste = {teste.getIdTeste().toString()};
        db.delete("Testes", "id_teste = ?", parametrosTeste);
        db.delete("Velocidades", "id_teste = ?", parametrosTeste);


    }
}
