package iesmm.pmdmo.pmdm_t2_tresenraya;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    //Representa el estado interno del juego
    private JuegoTresEnRaya mJuego;

    //Botones del layout
    private Button mBotonesTablero[];
    public Button mBotonReinicio;

    //Texto informativo del estado del juego
    private TextView mInfoTexto;

    private TextView mInfoJugador;
    private TextView mInfoMaquina;
    private TextView mInfoPartidas;

    //Determina quien será primer turno (TURNO INICIAL)
    private char mTurno = JuegoTresEnRaya.JUGADOR;

    //Determina si ha acabado el juego
    private boolean gameOver = false;

    //Determina el número de partidas ganadas del jugador y la máquina, y el total de las jugadas
    private int contJugador;
    private int contMaquina;
    private int contPartidas;

    //Determina la voz que habla cuando el jugador o la maquina ganen una partida
    private TextToSpeech sintetizador;

    //Determina los efectos de sonido y la música de fondo
    private MediaPlayer mBackgroudMusicPlayer;
    private MediaPlayer mJugadorMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showToast(R.string.toast_start_game);

        //Referencia de los botones del tablero
        mBotonesTablero = new Button[JuegoTresEnRaya.DIM_TABLERO];

        mBotonesTablero[0] = (Button) findViewById(R.id.one);
        mBotonesTablero[1] = (Button) findViewById(R.id.two);
        mBotonesTablero[2] = (Button) findViewById(R.id.three);
        mBotonesTablero[3] = (Button) findViewById(R.id.four);
        mBotonesTablero[4] = (Button) findViewById(R.id.five);
        mBotonesTablero[5] = (Button) findViewById(R.id.six);
        mBotonesTablero[6] = (Button) findViewById(R.id.seven);
        mBotonesTablero[7] = (Button) findViewById(R.id.eight);
        mBotonesTablero[8] = (Button) findViewById(R.id.nine);

        //Referencia al boton de reinicio del juego
        mBotonReinicio = (Button) findViewById(R.id.new_game);

        //Activación del efecto de sonido
        mJugadorMediaPlayer = MediaPlayer.create(this, R.raw.swordsound);

        //Activación de la música de fondo
        mBackgroudMusicPlayer = MediaPlayer.create(this, R.raw.musicafondo);
        //Comienza la música de fondo en bucle
        mBackgroudMusicPlayer.setLooping(true);
        mBackgroudMusicPlayer.start();



        mBotonReinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View boton2) {
                //Al hacer click en el boton de New Game la aplicación se cierra con el finish
                //y justo detrás con el startActivity esta empieza de nuevo a funcionar
                finish();
                startActivity(getIntent());

            }
        });

        //Referencia de los textos informativos del estado del juego
        mInfoTexto = (TextView) findViewById(R.id.information);

        //Referencia de los textos informativos de la puntucación del jugador, máquina y el número de partidas
        mInfoJugador = (TextView) findViewById(R.id.player_score);
        mInfoMaquina = (TextView) findViewById(R.id.computer_score);
        mInfoPartidas = (TextView) findViewById(R.id.tie_score);

        //Referencia al textToSpeech, para que nos indique si el jugador gana o pierde
        sintetizador = new TextToSpeech(this,this);

        //Ejecución inicial de la lógica del videojuego
        mJuego = new JuegoTresEnRaya();
        comenzarJuego();

        //Liberamos el sintetizador
        sintetizador.shutdown();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Liberamos los sonidos para cuando pausemos la aplicación no se siga escuchando de fondo
        mBackgroudMusicPlayer.release();
        mJugadorMediaPlayer.release();
    }

    public void onClick(View boton){
        //1. Localizamos cuál es el botón que se pulsa y su número de casilla correcto
        int id = boton.getId();
        String descripcionBoton = ((Button) findViewById(id)).getContentDescription().toString();
        int casilla = Integer.parseInt(descripcionBoton) - 1;

        //2. Determinamos si es posible colocar la ficha en la casilla
        if(mBotonesTablero[casilla].isEnabled()){
            colocarFichaEnTablero(JuegoTresEnRaya.JUGADOR, casilla);
        }

    }

    private DialogInterface.OnClickListener comenzarJuego(){
        //Reinicio de la lógica del tablero
        mJuego.limpiarTablero();

        //Reinicio de los botones del layout
        for (int i=0; i< mBotonesTablero.length; i++){
            mBotonesTablero[i].setText("");
            mBotonesTablero[i].setBackgroundResource(R.drawable.rectangulo);
            mBotonesTablero[i].setEnabled(true);
        }

        //Turno inicial del juego: JUGADOR O MÁQUINA
        controlarTurno();
        return null;
    }

    public void controlarTurno(){
        if(mTurno==JuegoTresEnRaya.JUGADOR){
            mInfoTexto.setText(R.string.primero_jugador);
        }else if (mTurno==JuegoTresEnRaya.MAQUINA){
            //1.Determinamos la posicion segun nivel
            int casilla = mJuego.getMovimientoMaquina();

            //2. Actualiza layout
            colocarFichaEnTablero(JuegoTresEnRaya.MAQUINA, casilla);

            //3.Actualizacion turno: JUGADOR
            if(!gameOver){
                mTurno=JuegoTresEnRaya.JUGADOR;
                mInfoTexto.setText(R.string.turno_jugador);
            }
        }
    }

    public void colocarFichaEnTablero(char jugador, int casilla){
        //1. Mueve la ficha según lógica
        mJuego.moverFicha(jugador, casilla);

        //2. Actualización y representación en el layout
        //Desactiva el control del botón determinado
        mBotonesTablero[casilla].setEnabled(false);

        //3.Se representa la ficha
        if(jugador==JuegoTresEnRaya.JUGADOR){
            //mBotonesTablero[casilla].setTextColor(Color.rgb(0, 200, 0));
            mBotonesTablero[casilla].setBackgroundResource(R.drawable.jugador);
            //Comienza el efecto de sonido
            mJugadorMediaPlayer.start();

        }else{
            //mBotonesTablero[casilla].setTextColor(Color.rgb(200,0,0));
            mBotonesTablero[casilla].setBackgroundResource(R.drawable.maquina);
        }

        //4. Se comprueba: ESTADO DEL JUEGO (SI NO ACABA SE CONTINUA)
        int estadoJuego = comprobarEstadoJuego();

        if (estadoJuego==1 || estadoJuego==2){
            gameOver();
        }else if (estadoJuego==0){
            if (jugador==JuegoTresEnRaya.JUGADOR){
                mTurno=JuegoTresEnRaya.MAQUINA;
            }else if (jugador==JuegoTresEnRaya.MAQUINA) {
                mTurno = JuegoTresEnRaya.JUGADOR;


            }
            //5. Pasa el siguiente turno
            controlarTurno();
        }
    }

    public int comprobarEstadoJuego(){
        //1. Comprobar el estado principal del tablero
        int estado = mJuego.comprobarGanador();

        //2. Representar el estado del juego
        if (estado==1) {
            contJugador++;
            mInfoTexto.setText(R.string.result_human_wins);
            mInfoJugador.setText(String.valueOf(contJugador));
            showToast(R.string.toast_player_win);
            habla("El jugador ha ganado, sigue jugando");
        }else if (estado==2){
            contMaquina++;
            mInfoTexto.setText(R.string.result_computer_wins);
            mInfoMaquina.setText(String.valueOf(contMaquina));
            showToast(R.string.toast_computer_win);
            habla("El jugador ha perdido, juega otra para ganar");
        }
        return estado;
    }


    private void gameOver() {
        //1. Actualiza la variable de control de finalización del juego
        gameOver = true;

        //2. Reinicio de los botones del layout a desactivados
        for (int i = 0; i < mBotonesTablero.length; i++) {
            mBotonesTablero[i].setEnabled(false);
        }
        contPartidas++;
        mInfoPartidas.setText(String.valueOf(contPartidas));

        dialogoReinicioPartida();


    }

    public void dialogoReinicioPartida(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.game_over);
        builder.setMessage(R.string.restart_game);

        builder.setPositiveButton(R.string.accept,comenzarJuego());

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void showToast(int m){
        //Creamos la notificación tipo Toast
        Toast msg = Toast.makeText(this, m,Toast.LENGTH_LONG);
        //Mostramos la notificacion
        msg.show();

    }

    private void habla(String m){
        sintetizador.speak(m,TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int i) {
        sintetizador.setLanguage(Locale.getDefault());
        sintetizador.setPitch(1f);//Tono de voz
        sintetizador.setSpeechRate(1f);//Velocidad al hablar
    }
}