package iesmm.pmdm.t3.pmdm_t3_configapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String filename = "Datos";
    EditText cadenaemail, cadenapassword;
    String email, password;
    int cont = 0;
    TextView contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cadenaemail = findViewById(R.id.campo_email);
        cadenapassword = findViewById(R.id.campo_password);

        existeFichero(filename);
        recuperarConfiguracion();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        switch (item.getItemId()){
            case R.id.save_settings:
                almacenarConfiguracion();
                contador = findViewById(R.id.contador);

                cont++;
                contador.setText(Integer.toString(cont));
                break;
            case R.id.load_settings:
                recuperarConfiguracion();
                break;
            case R.id.reset_settings:
                eliminarConfiguracion();

            default:
                Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void almacenarConfiguracion() {
        email = cadenaemail.getText().toString();
        password = cadenapassword.getText().toString();



        //Escritura del flujo
        try {
            FileOutputStream fos = this.openFileOutput(filename, Context.MODE_PRIVATE);
            String cadenaOutput = new String("Contenido del fichero\n");
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeBytes(cadenaOutput);


            //Recuperar el objeto de las preferencas
            SharedPreferences pref = this.getSharedPreferences("mispreferencias", Context.MODE_PRIVATE);

            //Editar preferencias compartidas
            SharedPreferences.Editor editor = pref.edit();

            //Almacenar par de clave-valor
            editor.putString("Email", email);
            editor.putString("Password", password);

            //Confirmar los cambios
            editor.commit();

            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void recuperarConfiguracion() {

        //Recuperar el objeto de las preferencias
        SharedPreferences pref = this.getSharedPreferences("mispreferencias", Context.MODE_PRIVATE);

        //Recargar valores
        String email_p = pref.getString("Email", null);
        String password_p = pref.getString("Password", null);

        if (email_p != null || password_p != null){
            //Establecer correo y password
            EditText text = (EditText) this.findViewById(R.id.campo_email);
            EditText text2 = (EditText) this.findViewById(R.id.campo_password);
            text.setText(email_p);
            text2.setText(password_p);
        }
    }

    private void eliminarConfiguracion(){

        //Recuperar el objeto de las preferencias
        SharedPreferences pref = this.getSharedPreferences("mispreferencias", Context.MODE_PRIVATE);

        //Editar preferencias compartidas
        SharedPreferences.Editor editor = pref.edit();

        //Limpiar las preferencias compartidas
        editor.clear().apply();
    }


    public boolean existeFichero (String filename){
        File file = this.getFileStreamPath(filename);
        return file.exists();
    }

}