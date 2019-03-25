package juego;

/**
 * Juego Reversi
 * 
 * Reglas:
 * 
 * 	https://es.wikipedia.org/wiki/Reversi
 *  https://es.wikihow.com/jugar-a-Othello
 * 
 */
public class Reversi {
	
	
	private Casillero tablero[][];
	private String jugadorFichasNegras = null, jugadorFichasBlancas = null, turno = null;
	private int dimensionTablero;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * pre : 'dimension' es un número par, mayor o igual a 4.
	 * post: empieza el juego entre el jugador que tiene fichas negras, identificado como 
	 * 		 'fichasNegras' y el jugador que tiene fichas blancas, identificado como
	 * 		 'fichasBlancas'. 
	 * 		 El tablero tiene 4 fichas: 2 negras y 2 blancas. Estas fichas están 
	 * 		 intercaladas en el centro del tablero.
	 * 
	 * @param dimensionTablero : cantidad de filas y columnas que tiene el tablero.
	 * @param fichasNegras : nombre del jugador con fichas negras.
	 * @param fichasBlancas : nombre del jugador con fichas blancas.
	 */
	public Reversi(int dimensionTablero, String fichasNegras, String fichasBlancas) {
		
		if (dimensionTablero >= 4 && (dimensionTablero % 2) == 0 ){
			this.tablero = new Casillero [dimensionTablero][dimensionTablero];
			this.jugadorFichasNegras = fichasNegras;
			this.jugadorFichasBlancas = fichasBlancas;
			this.dimensionTablero = dimensionTablero;
			this.turno = "NEGRAS";			
			for (int i = 0; i < this.tablero.length; i++){
				for (int j = 0; j < this.tablero[i].length; j++){
					this.tablero[i][j] = Casillero.LIBRE;
					this.tablero[dimensionTablero/2][dimensionTablero/2] = Casillero.NEGRAS;
					this.tablero[dimensionTablero/2][(dimensionTablero/2)-1] = Casillero.BLANCAS;
					this.tablero[(dimensionTablero/2)-1][(dimensionTablero/2)-1] = Casillero.NEGRAS;
					this.tablero[(dimensionTablero/2)-1][dimensionTablero/2] = Casillero.BLANCAS;
				}
			}			
		} else {
			throw new Error ("La dimensión debe ser par y mayor o igual a 4.");
		}

	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * post: devuelve la cantidad de filas que tiene el tablero.
	 */
	public int contarFilas() {
		
		return this.dimensionTablero;

	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * post: devuelve la cantidad de columnas que tiene el tablero.
	 */
	public int contarColumnas() {
		
		return this.dimensionTablero;
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * post: devuelve el nombre del jugador que debe colocar una ficha o null si
	 *       terminó el juego.
	 */
	public String obtenerJugadorActual() {
		
		String jugador = null;
		
		switch (this.turno) {
			case "NEGRAS": 	jugador = this.jugadorFichasNegras;
							break;
			case "BLANCAS": jugador = this.jugadorFichasBlancas;
							break;
			case "NULL" :	jugador = null;
							break;	
		}
		
		return jugador;
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	/**
	 * pre : fila está en el intervalo [1, contarFilas()],
	 * 		 columnas está en el intervalo [1, contarColumnas()].
	 * post: indica quién tiene la posesión del casillero dado 
	 * 		 por fila y columna.
	 * 
	 * @param fila
	 * @param columna
	 */
	public Casillero obtenerCasillero(int fila, int columna) {

		if (fila >= 1 && fila <= this.contarFilas() && columna >= 1 && columna <= this.contarColumnas()){
			return this.tablero[fila-1][columna-1];
		} else {
			throw new Error ("Fila y columna debe estar comprendido dentro de las dimensiones del tablero; 1 - " + this.dimensionTablero);
		}
		

	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public boolean puedeColocarFicha(int fila, int columna) {
		
		boolean flanqueaALaIzquierda = false;
		boolean flanqueaALaDerecha = false;
		boolean flanqueaParaArriba = false;
		boolean flanqueaParaAbajo = false;
		boolean flanqueaParaElNoreste = false;
		boolean flanqueaParaElSuroeste = false;
		boolean flanqueaParaElNoroeste = false;
		boolean flanqueaParaElSureste = false;
		
		Casillero miFicha = Casillero.LIBRE;
		if (this.turno == "NEGRAS"){
			miFicha = Casillero.NEGRAS;
		} else {
			if (this.turno == "BLANCAS"){
				miFicha = Casillero.BLANCAS;
			}
		}

		
		if (columna >= 3 && this.obtenerCasillero(fila, columna) == Casillero.LIBRE){					
			flanqueaALaIzquierda = this.flanqueaParaLaIzquierda(fila, columna, miFicha);		
		} 
		
		if (columna < this.contarColumnas() - 2 && this.obtenerCasillero(fila, columna) == Casillero.LIBRE){
			flanqueaALaDerecha = flanqueaParaLaDerecha(fila, columna, miFicha);
		}
		
		if (fila >= 3 && this.obtenerCasillero(fila, columna) == Casillero.LIBRE){
			flanqueaParaArriba = flanqueaParaArriba(fila, columna, miFicha);
		}
		
		if (fila < this.contarFilas() - 2 && this.obtenerCasillero(fila, columna) == Casillero.LIBRE){
			flanqueaParaAbajo = flanqueaParaAbajo(fila, columna, miFicha);
		}
		
		if (fila >= 3 && columna < this.contarColumnas() - 2 && this.obtenerCasillero(fila, columna) == Casillero.LIBRE){
			flanqueaParaElNoreste = flanqueaParaElNoreste(fila, columna, miFicha);
		}
		
		if (fila < this.contarFilas() - 2 && columna >= 3 && this.obtenerCasillero(fila, columna) == Casillero.LIBRE){
			flanqueaParaElSuroeste = flanqueaParaElSuroeste(fila, columna, miFicha);
		}
		
		if (fila >= 3 && columna >= 3 && this.obtenerCasillero(fila, columna) == Casillero.LIBRE){
			flanqueaParaElNoroeste = flanqueaParaElNoroeste(fila, columna, miFicha);
		}
		
		if (fila < this.contarFilas() - 2 && columna < this.contarColumnas() - 2 && this.obtenerCasillero(fila, columna) == Casillero.LIBRE){
			flanqueaParaElSureste = flanqueaParaElSureste(fila, columna, miFicha);
		}

		return flanqueaALaIzquierda || flanqueaALaDerecha || flanqueaParaArriba || flanqueaParaAbajo || flanqueaParaElNoreste || flanqueaParaElSuroeste || flanqueaParaElNoroeste || flanqueaParaElSureste;
		
	/*	if (!puedeColocarFicha){
			if (this.turno == "NEGRAS"){
				this.turno = "BLANCAS";
			} else {
				if (this.turno == "BLANCAS"){
					this.turno = "NEGRAS";
				}
			}
		}*/
				
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * pre : la posición indicada por (fila, columna) puede ser 
	 *		 ocupada por una ficha. 
	 *       'fila' está en el intervalo [1, contarFilas()].
	 * 		 'columna' está en el intervalor [1, contarColumnas()].
	 * 		 y aún queda un Casillero.VACIO en la columna indicada. 
	 * post: coloca una ficha en la posición indicada.

	 * @param fila
	 * @param columna
	 */
	public void colocarFicha(int fila, int columna) {
		
		if (this.puedeColocarFicha(fila, columna)){
			switch (this.turno) {
				case "NEGRAS" :		this.tablero[fila-1][columna-1] = Casillero.NEGRAS;
									this.laFichaFlanquea(fila, columna, Casillero.NEGRAS);
									this.turno = "BLANCAS";
									break;
				case "BLANCAS" : 	this.tablero[fila-1][columna-1] = Casillero.BLANCAS;
									this.laFichaFlanquea(fila, columna, Casillero.BLANCAS);
									this.turno = "NEGRAS";
									break;
			}
		}
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * post: devuelve la cantidad de fichas negras en el tablero.
	 */
	public int contarFichasNegras() {
		
		int contadorDeFichasNegras = 0;
		
		for (int i = 1; i <= this.dimensionTablero; i++) {
			for (int j = 1; j <= this.dimensionTablero; j++){
				if (this.obtenerCasillero(i, j) == Casillero.NEGRAS){
					contadorDeFichasNegras ++;
				}
			}
		}
	
		return contadorDeFichasNegras;
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * post: devuelve la cantidad de fichas blancas en el tablero.
	 */
	public int contarFichasBlancas() {
		
		int contadorDeFichasBlancas = 0;
		
		for (int i = 1; i <= this.dimensionTablero; i++) {
			for (int j = 1; j <= this.dimensionTablero; j++){
				if (this.obtenerCasillero(i, j) == Casillero.BLANCAS){
					contadorDeFichasBlancas ++;
				}
			}
		}
	
		return contadorDeFichasBlancas;
	
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * post: indica si el juego terminó porque no existen casilleros vacíos o
	 *       ninguno de los jugadores puede colocar una ficha.
	 */
	public boolean termino() {
		
		boolean termino = true;
		
		for (int i = 1; i <= this.contarFilas(); i++){
			for (int j = 1; j <= this.contarColumnas(); j++){
				if (this.puedeColocarFicha(i, j)) {
					termino = false;
				}
			/*	if (this.turno == "NEGRAS"){
					if (this.puedeColocarFicha(i, j)) {
						termino = false;
					}
				} else {
					if (this.puedeColocarFicha(i, j)) {
						termino = false;
					}
				}*/
			}
		}
		
		if (termino){
			this.turno = "NULL";
		}
		
		return termino;
		
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * post: indica si el juego terminó y tiene un ganador.
	 */
	public boolean hayGanador() {
		
		return (this.termino() && !(this.contarFichasNegras() == this.contarFichasBlancas()));
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	/**
	 * pre : el juego terminó.
	 * post: devuelve el nombre del jugador que ganó el juego.
	 */
	public String obtenerGanador() {
		
		String ganador = "Empate";
		
		if (this.termino()) {
			if (this.contarFichasNegras() > this.contarFichasBlancas()) {
				ganador = this.jugadorFichasNegras;
			}
			if (this.contarFichasBlancas() > this.contarFichasNegras()) {
				ganador = this.jugadorFichasBlancas;
			}
		}
		
		return ganador;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	private void laFichaFlanquea(int fila, int columna, Casillero miFicha){
		
		//boolean salir = false;
		int k = columna;
		
		for (int j = columna-1; j > 0 && flanqueaParaLaIzquierda(fila, j+1, miFicha); j--){
				this.invertirFicha(fila, j);
		}
		
		for (int j = columna+1; j <= this.contarColumnas() && flanqueaParaLaDerecha(fila, j-1, miFicha); j++){
				this.invertirFicha(fila, j);
		}
				
		for (int i = fila-1; i > 0 && flanqueaParaArriba(i+1, columna, miFicha); i--){
				this.invertirFicha(i, columna);
		}
		
		for (int i = fila+1; i <= this.contarFilas() && flanqueaParaAbajo(i-1, columna, miFicha); i++){
				this.invertirFicha(i, columna);
		}
		
		k = columna;
		for (int i = fila-1; i > 0 && k < this.contarColumnas(); i--){
			k++;
			if (flanqueaParaElNoreste(i+1, k-1, miFicha)){
				this.invertirFicha(i, k);		
			}
		}
		
		k = columna;
		for (int i = fila + 1; i < this.contarFilas() && k >= 3; i++){
			k--;
			if (flanqueaParaElSuroeste(i-1, k+1, miFicha)){
				this.invertirFicha(i, k);
			}
		}
		
		k = columna;
		for (int i = fila - 1; i >= 3 && k >= 3; i--){
			k--;
			if (flanqueaParaElNoroeste(i+1, k+1, miFicha)){
				this.invertirFicha(i, k);
			}
		}
		
		k = columna;
		for (int i = fila + 1; i < this.contarFilas() && k < this.contarColumnas(); i++){
			k++;
			if (flanqueaParaElSureste(i-1, k-1, miFicha)){
				this.invertirFicha(i, k);
			}
		}
		
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	


	/**
	 * 
	 * @param fila
	 * @param columna
	 * pre : Fila y Columna estan comprendidos entre 1 y la dimension.
	 *	*/
	private void invertirFicha (int fila, int columna){
		
		if (this.obtenerCasillero(fila, columna) == Casillero.NEGRAS){
			this.tablero[fila-1][columna-1] = Casillero.BLANCAS;
		} else {
			this.tablero[fila-1][columna-1] = Casillero.NEGRAS;
		}
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean flanqueaParaLaIzquierda (int fila, int columna, Casillero miFicha){
		
		Casillero fichaLateralIzquierda = this.obtenerCasillero(fila, columna-1);
		
		boolean fichaAliada = false;
	
		if (fichaLateralIzquierda != miFicha && fichaLateralIzquierda != Casillero.LIBRE){
			for (int j = columna - 2; j > 0 && !fichaAliada && !(this.obtenerCasillero(fila, j) == Casillero.LIBRE); j--){
				if (this.obtenerCasillero(fila, j) == miFicha){
					fichaAliada = true;
				}
			}
		}
		return fichaAliada;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	private boolean flanqueaParaLaDerecha (int fila, int columna, Casillero miFicha){
		
		Casillero fichaLateralDerecha = this.obtenerCasillero(fila, columna+1);

		boolean fichaAliada = false;
	
		if (fichaLateralDerecha != miFicha && fichaLateralDerecha != Casillero.LIBRE){
			for (int j = columna + 2; j <= this.contarColumnas() && !fichaAliada && !(this.obtenerCasillero(fila, j) == Casillero.LIBRE); j++){
				if (this.obtenerCasillero(fila, j) == miFicha){
					fichaAliada = true;
				}
			}
		}
		
		return fichaAliada;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean flanqueaParaArriba (int fila, int columna, Casillero miFicha){
		
		Casillero fichaSuperior = this.obtenerCasillero(fila-1, columna);
		
		boolean fichaAliada = false;
		
		if (fichaSuperior != miFicha && fichaSuperior != Casillero.LIBRE){
			for (int i = fila - 2; i > 0 && !fichaAliada&& !(this.obtenerCasillero(i, columna) == Casillero.LIBRE); i--){
				if (this.obtenerCasillero(i, columna) == miFicha){
					fichaAliada = true;
				}
			}
		}
		
		return fichaAliada;
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean flanqueaParaAbajo (int fila, int columna, Casillero miFicha){
		
		Casillero fichaInferior = this.obtenerCasillero(fila+1, columna);
		
		boolean fichaAliada = false;
		
		if (fichaInferior != miFicha && fichaInferior != Casillero.LIBRE){
			for (int i = fila + 2; i <= this.contarFilas() && !fichaAliada && !(this.obtenerCasillero(i, columna) == Casillero.LIBRE); i++){
				if (this.obtenerCasillero(i, columna) == miFicha){
					fichaAliada = true;
				}
			}
		}
		
		return fichaAliada;
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean flanqueaParaElNoreste (int fila, int columna, Casillero miFicha){
		
		Casillero fichaContinuaAlNoreste = this.obtenerCasillero(fila-1, columna+1);
		
		int i = fila - 2;
		int j = columna + 2;
		
		boolean fichaAliada = false;
	
		if (fichaContinuaAlNoreste != miFicha && fichaContinuaAlNoreste != Casillero.LIBRE){
			while (i > 0 && j <= this.contarColumnas() && !fichaAliada && this.obtenerCasillero(i, j) != Casillero.LIBRE){
				if (this.obtenerCasillero(i, j) == miFicha){
					fichaAliada = true;
				}
				i--;
				j++;
			}
		}
			
		return fichaAliada;
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean flanqueaParaElSuroeste (int fila, int columna, Casillero miFicha){
		
		Casillero fichaContinuaAlNoreste = this.obtenerCasillero(fila+1, columna-1);
		
		int i = fila + 2;
		int j = columna - 2;
		
		boolean fichaAliada = false;
		
		if (fichaContinuaAlNoreste != miFicha && fichaContinuaAlNoreste != Casillero.LIBRE){
			while (i <= this.contarFilas() && j > 0 && !fichaAliada && this.obtenerCasillero(i, j) != Casillero.LIBRE){
				if (this.obtenerCasillero(i, j) == miFicha){
					fichaAliada = true;
				}
				i++;
				j--;
			}
		}
		
		return fichaAliada;
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean flanqueaParaElNoroeste (int fila, int columna, Casillero miFicha){
		
		Casillero fichaContinuaAlNoroeste = this.obtenerCasillero(fila-1, columna-1);
		
		int i = fila - 2;
		int j = columna - 2;
		
		boolean fichaAliada = false;
		
		if (fichaContinuaAlNoroeste != miFicha && fichaContinuaAlNoroeste != Casillero.LIBRE){
			while (i > 0 && j > 0 && !fichaAliada && this.obtenerCasillero(i, j) != Casillero.LIBRE){
				if (this.obtenerCasillero(i, j) == miFicha){
					fichaAliada = true;
				}
				i--;
				j--;
			}
		}
		
		return fichaAliada;
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean flanqueaParaElSureste (int fila, int columna, Casillero  miFicha){
		
		Casillero fichaContinuaAlSureste = this.obtenerCasillero(fila+1, columna+1);
				
		int i = fila + 2;
		int j = columna + 2;
		
		boolean fichaAliada = false;
		
		if (fichaContinuaAlSureste != miFicha && fichaContinuaAlSureste != Casillero.LIBRE){
			while (i <= this.contarFilas() && j <= this.contarColumnas() && !fichaAliada && this.obtenerCasillero(i, j) != Casillero.LIBRE){
				if (this.obtenerCasillero(i, j) == miFicha){
					fichaAliada = true;
				}
				i++;
				j++;
			}
		}
		
		return fichaAliada;
		
	}
	
}