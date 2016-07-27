package br.edu.ufcspa.tc6m.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.controle.Calcula;
import br.edu.ufcspa.tc6m.dao.TesteDAO;
import br.edu.ufcspa.tc6m.formulas.ListaFormulas;
import br.edu.ufcspa.tc6m.modelo.Formula;
import br.edu.ufcspa.tc6m.modelo.Teste;
import br.edu.ufcspa.tc6m.modelo.Teste;

/**
 * Created by edupooch on 14/05/16.
 * <p/>
 * Classe Adapter criada para fazer uma lista de teste mais interativa e com mais informações.
 */

public class TestesAdapter extends BaseAdapter {

    private static final int ID_FC_FINAL = 7;
    private static final int ID_FC_INICIAL = 0;

    private final List<Teste> testes;
    private final Context context;

    public TestesAdapter(Context context, List<Teste> testes) {
        this.context = context;
        this.testes = testes;
    }

    @Override
    public int getCount() {
        return testes.size();
    }

    @Override
    public Object getItem(int position) {
        return testes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return testes.get(position).getIdTeste();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Teste teste = testes.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;
        if (view == null) { //usa -se o convertView para otimizar o carregamento de listas muito grandes
            view = inflater.inflate(R.layout.list_item_testes, parent, false);
            //parent passa os valores do pai para funcionar os tamanhos pré-definidos no xml
            //false é usado para não adicionar a view duas vezes
        }

        TextView textTitulo = (TextView) view.findViewById(R.id.titulo);
        textTitulo.setText(teste.toString());

        TextView textSubtitulo = (TextView) view.findViewById(R.id.subtitulo);
        String vm = String.format(Locale.getDefault(), "%.2f", Calcula.velocidadeMedia(teste.getDistanciaPercorrida()));

        //Pega a última formula usada das preferências, e o default é a formula 0 (Britto 1)
        SharedPreferences sharedPref = context.getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
        int ultimaFormula = sharedPref.getInt("ULTIMA_FORMULA", ListaFormulas.ID_BRITTO1);
        Formula formula = new ListaFormulas().getFormulas().get(ultimaFormula);

        int variacaoFc = 0;
        if (teste.getFc(ID_FC_FINAL) != null && teste.getFc(ID_FC_INICIAL) != null) {
            variacaoFc = teste.getFc(ID_FC_FINAL) - teste.getFc(ID_FC_INICIAL);
        }
        double dbDpEstimada = Calcula.dpEstimada(formula, teste.getIdade(), teste.getPaciente().getGenero(), teste.getMassa(), teste.getEstatura(), variacaoFc);
        double dbPercentDpEstimada = Calcula.porcentagem(teste.getDistanciaPercorrida(), dbDpEstimada);
        String strPercDpEstimada = String.format(Locale.getDefault(), "%.1f", dbPercentDpEstimada);


        String strSubtitulo = teste.getDistanciaPercorrida() + "m | " + vm + "m/s | " + strPercDpEstimada + "%";
        textSubtitulo.setText(strSubtitulo);


        ImageView imagem = (ImageView) view.findViewById(R.id.imagem_item);
        if (dbPercentDpEstimada >= 100) {
            imagem.setBackgroundColor(Color.rgb(83, 186, 131));    //VERDE
        } else if (dbPercentDpEstimada < 100 && dbPercentDpEstimada >= 90) {
            imagem.setBackgroundColor(Color.rgb(77, 166, 255)); //AZUL
        } else if (dbPercentDpEstimada < 90 && dbPercentDpEstimada >= 70) {
            imagem.setBackgroundColor(Color.rgb(255, 217, 102)); //AMARELO
        } else {
            imagem.setBackgroundColor(Color.rgb(255, 77, 77)); //VERMELHO
        }


        return view;
    }
}
