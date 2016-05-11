package br.edu.ufcspa.tc6m.controle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.modelo.Teste;

public class ValoresFinaisActivity extends AppCompatActivity {

    private TesteHelper helper;
    private Teste teste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valores_finais2);
        iniciaComponentes();

    }

    private void iniciaComponentes() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        teste = (Teste) intent.getSerializableExtra("teste");
        helper = new TesteHelper(this,teste);

        TextView titulo = (TextView) findViewById(R.id.tituloFinais);
        titulo.setText(teste.getPaciente().getNome());

        Button btSalvar = (Button)findViewById(R.id.btSalvarFinal);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teste = helper.pegaDadosFromFields(7);
                Intent intentVaiProValoresRecuperacao = new Intent(ValoresFinaisActivity.this, ValoresRecuperacaoActivity.class);
                intentVaiProValoresRecuperacao.putExtra("teste",teste);
                startActivity(intentVaiProValoresRecuperacao);
                finish();
            }

        });

    }

}
