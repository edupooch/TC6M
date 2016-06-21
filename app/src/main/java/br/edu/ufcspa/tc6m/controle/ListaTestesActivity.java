package br.edu.ufcspa.tc6m.controle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.dao.PacienteDAO;
import br.edu.ufcspa.tc6m.dao.TesteDAO;
import br.edu.ufcspa.tc6m.modelo.Paciente;
import br.edu.ufcspa.tc6m.modelo.Teste;

public class ListaTestesActivity extends AppCompatActivity {

    private ListView listaTestes;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_testes);
//       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iniciaComponentes();
        registerForContextMenu(listaTestes);


    }

    private void iniciaComponentes() {
        Intent intent = getIntent();
        paciente = (Paciente) intent.getSerializableExtra("paciente");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ///
        carregaLista();
    }

    private void carregaLista() {
        listaTestes = (ListView) findViewById(R.id.lista_testes);


        TesteDAO dao = new TesteDAO(this);
        List<Teste> testes = dao.buscaTestes(paciente);

        ArrayAdapter<Teste> adapter =
                new ArrayAdapter<Teste>(this, android.R.layout.simple_list_item_1, testes);
        listaTestes.setAdapter(adapter);

        listaTestes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Teste teste = (Teste) listaTestes.getItemAtPosition(position);
                // teste clicado

                Intent intentVaiPraAnalise = new Intent(ListaTestesActivity.this, AnaliseTesteActivity.class);
                intentVaiPraAnalise.putExtra("teste", teste);
                startActivity(intentVaiPraAnalise);
            }
        });

        dao.close();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar Teste");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Teste teste  = (Teste) listaTestes.getItemAtPosition(info.position);
                TesteDAO dao = new TesteDAO(ListaTestesActivity.this);

                dao.deleta(teste);
                dao.close();

                carregaLista();
                return false;
            }
        });
    }

}
