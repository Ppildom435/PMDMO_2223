package iesmm.pmdm.t3.pmdm_t4_agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   ListView lista;
   private static final String FILENAME ="contactos.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.agenda);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mostrarDialogo();
            }
        });

        CargarDatos();

    }



    public void CargarDatos(){
        List<String> agenda = new ArrayList<String>();
        String linea;

        try {
            InputStream is = openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            if (is != null) {
                while ((linea = reader.readLine()) != null) {
                    agenda.add(linea.split(";")[0]);
                }
            }
            is.close();

            String datos[]=agenda.toArray(new String[agenda.size()]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datos);
            lista.setAdapter(adapter);
        }catch (IOException e){
            System.out.println("No se detecta el archivo");
        }
    }

    private void mostrarDialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Que acción quieres realizar?");
        builder.setItems(R.array.opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

            }
        });
    }
}