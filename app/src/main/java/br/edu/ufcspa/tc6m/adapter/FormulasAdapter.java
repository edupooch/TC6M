package br.edu.ufcspa.tc6m.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Formula;

/**
 * Created by Particular on 12/07/2016.
 */
public class FormulasAdapter extends BaseAdapter {
    private final List<Formula> formulas;
    private final Context context;

    public FormulasAdapter(Context context, List<Formula> formulas) {
        this.context = context;
        this.formulas = formulas;
    }

    @Override
    public int getCount() {
        return formulas.size();
    }

    @Override
    public Object getItem(int position) {
        return formulas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Formula formula = formulas.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;

        if (view == null) { //usa -se o convertView para otimizar o carregamento de listas muito grandes
            view = inflater.inflate(R.layout.list_item_formulas, parent, false);
            //parent passa os valores do pai para funcionar os tamanhos pré-definidos no xml
            //false é usado para não adicionar a view duas vezes
        }

        TextView textAutores = (TextView) view.findViewById(R.id.text_autores_formula);
        textAutores.setText(formula.getAutores());

        TextView textCoeficiente = (TextView) view.findViewById(R.id.text_coeficiente);
        double coeficiente = formula.getCoeficienteExplicacao();
        String stringCoeficiente = "r² = " + String.format(Locale.getDefault(), "%.2f", coeficiente);
        textCoeficiente.setText(stringCoeficiente);

        TextView textFormula = (TextView) view.findViewById(R.id.text_formula);
        textFormula.setText(formula.getFormula());

        return view;
    }
}
