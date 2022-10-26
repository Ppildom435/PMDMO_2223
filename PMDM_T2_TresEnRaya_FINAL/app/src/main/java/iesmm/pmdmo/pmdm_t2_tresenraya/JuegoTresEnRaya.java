package iesmm.pmdmo.pmdm_t2_tresenraya;

import java.util.Random;

public class JuegoTresEnRaya {
    // Número de casillas del tablero
    public static final int DIM_TABLERO = 9;

    // Constantes usadas para representar JUGADOR, MÁQUINA, EN BLANCO
    public static final char JUGADOR = 'X';
    public static final char MAQUINA = 'O';
    public static final char BLANCO = ' ';

    // Número aleatorio generado para calcular MAQUINA
    private Random aleatorio;

    // Estructura de dato del tablero
    private char tablero[];

    public JuegoTresEnRaya() {
        tablero = new char[DIM_TABLERO];
        aleatorio = new Random();
        limpiarTablero();
    }

    public char[] getTablero() {
        return tablero;
    }

    public void setTablero(char[] tablero) {
        this.tablero = tablero;
    }

    /**
     * Limpia el tablero de todas las X y O
     */
    public void limpiarTablero() {
        // Resetea todos las casillas
        for (int i = 0; i < DIM_TABLERO; i++)
            tablero[i] = BLANCO;
    }

    /**
     * Pone una ficha del tipo en la casilla correspondiente del tablero. La
     * casilla debe estar en BLANCO y dentro de los límites del tablero.
     *
     * @param ficha   - JUGADOR ó MÁQUINA
     * @param casilla - La ubicación de la casilla del tablero (0-[DIM_TABLERO-1])
     *                que se pondrá ficha
     * @return TRUE si el movimiento fue realizado, FALSE en caso contrario.
     */
    public boolean moverFicha(char ficha, int casilla) {
        boolean casilla_ocupada = false;

        if (casilla >= 0 && casilla < DIM_TABLERO && tablero[casilla] == BLANCO) {
            tablero[casilla] = ficha;
            casilla_ocupada = true;
        }

        return casilla_ocupada;
    }

    /**
     * Comprueba si existe un ganador. Devuelve un valor del estado del tablero
     * existente (tipo de ganador, no ganador ó empate), PRIORIZAMOS AL JUGADOR
     *
     * @return Devuelve : * 0 si NO hay ganador * 1 si JUGADOR gana
     * (PRIORIDAD SI SE PRODUCE EMPATE) * 2 si MAQUINA gana
     */
    public int comprobarGanador() {
        // Determina si ha encontrado un valor de estado del tablero
        boolean encontrado = false;
        int valor = 0; // Valor del estado resultante
        int i; // Índice para recorrer el tablero

        // COMPROBACIÓN: LÍNEA HORIZONTAL (FILA)
        i = 0;
        while (i <= 6 && !encontrado) {
            if (tablero[i] == JUGADOR && tablero[i + 1] == JUGADOR
                    && tablero[i + 2] == JUGADOR) {
                valor = 1;
                encontrado = true;
            } else if (tablero[i] == MAQUINA && tablero[i + 1] == MAQUINA
                    && tablero[i + 2] == MAQUINA) {
                valor = 2;
                encontrado = true;
            }

            i += 3;
        }

        // COMPROBACIÓN: LÍNEA VERTICAL (COLUMNA)
        i = 0;
        while (i <= 2 && !encontrado) {
            if (tablero[i] == JUGADOR && tablero[i + 3] == JUGADOR
                    && tablero[i + 6] == JUGADOR) {
                valor = 1;
                encontrado = true;
            } else if (tablero[i] == MAQUINA && tablero[i + 3] == MAQUINA
                    && tablero[i + 6] == MAQUINA) {
                valor = 2;
                encontrado = true;
            }

            i++;
        }

        // COMPROBACIÓN: LÍNEA DIAGONAL (DIAGONAL)
        if ((tablero[0] == JUGADOR && tablero[4] == JUGADOR && tablero[8] == JUGADOR)
                || (tablero[2] == JUGADOR && tablero[4] == JUGADOR && tablero[6] == JUGADOR)) {
            valor = 1;
            encontrado = true;
        } else if ((tablero[0] == MAQUINA && tablero[4] == MAQUINA && tablero[8] == MAQUINA)
                || (tablero[2] == MAQUINA && tablero[4] == MAQUINA && tablero[6] == MAQUINA)) {
            valor = 2;
            encontrado = true;
        }

        // Comprueba NO ganador
        i = 0;
        while (i < DIM_TABLERO && !encontrado) {
            if (tablero[i] == BLANCO) {
                valor = 0;
                encontrado = true;
            }

            i++;
        }

        // Prioridad al jugador (si hay empate)
        if (!encontrado)
            valor = 1;

        return valor;
    }

    //La maquina intenta hacer un movimiento "inteligente" (Codigo extraído de actividad de internet e implementado con algunos cambios)
    public int getMovimientoMaquinaInteligente() {

        //Empieza comprobando si la casilla central en este caso la 4, esta ocupada, en ese caso buscará ocupar otra casilla cualquiera
        int i = 0;
        int casilla;
        if (tablero[casilla = 4] == BLANCO) {
            return 4;
        } else {
            if (tablero[casilla] == MAQUINA) {
                //Empieza comprobando los horizontales y verticales en la matriz (1,3,5,7)
                if (tablero[casilla - 3] == BLANCO) {
                    return 1;
                } else if (tablero[casilla - 1] == BLANCO) {
                    return 3;
                } else if (tablero[casilla + 1] == BLANCO) {
                    return 5;
                } else if (tablero[casilla + 3] == BLANCO) {
                    return 7;
                }
            } else {
                //Comprueba los diagonales (0,2,6,8)
                if (tablero[casilla - 4] == BLANCO) {
                    return 0;
                } else if (tablero[casilla - 2] == BLANCO) {
                    return 2;
                } else if (tablero[casilla + 2] == BLANCO) {
                    return 6;
                } else if (tablero[casilla + 4] == BLANCO) {
                    return 8;
                } else {
                    while (tablero[i] == BLANCO) {
                        i++;
                    }
                    return i;
                }
            }
        }
        return i;
    }

    /**
     * Devuelve una casilla para que mueva ficha la MAQUINA.
     *
     * @return El mejor movimiento que puede realizar MAQUINA.
     */
    public int getMovimientoMaquina() {
        // NIVEL DE DIFICULTAD 1: MOVIMIENTO ALEATORIO
        //return getMovimientoMaquinaAleatorio();
        // NIVEL DE DIFICULTAD INTELIGENTE
        return getMovimientoMaquinaInteligente();

    }

    /*private int getMovimientoMaquinaAleatorio() {
        // Generación de una casilla aleatoria
        int casilla;

        do {
            casilla = aleatorio.nextInt(9);
        } while (tablero[casilla] == JUGADOR || tablero[casilla] == MAQUINA);

        return casilla;
    }*/



    @Override
    public String toString() {
        return tablero[0] + "|" + tablero[1] + "|" + tablero[2] + "\n"
                + tablero[3] + "|" + tablero[4] + "|" + tablero[5] + "\n"
                + tablero[6] + "|" + tablero[7] + "|" + tablero[8];
    }

    public static void main(String[] params) {
        JuegoTresEnRaya juego = new JuegoTresEnRaya();

        // SIMULACIÓN DEL JUGADOR: Hacer diagonal de X (0-4-8)

        // Coloca ficha: JUGADOR
        if (!juego.moverFicha('X', 0))
            System.out.println("CASILLA 0 OCUPADA");

        // Coloca ficha: MÁQUINA
        juego.moverFicha('O', juego.getMovimientoMaquina());
        System.out.println("ESTADO: " + juego.comprobarGanador());
        System.out.println("NUEVO TABLERO: \n" + juego + "\n");

        // Coloca ficha: JUGADOR
        if (!juego.moverFicha('X', 4))
            System.out.println("CASILLA 4 OCUPADA");

        // Coloca ficha: MÁQUINA
        juego.moverFicha('O', juego.getMovimientoMaquina());
        System.out.println("ESTADO: " + juego.comprobarGanador());
        System.out.println("NUEVO TABLERO: \n" + juego + "\n");

        // Coloca ficha: JUGADOR
        if (!juego.moverFicha('X', 8))
            System.out.println("CASILLA 8 OCUPADA");

        // Coloca ficha: MÁQUINA
        juego.moverFicha('O', juego.getMovimientoMaquina());
        System.out.println("ESTADO: " + juego.comprobarGanador());
        System.out.println("NUEVO TABLERO: \n" + juego + "\n");
    }
}