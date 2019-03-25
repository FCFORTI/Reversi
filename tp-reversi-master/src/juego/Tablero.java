package juego;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Representación gráfica del Tablero del Juego Reversi.
 * 
 */
public class Tablero {
	
	private static final int TAMANIO_FICHA = 80;
	private static final double DIMENSION_BOTON = 20;
	
	private Reversi juego;
	private BorderPane panel;
	private GridPane grilla;
	private Stage escenario;
	
	private Label cantidadFichasNegras;
	private Label cantidadFichasBlancas;
	private Label jugadorActual;
	
	/**
	 * post: asocia el Tablero a 'nuevoJuego' y lo inicializa a partir de su estado. 
	 * 
	 * @param nuevoJuego
	 */
	public Tablero(Reversi nuevoJuego) {
		
		juego = nuevoJuego;
		escenario = new Stage();
		panel = new BorderPane();
		grilla = new GridPane();
	}
	
	/**
	 * post: muestra el Tablero en pantalla.
	 */
	public void mostrar() {
		
		dibujarFondo();
		dibujarMarcador();
		
		Scene escena = new Scene(panel, panel.getPrefWidth(), panel.getPrefHeight());

		escenario.setScene(escena);
		escenario.setResizable(false);
		escenario.setTitle(Aplicacion.TITULO);
		
		dibujar();

		escenario.show();
	}
	
	/**
	 * post: agrega el fondo del Tablero.
	 */
	private void dibujarFondo() {
		
		grilla.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
		grilla.setBorder(crearBorde());
		grilla.setPadding(new Insets(10));
		grilla.setCenterShape(true);
		
		panel.setCenter(grilla);
	}

	/**
	 * post: agrega el marcador al Tablero.
	 */
	private void dibujarMarcador() {

		Text tituloMarcador = new Text("Marcador");
		tituloMarcador.setFont(new Font(16));

		Text tituloJuega = new Text("Juega");
		tituloJuega.setFont(new Font(16));
		
		cantidadFichasNegras = new Label();
		cantidadFichasBlancas = new Label();
		jugadorActual = new Label();

		GridPane marcador = new GridPane();
		marcador.setAlignment(Pos.TOP_CENTER);
		marcador.setHgap(20);
		marcador.setVgap(20);
		marcador.setPadding(new Insets(20));
		marcador.add(tituloMarcador, 0, 0, 2, 1);
		marcador.add(new Label("Negras  :"), 0, 1);
		marcador.add(cantidadFichasNegras, 1, 1);
		marcador.add(new Label("Blancas :"), 0, 2);
		marcador.add(cantidadFichasBlancas, 1, 2);
		marcador.add(tituloJuega, 0, 3, 2, 1);
		marcador.add(jugadorActual, 0, 4, 2, 1);

		panel.setPadding(new Insets(5));
		panel.setRight(marcador);
	}
	
	/**
	 * post: actualiza el Tablero a partir del estado del juego asociado.
	 */
	public void dibujar() {
		
		grilla.getChildren().clear();
		
		for (int fila = 1; fila <= juego.contarFilas(); fila++) {

			for (int columna = 1; columna <= juego.contarColumnas(); columna++) {

				dibujarFicha(fila, columna);
				dibujarBoton(fila, columna);
			}
		}
		
		dibujarEstado();
	}

	/**
	 * post: actualiza el estado del juego del en Tablero.
	 */
	private void dibujarEstado() {

		cantidadFichasNegras.setText(String.valueOf(juego.contarFichasNegras()));
		cantidadFichasBlancas.setText(String.valueOf(juego.contarFichasBlancas()));
		jugadorActual.setText(juego.obtenerJugadorActual());
	}

	/**
	 * post: dibuja la ficha en la posición indicada por fila y columna.
	 */
	private void dibujarFicha(int fila, int columna) {

		Casillero casillero = juego.obtenerCasillero(fila, columna);
		
		Circle dibujoCasillero = new Circle(TAMANIO_FICHA / 2);
		dibujoCasillero.setFill(crearPintura(casillero));
		dibujoCasillero.setStroke(new Color(0.5, 0.5, 0.5, 0.8));
		dibujoCasillero.setScaleX(0.95);
		dibujoCasillero.setScaleY(0.95);
		
		dibujar(dibujoCasillero, fila, columna);
	}

	/**
	 * post: dibuja el boton en el casillero indicado por fila y columna,
	 *       si es que se puede colocar una ficha.
	 * 
	 * @param fila
	 * @param columna
	 */
	private void dibujarBoton(int fila, int columna) {

		if (juego.puedeColocarFicha(fila, columna)) {
			
			Button botonColocarFicha = new Button();
			botonColocarFicha.setMinSize(DIMENSION_BOTON, DIMENSION_BOTON);
			botonColocarFicha.setMaxSize(DIMENSION_BOTON, DIMENSION_BOTON);
			botonColocarFicha.setOnAction(new ColocarFicha(this, juego, fila, columna));

			dibujar(botonColocarFicha, fila, columna);
		}
	}

	private void dibujar(Node elemento, int fila, int columna) {
		
		GridPane.setHalignment(elemento, HPos.CENTER);
		GridPane.setValignment(elemento, VPos.CENTER);
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		grilla.add(elemento, columna - 1, fila - 1);
	}

	
	/**
	 * post: determina la pintura a utilizar para 'casillero'.

	 * @param casillero
	 * @return pintura a utilizar para identificar el Casillero.
	 */
	private Paint crearPintura(Casillero casillero) {

		Paint pintura;

		switch (casillero) {
		
			case BLANCAS:
				pintura = Color.WHITE;
				break;
				
			case NEGRAS:
				pintura = Color.BLACK;
				break;
				
			default:
				pintura = Color.TRANSPARENT;
		}

		return pintura;
	}

	private Border crearBorde() {
		
		BorderStroke trazo = new BorderStroke(Color.DARKGRAY, 
												BorderStrokeStyle.SOLID, 
												new CornerRadii(0), 
												new BorderWidths(1));
		
		return new Border(trazo);
	}
	
	/**
	 * pre : el juego asociado terminó.
	 * post: muestra un mensaje indicando el resultado del juego.
	 */
	public void mostrarResultado() {

		Stage dialogo = new Stage();
		
		BorderPane panelGanador = new BorderPane();
		panelGanador.setPadding(new Insets(10.0));
		
		Text textoResultado = new Text(crearMensajeResultado());
		textoResultado.setFont(new Font(40.0));
		panelGanador.setCenter(textoResultado);
		
		Scene escenaGanador = new Scene(panelGanador);
		
		dialogo.setTitle("Resultado");
		dialogo.setScene(escenaGanador);
		dialogo.initOwner(escenario);
		dialogo.initModality(Modality.WINDOW_MODAL);
		dialogo.setResizable(false);
		
		dialogo.showAndWait();
	}
	
	private String crearMensajeResultado() {
		
		String mensajeResultado;
		if (juego.hayGanador()) {
			
			mensajeResultado = "Ganó el jugador " + juego.obtenerGanador();
			
		} else {
			
			mensajeResultado = "Empataron";
		}
		
		return mensajeResultado;
	}
}
