package iesmm.pmdm.t3.pmdm_t4_login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Recuperar datos del bundle transmitido
        Bundle bundle = this.getIntent().getExtras();

        if (bundle!=null){
            String email = bundle.getString("email");
            TextView textView = this.findViewById(R.id.welcome);
            textView.setText("Bienvenido \n " +email);
        }
    }
}