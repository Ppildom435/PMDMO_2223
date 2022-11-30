package iesmm.pmdm.t3.pmdm_t4_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = this.findViewById(R.id.boton_iniciar_sesion);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Comprobar lo introducido en los campos
                String correo = ((TextView) findViewById(R.id.input_usuario)).getText().toString();
                String password = ((TextView) findViewById(R.id.input_contrasena)).getText().toString();

                if (getAccess(correo, password)){
                    //Construccion del conjunto de datos a transferir
                    Bundle bundle = new Bundle();
                    bundle.putString("email",correo);

                    //Llamada al intent
                    Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                    i.putExtras(bundle);
                    startActivity(i);
                }else{
                    Snackbar.make(view,"Error de acceso",Snackbar.LENGTH_SHORT).show();
                }



                //correo = getIntent().getStringExtra("correo");
                //password = getIntent().getStringExtra("password");
            }
        });
    }

    /**
     * Devuelve cierto si se confima que el correo y el password son correctos y se localizan en el fichero
     * @param correo email de acceso
     * @param password password propuesto
     * @return Devuelve cierto si es correcto y false si es incorrecto
     */
    private boolean getAccess(String correo, String password) {

        return true;
    }


}