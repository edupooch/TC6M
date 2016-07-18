package br.edu.ufcspa.tc6m.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.formulas.ListaFormulas;
import br.edu.ufcspa.tc6m.modelo.Formula;
import br.edu.ufcspa.tc6m.modelo.Preferencias;

/**
 * Created by Particular on 12/07/2016.
 */
public class FormulasAdapter extends BaseAdapter {
    private static final int ATIVADA = 1;
    private static final int DESATIVADA = 0;
    private final List<Formula> formulas;
    private final Context context;
    private final Preferencias preferencias;


    public FormulasAdapter(Context context, List<Formula> formulas, Preferencias preferencias) {
        this.context = context;
        this.formulas = formulas;
        this.preferencias = preferencias;
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

    public Preferencias getPreferencias() {
        return preferencias;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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

        Switch switchFormula = (Switch) view.findViewById(R.id.switch_formula);
        switchFormula.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Toast.makeText(context, "Botao da formula " + position + " " + isChecked, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case ListaFormulas.ID_BRITTO1:
                        if (isChecked) {
                            preferencias.setBritto1(ATIVADA);
                        } else {
                            preferencias.setBritto1(DESATIVADA);
                        }
                        break;
                    case ListaFormulas.ID_BRITTO2:
                        if (isChecked) {
                            preferencias.setBritto2(ATIVADA);
                        } else {
                            preferencias.setBritto2(DESATIVADA);
                        }
                        break;
                    case ListaFormulas.ID_DOURADO:
                        if (isChecked) {
                            preferencias.setDourado(ATIVADA);
                        } else {
                            preferencias.setDourado(DESATIVADA);
                        }
                        break;
                    case ListaFormulas.ID_SOARES_PEREIRA:
                        if (isChecked) {
                            preferencias.setSoaresPereira(ATIVADA);
                        } else {
                            preferencias.setSoaresPereira(DESATIVADA);
                        }
                        break;
                    case ListaFormulas.ID_IWAMA:
                        if (isChecked) {
                            preferencias.setIwama(ATIVADA);
                        } else {
                            preferencias.setIwama(DESATIVADA);
                        }
                        break;

                }

            }
        });
        return view;
    }
}
