package juego;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Acción ejecutada al presionar un botón para colocar una ficha en 
 * un casillero del tablero.
 * 
 */
public class ColocarFicha implements EventHandler<ActionEvent> {

	private Tablero tablero;
	private Reversi juego;
	private int fila;
	private int columna;
	
	/**
	 * post: asocia la acción de colocar una ficha en el juego 'reversi' en la
	 * 		 fila 'filaSeccionada' y columna 'columnaSeleccionada' 
	 * 		 del tablero 'tableroReversi'.
	 * 
	 * @param tableroReversi
	 * @param reversi
	 * @param filaSeleccionada
	 * @param columnaSeleccionada
	 */
	public ColocarFicha(Tablero tableroReversi, Reversi reversi, 
						int filaSeleccionada, int columnaSeleccionada) {

		tablero = tableroReversi;
		juego = reversi; 
		fila = filaSeleccionada;
		columna = columnaSeleccionada;
	}
	
	@Override
	public void handle(ActionEvent evento) {

		juego.colocarFicha(fila, columna);
		
		tablero.dibujar();
		
		if (juego.termino()) {
			
			tablero.mostrarResultado();
		}
	}
}
