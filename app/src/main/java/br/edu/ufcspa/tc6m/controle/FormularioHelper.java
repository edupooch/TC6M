package br.edu.ufcspa.tc6m.controle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private final EditText campoObs;
    private final ImageButton btFoto;
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
        campoTelefone = (EditText) activity.findViewById(R.id.edTextTelefone);
        campoTelefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        campoObs = (EditText) activity.findViewById(R.id.edTextObs);
        btFoto = (ImageButton) activity.findViewById(R.id.btFoto);


    }

    public Paciente pegaPacienteFromFields(Long id) {
        Paciente paciente = new Paciente();

        if (id != null) paciente.setId(id);
        paciente.setNome(campoNome.getText().toString());

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            paciente.setDataNascimento(new java.sql.Date(format.parse(campoData.getText().toString()).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        paciente.setEstatura(Double.valueOf(campoAltura.getText().toString()));
        paciente.setMassa(Double.valueOf(campoPeso.getText().toString()));
        paciente.setTelefone(campoTelefone.getText().toString());
        paciente.setEmail(campoEmail.getText().toString());
        paciente.setObs(campoObs.getText().toString());
        paciente.setCaminhoFoto((String) btFoto.getTag());
        if (btnMasculino.isChecked()) {
            paciente.setGenero(1);
        }
        if (btnFeminino.isChecked()) {
            paciente.setGenero(0);
        }
        return paciente;
    }

    public void preencheFormulário(Paciente paciente) {
        campoNome.setText(paciente.getNome());
        String[] arrayData = paciente.getDataNascimento().toString().split("-");
        String strData = arrayData[2] + "/" + arrayData[1] + "/" + arrayData[0];
        campoData.setText(strData);
        campoData.setFocusable(false);
        campoData.setEnabled(false);
        if (paciente.getGenero() == 0) {
            btnMasculino.setChecked(true);
        } else {
            btnFeminino.setChecked(true);
        }
        btnMasculino.setEnabled(false);
        btnFeminino.setEnabled(false);
        campoPeso.setText(String.valueOf(paciente.getMassa()));
        campoAltura.setText(String.valueOf(paciente.getEstatura()));
        campoTelefone.setText(paciente.getTelefone());

        campoEmail.setText(paciente.getEmail());
        campoObs.setText(paciente.getObs());
        carregaImagem(paciente.getCaminhoFoto());
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

    /**
     * Método que carrega a imagem no espaço do ícone
     * @param caminhoFoto
     */
    public void carregaImagem(String caminhoFoto) {
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            bitmap = Bitmap.createScaledBitmap(bitmap, 250, 200, true);
            btFoto.setImageBitmap(bitmap);
            btFoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
            btFoto.setTag(caminhoFoto);
        }
    }
}
