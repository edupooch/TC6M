package br.edu.ufcspa.tc6m.controle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import br.edu.ufcspa.tc6m.modelo.Paciente;

public class ListaPacientesActivity extends AppCompatActivity {


    private ListView listaPacientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacientes);
        iniciaComponentes();
        registerForContextMenu(listaPacientes);
    }

    private void iniciaComponentes() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carregaLista();
        listaPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Paciente paciente = (Paciente) listaPacientes.getItemAtPosition(position);
                // paciente clicado

               /*
                Intent intentVaiProFormulario = new Intent(ListaPacientesActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("paciente", paciente);
                startActivity(intentVaiProFormulario);
                */

                Intent intentVaiProPerfil = new Intent(ListaPacientesActivity.this, PerfilActivity.class);
                intentVaiProPerfil.putExtra("paciente",paciente);
                startActivity(intentVaiProPerfil);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btAdicionarPaciente);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FormularioActivity.class));
            }
        });
    }

    private void carregaLista() {

        listaPacientes = (ListView) findViewById(R.id.lista_pacientes);



        PacienteDAO dao = new PacienteDAO(this);
        List<Paciente> pacientes = dao.buscaPacientes();
        ArrayAdapter<Paciente> adapter =
                new ArrayAdapter<Paciente>(this, android.R.layout.simple_list_item_1, pacientes);
        listaPacientes.setAdapter(adapter);
        dao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                Paciente paciente = (Paciente) listaPacientes.getItemAtPosition(info.position);

                PacienteDAO dao = new PacienteDAO(ListaPacientesActivity.this);
                dao.deleta(paciente);
                dao.close();

                carregaLista();

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_lista:
                startActivity(new Intent(getApplicationContext(), ListaPacientesActivity.class));
                break;

            case R.id.action_cronometro:
                startActivity(new Intent(getApplicationContext(), CronometroActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
