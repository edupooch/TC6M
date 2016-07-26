package br.edu.ufcspa.tc6m.controle;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import br.edu.ufcspa.tc6m.R;

/**
 * Created by cliente on 26/07/2016.
 */
public class BarraDeProgresso extends AsyncTask<Void,Void,Float> {
    private Activity activity;

    public BarraDeProgresso(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Float doInBackground(Void... params) {
        float progresso = 0;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progresso += 0.27;
        return progresso;
    }

    @Override
    protected void onPostExecute(Float progresso) {
        CircularProgressBar circulo = (CircularProgressBar) activity.findViewById(R.id.progressoCirculo);
        circulo.setProgress(progresso);

    }
}
