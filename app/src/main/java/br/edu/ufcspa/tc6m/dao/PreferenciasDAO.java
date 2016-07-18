package br.edu.ufcspa.tc6m.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcspa.tc6m.modelo.Paciente;
import br.edu.ufcspa.tc6m.modelo.Preferencias;
import br.edu.ufcspa.tc6m.modelo.Teste;

/**
 * Created by edupooch on 30/04/16.
 */
public class PreferenciasDAO extends SQLiteOpenHelper {

    public PreferenciasDAO(Context context) {
        super(context, "Preferencias", null, 1);
    }

    /**
     * Cria a tabela para as preferencias, as formulas escolhidas nas preferências são colocadas
     * como valor 1 em seus campos
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Preferencias (id_preferecias INTEGER PRIMARY KEY, " +
                "tamanho_volta INTEGER, " +
                "britto1 INTEGER, " +
                "britto2 INTEGER, " +
                "dourado INTEGER, " +
                "soares_pereira INTEGER, " +
                "iwama INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Preferencias";
        db.execSQL(sql);
        onCreate(db);
    }

    public Preferencias buscaPreferencias() {
        String sql = "SELECT * FROM Preferencias;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        Preferencias preferencias = new Preferencias();

        while (c.moveToNext()) {
            preferencias.setTamanhoVolta(c.getInt(c.getColumnIndex("tamanho_volta")));
            preferencias.setBritto1(c.getInt(c.getColumnIndex("britto1")));
            preferencias.setBritto2(c.getInt(c.getColumnIndex("britto2")));
            preferencias.setDourado(c.getInt(c.getColumnIndex("dourado")));
            preferencias.setSoaresPereira(c.getInt(c.getColumnIndex("soares_pereira")));
            preferencias.setIwama(c.getInt(c.getColumnIndex("iwama")));

        }
        c.close();

        return preferencias;
    }
}
