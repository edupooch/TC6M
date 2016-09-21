package br.edu.ufcspa.tc6m.controle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Paciente;
import br.edu.ufcspa.tc6m.modelo.Teste;

public class ValoresBasaisActivity extends AppCompatActivity { //implementar como fragment depois

    private TesteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valores_basais);
        iniciaComponentes();

    }

    private void iniciaComponentes() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /////
        Intent intent = getIntent();
        final Paciente paciente = (Paciente) intent.getSerializableExtra("paciente");

        helper = new TesteHelper(this, paciente);

        TextView edTextTitulo = (TextView) findViewById(R.id.tituloBasais);
        edTextTitulo.setText(paciente.getNome());



        ////////////////////////////////////////////////////////////////////////////////////////////
        Button btNext = (Button) findViewById(R.id.btComecarTeste);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Teste teste = helper.pegaDadosFromFields(0);
                teste.setMassa(paciente.getMassa());
                teste.setEstatura(paciente.getEstatura());
                teste.setIdade(Calcula.idade(paciente.getDataNascimento()));
                Intent intentVaiProCronometro = new Intent(ValoresBasaisActivity.this, TesteActivity.class);
                intentVaiProCronometro.putExtra("teste", teste);
                startActivity(intentVaiProCronometro);
                finish();
            }
        });


        ////////////////////////////////////////////////////////////////////////////////////////////


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                //NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //DIALOGO DO BOTÃO VOLTAR
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.atencao_sair);
        builder.setMessage(getString(R.string.dialog_abandonar_voltar));
        builder.setPositiveButton(getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
