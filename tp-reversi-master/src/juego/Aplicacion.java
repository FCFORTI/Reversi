package juego;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Aplicación del juego Reversi.
 * 
 * Punto de entrada del programa.
 * 
 */
public class Aplicacion extends Application {

	public static final String TITULO = "Reversi";
	
	private GridPane grilla;
	
	private TextField campoNombreFichasNegras;
	private TextField campoNombreFichasBlancas;

	private TextField campoDimension;

	private Button botonIniciar;

	@Override
	public void start(Stage escenarioPrincipal) {

		crearGrilla();

		Scene escena = new Scene(grilla, 400, 300);
		escenarioPrincipal.setScene(escena);
		escenarioPrincipal.setTitle(TITULO);
		escenarioPrincipal.show();
	}

	private void crearGrilla() {

		grilla = new GridPane();
		grilla.setAlignment(Pos.CENTER);
		grilla.setHgap(20);
		grilla.setVgap(20);
		
		Text textoTitulo = new Text(TITULO);
		textoTitulo.setFont(new Font(16));
		
		crearControles();

		grilla.add(textoTitulo, 0, 0, 2, 1);
		grilla.add(new Label("Fichas Negras"), 0, 1);
		grilla.add(campoNombreFichasNegras, 1, 1);
		grilla.add(new Label("Fichas Blancas"), 0, 2);
		grilla.add(campoNombreFichasBlancas, 1, 2);
		grilla.add(new Label("Dimensión"), 0, 3);
		grilla.add(campoDimension, 1, 3);
		grilla.add(botonIniciar, 0, 5, 2, 1);
		
		GridPane.setHalignment(botonIniciar, HPos.CENTER);
		GridPane.setHalignment(textoTitulo, HPos.CENTER);
	}

	private void crearControles() {

		campoNombreFichasNegras = new TextField("negras");
		campoNombreFichasBlancas = new TextField("blancas");
		
		campoDimension = new TextField("8");
		
		botonIniciar = new Button("Iniciar");
		botonIniciar.setOnAction(new IniciarJuego(this));
	}
	
	/**
	 * post: crea un juego Reversi, lo asocia a una Tablero 
	 * 		 y comienza su ejecución.
	 * 
	 */
	public void iniciar() {
		
		String nombreFichasNegras = campoNombreFichasNegras.getText();
		String nombreFichasBlancas = campoNombreFichasBlancas.getText();
		int dimension = Integer.parseInt(campoDimension.getText());
		
		Reversi juego = new Reversi(dimension, nombreFichasNegras, nombreFichasBlancas);
		
		Tablero tablero = new Tablero(juego);
		tablero.mostrar();
	}
	
	public static void main(String[] args) {
		
		Thread.setDefaultUncaughtExceptionHandler(new MostrarError());
		
		launch(args);
	}
}
