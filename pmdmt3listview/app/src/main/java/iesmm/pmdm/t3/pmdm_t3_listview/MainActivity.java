package iesmm.pmdm.t3.pmdm_t3_listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter adaptador; //Adaptador controlador de datos del listView
    private final String FILENAME = "Lista.txt";

    private String nif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
    }

    private void loadData() {
        //1. Cargar datos iniciales
        ArrayList cadenas = new ArrayList();

        //2.Cargar datos en listView
        addItemsInListView(cadenas);
    }

    private void addItemsInListView(ArrayList cadenas) {
        //1. Localizar el listview dentro del layout
        ListView lista = this.findViewById(R.id.listView1);

        //2. Crear adaptador de datos y vincular los datos que vamos a ppresentar en el listView
        adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,cadenas);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String txt = "N de posición: " +i+ "\n N. de elementos de lista: " +adaptador.getCount();
                txt += "\n Valor del elemento: " +adaptador.getItem(i).toString();

                Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void putItem(View view) {
        //1. Localizar el edittext dentro del layout
        EditText texto = this.findViewById(R.id.editText);

        //2. Obtener texto de la interfaz y añadirlo al adaptador
        if (!texto.getText().toString().isEmpty() && validar()){
            adaptador.add(texto.getText().toString());
            adaptador.sort(Comparator.naturalOrder());
        }else
            Toast.makeText(this, "Introduce elemento", Toast.LENGTH_SHORT).show();
    }

    public void clearItems(View view) {
        //1. Volcar el contenido del listview a un fichero de memoria externa
        escribirFichero();
        //2. Vaciar el ListView
        adaptador.clear();
    }

    private void escribirFichero()  {
        //1. Obtener primero ruta de directorio inicial del punto de montaje de memoria externa
        File dir = this.getExternalFilesDir(null);

        if (dir.canWrite()){
            //File file = new File("dir" +File.pathSeparator+ FILENAME);
            File file = new File(dir, FILENAME);
            Toast.makeText(this, file.getAbsolutePath().toString(), Toast.LENGTH_SHORT).show();

            try {
                FileWriter fout = new FileWriter(file);

                for (int i=0;i<adaptador.getCount();i++){
                    fout.write(adaptador.getItem(i).toString()+"\n");
                }
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            Toast.makeText(this, "Error e/s", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validar(){
        String letra="";

        if (nif.length()!=9 || Character.isLetter(this.nif.charAt(8))==false){
            return false;
        }

        letra = this.nif.substring(8).toUpperCase();

        if(soloNumeros()==true && letraNIF().equals(letra)){
            return true;
        }else{
            return false;
        }
    }

    private boolean soloNumeros(){
        String numero="";
        String miNIF="";

        String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};

        for (int i=0;i<this.nif.length()-1; i++){
            numero = this.nif.substring(i, i+1);

            for (int j=0;i<unoNueve.length;j++){
                if (numero.equals(unoNueve[j])){
                    miNIF += unoNueve[j];
                }
            }
        }

        if (miNIF.length() != 8){
            return false;
        }else{
            return true;
        }
    }

    private String letraNIF(){
        int miDNI = Integer.parseInt(this.nif.substring(0,8));
        int resto=0;
        String miLetra="";

        String[] asignacionLetra = {"A","B","C","D","E","F","G","H","I","J","K","L","M",
                                    "N","0","P","Q","R","S","T","U","V","W","X","Y","Z"};

        resto=miDNI % 23;

        miLetra=asignacionLetra[resto];

        return miLetra;

    }
}