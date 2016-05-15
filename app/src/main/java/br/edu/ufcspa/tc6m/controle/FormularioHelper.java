package br.edu.ufcspa.tc6m.controle;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Paciente;

/**
 * Created by edupooch on 17/02/16.
 */
public class FormularioHelper {

    ;
    private Paciente paciente;
    private final EditText campoNome;
    private final EditText campoData;
    private final EditText campoAltura;
    private final EditText campoPeso;
    private final EditText campoEmail;
    private final EditText campoTelefone;
    private final EditText campoObs;
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
        campoTelefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        campoObs= (EditText) activity.findViewById(R.id.edTextObs);


    }

    public Paciente pegaPacienteFromFields(Long id) {
        Paciente paciente = new Paciente();

        if (id != null) paciente.setId(id);
        paciente.setNome(campoNome.getText().toString());
        paciente.setDataNascimento(campoData.getText().toString());
        paciente.setEstatura(Double.valueOf(campoAltura.getText().toString()));
        paciente.setMassa(Double.valueOf(campoPeso.getText().toString()));
        paciente.setTelefone(campoTelefone.getText().toString());
        paciente.setEmail(campoEmail.getText().toString());
        paciente.setObs(campoObs.getText().toString());
        if (btnMasculino.isChecked()) {
            paciente.setGenero(1);
        }if (btnFeminino.isChecked()) {
            paciente.setGenero(0);
        }
        return paciente;
    }

    public void preencheFormulário(Paciente paciente) {
        campoNome.setText(paciente.getNome());
        campoData.setText(paciente.getDataNascimento());
        if (paciente.getGenero() == 0){
            btnMasculino.setChecked(true);
        }else{
            btnFeminino.setChecked(true);
        }
        campoPeso.setText(String.valueOf(paciente.getMassa()));
        campoAltura.setText(String.valueOf(paciente.getEstatura()));
        campoTelefone.setText(paciente.getTelefone());

        campoEmail.setText(paciente.getEmail());
        campoObs.setText(paciente.getObs());
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
