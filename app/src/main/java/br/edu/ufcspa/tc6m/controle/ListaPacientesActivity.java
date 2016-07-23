package br.edu.ufcspa.tc6m.controle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.edu.ufcspa.tc6m.R;
import br.edu.ufcspa.tc6m.adapter.PacientesAdapter;
import br.edu.ufcspa.tc6m.dao.PacienteDAO;
import br.edu.ufcspa.tc6m.dao.TesteDAO;
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

                Intent intentVaiProPerfil = new Intent(ListaPacientesActivity.this, PerfilActivity.class);
                intentVaiProPerfil.putExtra("paciente", paciente);
                startActivity(intentVaiProPerfil);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btAdicionarPaciente);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaPacientesActivity.this, FormularioActivity.class));
            }
        });
    }

    private void carregaLista() {

        listaPacientes = (ListView) findViewById(R.id.lista_pacientes);


        PacienteDAO dao = new PacienteDAO(this);
        List<Paciente> pacientes = dao.buscaPacientes();
        Collections.sort(pacientes);
        //ArrayAdapter<Paciente> adapter = new ArrayAdapter<Paciente>(this, R.layout.list_item_pacientes, pacientes);

        PacientesAdapter adapter = new PacientesAdapter(this, pacientes);
        listaPacientes.setAdapter(adapter);

        TextView textInicial = (TextView) findViewById(R.id.textInicial);
        if (listaPacientes.getCount() == 0) {
            assert textInicial != null;
            textInicial.setText(R.string.text_inicial_vazio);
        } else {
            assert textInicial != null;
            textInicial.setText(R.string.text_inicial);
        }

        dao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Paciente paciente = (Paciente) listaPacientes.getItemAtPosition(info.position);


        MenuItem ligar = menu.add("Ligar");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(ListaPacientesActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaPacientesActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 123);

                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + paciente.getTelefone()));
                    startActivity(intentLigar);

                }
                return false;
            }
        });

        MenuItem enviarSms = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + paciente.getTelefone()));
        enviarSms.setIntent(intentSMS);

        MenuItem enviarEmail = menu.add("Enviar Email");
        enviarEmail.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String[] endereco = {paciente.getEmail()};
                composeEmail(endereco,"Teste da Caminhada de Seis Minutos");
                return false;
            }
        });

    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
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

            case R.id.action_config:
                Intent intentVaiPrasPreferencias = new Intent(ListaPacientesActivity.this, PreferenciasActivity.class);
                startActivity(intentVaiPrasPreferencias);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
