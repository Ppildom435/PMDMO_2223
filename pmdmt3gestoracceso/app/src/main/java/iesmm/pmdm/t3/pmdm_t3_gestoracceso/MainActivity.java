package iesmm.pmdm.t3.pmdm_t3_gestoracceso;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final String LOGTAG = "PMDM";
    private final String FILENAME = "accesos.dat";
    private Date date = new Date();

    String txtTipo="Tipo";

    TableLayout tablelayout;
    TableRow tablerow;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mostrarTabla();


        for (int i=0; i<=3;i++) {
            mostrarBox("");
        }

        registrarEstado("ENTRADA ,","FECHA");

    }

    @Override
    protected void onDestroy() {

        salidaEstados("SALIDA ,", "FECHA");
        super.onDestroy();
    }

    private void registrarEstado(String entrada, String fecha) {
        Log.i(LOGTAG, "Se registra la entrada y fecha/hora");

        try {
            //1. Recuperar flujo de escritura
            DataOutputStream fout = new DataOutputStream(this.openFileOutput(FILENAME, MODE_APPEND));

            //Adquiero la fecha y hora local
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy , hh:mm ", Locale.getDefault());
            fecha = dateFormat.format(date);

            //2. Añadir al fichero
            fout.writeUTF(entrada);
            fout.writeUTF(fecha);

            //3. Cierre del flujo
            fout.close();

        }catch (FileNotFoundException e){
            Log.e(LOGTAG, "Fichero no encontrado");
        }catch (IOException e){
            Log.e(LOGTAG,"Error en entrada y salida");
        }catch (Exception e){
            Log.e(LOGTAG, "Error general");
        }

    }

    private void salidaEstados(String salida, String fecha){
        Log.i(LOGTAG, "Se registra la salida y fecha/hora");

        try {
            //1. Recuperar flujo de entrada
            DataOutputStream fout = new DataOutputStream(this.openFileOutput(FILENAME, MODE_APPEND));

            //Adquiero la fecha y hora local
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy , hh:mm ,", Locale.getDefault());
            fecha = dateFormat.format(date);

            //2. Añadir al fichero
            fout.writeUTF(salida);
            fout.writeUTF(fecha);

            //3. Cierre del flujo
            fout.close();

        }catch (FileNotFoundException e){
            Log.e(LOGTAG, "Fichero no encontrado");
        }catch (IOException e){
            Log.e(LOGTAG, "Error de entrada y salida");
        }catch (Exception e){
            Log.e(LOGTAG, "Error general");
        }

    }

    /*private void mostrarBox(String cad) {
        //1. Identificar el contenedor del layout
        LinearLayout layout = this.findViewById(R.id.container);

        //2. Personalizar el objeto Textview
        TextView box = new TextView(this);
        box.setPadding(10,20,10,10);
        box.setGravity(Gravity.CENTER);
        box.setTextColor(Color.RED);
        box.setText(cad);

        //3. Agregar TextView al contenedor
        layout.addView(box);
    }*/

    private void mostrarTabla(){
       tablelayout = this.findViewById(R.id.table);
       tablerow = new TableRow(this);

       tablelayout.addView(tablerow);

       tablerow.setGravity(Gravity.CENTER);

       String[] columnas = {"TIPO", "FECHA", "HORA"};

       for (int i=0;i<columnas.length; i++){
           TextView dato = new TextView(this);
           dato.setGravity(Gravity.CENTER);
           dato.setPadding(150,20,150,10);


           dato.setText(columnas[i]);

           tablerow.addView(dato);

       }

    }

    private void mostrarBox(String cad) {

        DataInputStream fin = null;
        try {
            fin = new DataInputStream(this.openFileInput(FILENAME));
            fin.readUTF();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /*private void mostrar(){
        ListView lista = this.findViewById(R.id.listview);

        List<String> listado = new ArrayList<String>();
        String linea;

        try {
            DataInputStream fin = new DataInputStream(this.openFileInput(FILENAME));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));

            if (fin!=null) {
                while ((linea = reader.readLine()) != null) {
                    listado.add(linea.split("" [0]));
                }
                fin.close();


                String datos[] = listado.toArray(new String[listado.size()]);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, datos);
                lista.setAdapter(adapter);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}