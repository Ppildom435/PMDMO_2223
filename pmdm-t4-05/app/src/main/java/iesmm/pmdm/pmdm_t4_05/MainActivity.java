package iesmm.pmdm.pmdm_t4_05;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Instanciación de componentes visuales para su control
        Button start = (Button) this.findViewById(R.id.button);

        // 2. Vinculamos el control del escuchador de eventos con el componente
        start.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                showProgress();

                // Inicio de tarea asíncrona
                new incrementaBarra().execute();

                break;
        }
    }



    /* Muestra un cuadro de diálogo con barra de progreso */
    private void showProgress() {
        // OTRA OPCIÓN: Método estático de inicio: ProgressDialog.show(this, titulo, mensaje);
        progress = new ProgressDialog(this);
        progress.setTitle("Descarga simulada"); // Titulo
        progress.setMessage("Cargando"); // Contenido

        // Propiedades de configuración
        progress.setMax(100); // Valor máximo de la barra de progreso
        progress.setCancelable(true); // Permitir que se cancele por el usuario

        // Tipo de barra de progreso: ProgressDialog.STYLE_SPINNER / STYLE_HORIZONTAL
        //progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        // Mostrar cuadro de diálogo
        progress.show();

        // Incrementar valor del progreso en n. unidades
        //progress.incrementProgressBy(10);// Aumenta en 1 unidad



        // Obtener valor de la barra de progreso
        //progress.getProgress();

        // Obtener valor máximo de la barra de progreso
        // progress.getMax();

        // Finalización del cuadro de diálogo
        // progress.cancel();
    }

    private class incrementaBarra extends AsyncTask<Void, Integer, ProgressDialog> {

        private final int DELAY = 1000; // 1 segundo de espera
        private final String TAG = "PMDM";

        @Override
        protected void onPreExecute() {
            showProgress();
        }

        @Override
        protected ProgressDialog doInBackground(Void... voids) {
            Log.i("PMDM", "Se inicia el doInBackground");

            //Aumentar barra de carga de 10 en 10 hasta 100

            while (progress.getProgress()<progress.getMax()){
                //Espera 3 segundos
                SystemClock.sleep(DELAY);
                //Sirve para utilizar la actualización del progresUpdate
                publishProgress(progress.getProgress());


            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //Actualiza
            progress.incrementProgressBy(10);



        }

        @Override
        protected void onPostExecute(ProgressDialog unused) {
            super.onPostExecute(unused);
            //Termina
            progress.cancel();
        }




    }
}