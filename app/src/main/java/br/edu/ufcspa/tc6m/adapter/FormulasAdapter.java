package br.edu.ufcspa.tc6m.adapter;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;


import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.controle.ReferenciasActivity;
import br.edu.ufcspa.tc6m.formulas.ListaFormulas;
import br.edu.ufcspa.tc6m.modelo.Formula;

/**
 * Cria a lista de fórmulas e alteras o arquivo de preferências no listener do switch.
 * <p/>
 * Não é necessário alterar este código quando adicionar uma fórmula nova.
 */
public class FormulasAdapter extends BaseAdapter {

    private static final int ATIVADA = 1;
    private static final int DESATIVADA = 0;

    private final List<Formula> formulas;
    private final Context context;
    private final SharedPreferences sharedPref;


    public FormulasAdapter(Context context, List<Formula> formulas, SharedPreferences sharedPref) {
        this.context = context;
        this.formulas = formulas;
        this.sharedPref = sharedPref;
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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final Formula formula = formulas.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;

        if (view == null) { //usa -se o convertView para otimizar o carregamento de listas muito grandes
            view = inflater.inflate(R.layout.list_item_formulas, parent, false);
            //parent passa os valores do pai para funcionar os tamanhos pré-definidos no xml
            //false é usado para não adicionar a view duas vezes
        }

        TextView textAutores = (TextView) view.findViewById(R.id.text_autores_formula);
        textAutores.setText(formula.getAutores());
        textAutores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(formula.getReferencia())
                        .setCancelable(false)
                        .setTitle("Referência:")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        TextView textCoeficiente = (TextView) view.findViewById(R.id.text_coeficiente);
        double coeficiente = formula.getCoeficienteExplicacao();
        String stringCoeficiente = "r² = " + String.format(Locale.getDefault(), "%.2f", coeficiente);
        textCoeficiente.setText(stringCoeficiente);

        TextView textFormula = (TextView) view.findViewById(R.id.text_formula);
        textFormula.setText(formula.getFormula());

        final Switch switchFormula = (Switch) view.findViewById(R.id.switch_formula);


        //Se sharedPref.getInt("FORMULA_" + position) for 1, o check será posto em true
        //o valor default é 1
        switchFormula.setChecked(sharedPref.getInt("FORMULA_" + position, ATIVADA) == ATIVADA);
        System.out.println("FORMULA_" + position + " valor = " + sharedPref.getInt("FORMULA_" + position, ATIVADA));

        //Salva nas preferências assim que o switch é alterado
        switchFormula.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switchFormula.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        /**
                         * Método altera a referência de ativada da formula para mudança no switch
                         */
                        int ativacao;
                        if (switchFormula.isChecked()) {
                            ativacao = ATIVADA;
                        } else {
                            ativacao = DESATIVADA;
                        }
                        System.out.println("FORMULA_" + position + " valor = " + ativacao);

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("FORMULA_" + position, ativacao);
                        editor.apply();

                    }
                });
                return false;
            }
        });


        return view;
    }

}
