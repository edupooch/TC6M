package br.edu.ufcspa.tc6m.controle;

import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Paciente;

/**
 * Created by edupooch on 17/02/16.
 */
public class FormularioHelper {

    private Paciente paciente;
    private final EditText campoNome;
    private final EditText campoData;
    private final EditText campoAltura;
    private final EditText campoPeso;
    private final EditText campoEmail;
    private final EditText campoTelefone;
    private RadioButton btnMasculino;
    private RadioButton btnFeminino;

    public FormularioHelper(FormularioActivity activity) {

        paciente = new Paciente();

        campoNome = (EditText) activity.findViewById(R.id.edTextNomePaciente);
        campoData = (EditText) activity.findViewById(R.id.edTextDataNascimento);
        btnFeminino = (RadioButton) activity.findViewById(R.id.btnFeminino);
        btnMasculino = (RadioButton) activity.findViewById(R.id.btnMasculino);
        campoAltura = (EditText) activity.findViewById(R.id.edTextAltura);
        campoPeso = (EditText) activity.findViewById(R.id.edTextPeso);
        campoEmail = (EditText) activity.findViewById(R.id.edTextEmail);
        campoTelefone= (EditText) activity.findViewById(R.id.edTextTelefone);

    }

    public Paciente pegaPaciente() {

        Paciente paciente = new Paciente();
        paciente.setNome(campoNome.getText().toString());
        paciente.setDataNascimento(campoData.getText().toString());
        paciente.setAltura(Double.valueOf(campoAltura.getText().toString()));
        paciente.setPeso(Double.valueOf(campoPeso.getText().toString()));
        paciente.setTelefone(campoTelefone.getText().toString());
        paciente.setEmail(campoEmail.getText().toString());
        if (btnMasculino.isChecked()) {
            paciente.setGenero(0);
        }if (btnFeminino.isChecked()) {
            paciente.setGenero(1);
        }

        return paciente;
    }

    public void preencheFormulário(Paciente paciente) {
        Log.i("FormularioActivity", "paciente.getNome() — get nome " + paciente.getNome());
        campoNome.setText(paciente.getNome());
        campoData.setText(paciente.getDataNascimento());
        if (paciente.getGenero() == 0){
            btnMasculino.setChecked(true);
        }else{
            btnFeminino.setChecked(true);
        }
        campoPeso.setText(String.valueOf(paciente.getPeso()));
        campoAltura.setText(String.valueOf(paciente.getAltura()));
        campoTelefone.setText(paciente.getTelefone());
        campoEmail.setText(paciente.getEmail());
        this.paciente = paciente;

    }

    public boolean validateFields() {

        return (!campoNome.getText().toString().isEmpty() &&
                !campoData.getText().toString().isEmpty() &&
                !campoPeso.getText().toString().isEmpty() &&
                !campoAltura.getText().toString().isEmpty() &&
                btnFeminino.isChecked() ^ btnMasculino.isChecked()

        );
    }
}
