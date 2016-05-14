package br.edu.ufcspa.tc6m.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

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
    public TesteDAO(Context context) {
        super(context, "Testes", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS Testes ( id_teste INTEGER PRIMARY KEY,\n" +
                "id_paciente INTEGER NOT NULL,\n" +
                "dia_hora datetime,\n" +
                "fc_0 INTEGER, fc_1 INTEGER, fc_2 INTEGER, fc_3 INTEGER, fc_4 INTEGER, fc_5 INTEGER, fc_6 INTEGER, fc_7 INTEGER, fc_8 INTEGER,\n" +
                "spo2_0 INTEGER, spo2_1 INTEGER, spo2_2 INTEGER, spo2_3 INTEGER, spo2_4 INTEGER, spo2_5 INTEGER, spo2_6 INTEGER,\n" +
                "disp_0 REAL, disp_1 REAL, disp_2 REAL, disp_3 REAL, disp_4 REAL, disp_5 REAL, disp_6 REAL, disp_7 REAL, disp_8 REAL,\n" +
                "fad_0 REAL, fad_1 REAL, fad_2 REAL, fad_3 REAL, fad_4 REAL, fad_5 REAL, fad_6 REAL, fad_7 REAL, fad_8 REAL,\n" +
                "o2supl_0 REAL,\n" +
                "pa_0 TEXT, pa_1 TEXT, pa_2 TEXT,\n" +
                "gc_0 TEXT, gc_1 TEXT, gc_2 TEXT,\n" +
                "voltas_0 INTEGER, voltas_1 INTEGER, voltas_2 INTEGER, voltas_3 INTEGER, voltas_4 INTEGER, voltas_5 INTEGER,\n" +
                "n_paradas INTEGER,\n" +
                "tempo_paradas TEXT,\n" +
                "motivo_parada TEXT,\n" +
                "massa REAL,\n" +
                "estatura REAL,\n" +
                "obs_final TEXT,\n" +
                "distancia_percorrida REAL,\n" +
                "dp_estimada_1 REAL, dp_estimada_2 REAL,\n" +
                "dp_porcento_1 REAL, dp_porcento_2 REAL,\n" +
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

        String[] strKeyFc = {"fc0", "fc1", "fc2", "fc3", "fc4", "fc5", "fc6", "fc7", "fc8",};
        String[] strKeyDisp = {"disp0", "disp1", "disp2", "disp3", "disp4", "disp5", "disp6", "disp7", "disp8",};
        String[] strKeyFad = {"fad0", "fad1", "fad2", "fad3", "fad4", "fad5", "fad6", "fad7", "fad8",};
        String[] strKeySp = {"sp0", "sp1", "sp2", "sp3", "sp4", "sp5", "sp6"};
        String[] strKeyPa = {"pa0", "pa1", "pa2"};
        String[] strKeyGc = {"gc0", "gc1", "gc2"};
        String[] strKeyVoltas = {"voltas0", "voltas1", "voltas2", "voltas3", "voltas4", "voltas5",};

        dados.put("idPaciente", teste.getIdPaciente());
        for (int i = 0; i < 9; i++) {//pega os dados de 0 a 8 do teste
            dados.put(strKeyFc[i], teste.getFc(i));
            dados.put(strKeyDisp[i], teste.getDispneia(i));
            dados.put(strKeyFad[i], teste.getFadiga(i));
            if (i < 7) //Vetor sp só vai até o 6
                dados.put(strKeySp[i], teste.getSpO2(i));
            if (i < 6)//Vetor voltas só vai até o 5
                dados.put(strKeyVoltas[i], teste.getVoltas(i));
            if (i < 3) {//Vetor pa e gc só vao até o 2
                dados.put(strKeyPa[i], teste.getPa(i));
                dados.put(strKeyGc[i], teste.getGc(i));
            }
        }
        dados.put("o2supl",teste.getO2Supl());
        dados.put("nparadas",teste.getnParadas());
        dados.put("tempoparadas",teste.getTempoParadas());
        dados.put("motivoparadas",teste.getMotivoParadas());
        dados.put("massa",teste.getMassa());
        dados.put("estatura", teste.getEstatura());
        
        return dados;
    }
}
