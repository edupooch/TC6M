package br.edu.ufcspa.tc6m.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.dao.PacienteDAO;
import br.edu.ufcspa.tc6m.dao.TesteDAO;
import br.edu.ufcspa.tc6m.modelo.Paciente;

/**
 * Created by edupooch on 14/05/16.
 */
public class PacientesAdapter extends BaseAdapter {

    private final List<Paciente> pacientes;
    private final Context context;

    public PacientesAdapter(Context context, List<Paciente> pacientes) {
        this.context = context;
        this.pacientes = pacientes;
    }

    @Override
    public int getCount() {
        return pacientes.size();
    }

    @Override
    public Object getItem(int position) {
        return pacientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pacientes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Paciente paciente = pacientes.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;
        if (view == null) { //usa -se o convertView para otimizar o carregamento de listas muito grandes
            view = inflater.inflate(R.layout.list_item_pacientes, parent, false);
            //parent passa os valores do pai para funcionar os tamanhos pré-definidos no xml
            //false é usado para não adicionar a view duas vezes
        }
        TesteDAO dao = new TesteDAO(context);
        int nTestes = dao.contarTestesDoPaciente(paciente);
        dao.close();

        TextView textNome = (TextView) view.findViewById(R.id.textNomePacienteLista);
        textNome.setText(paciente.getNome());

        TextView textTestes = (TextView) view.findViewById(R.id.textNumeroTestesLista);
        textTestes.setText(String.valueOf(nTestes));

        TextView textPlural = (TextView) view.findViewById(R.id.textTestesPluralLista);
        if (nTestes == 1) textPlural.setText(R.string.teste_singular);


        return view;
    }
}
