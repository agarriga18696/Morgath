package personajes;

import objetos.Arma;
import objetos.Armadura;
import objetos.Ingrediente;
import objetos.Inventario;
import objetos.Miscelaneo;
import objetos.Objeto;
import objetos.Pocion;
import personajes.Generos.Genero;
import personajes.Nombres.NombresHombre;
import personajes.Nombres.NombresMujer;
import personajes.Oficios.OficiosGenericos;
import utilidades.Aleatorio;

public class PNJ {

	// Atributos.
	private String nombre;
	private String raza;
	private String oficio;
	private String genero;
	private Inventario inventario;
	private int oro;
	private boolean tieneMision;
	private boolean esInmortal;

	// Constructor.
	public PNJ(String nombre, String raza, String oficio, String genero, int oro, boolean tieneMision, boolean esInmortal) {

		this.nombre = nombre;
		this.raza = raza;
		this.oficio = oficio;
		this.genero = genero;
		this.inventario = new Inventario();
		this.oro = oro;
		this.tieneMision = tieneMision;
		this.esInmortal = esInmortal;
	}

	// Getters i Setters.
	public String getNombre() {
		return nombre;
	}
	
	public String getRaza() {
		return raza;
	}

	public String getOficio() {
		return oficio;
	}

	public String getGenero() {
		return genero;
	}

	// Crear un PNJ personalizado.
	public static PNJ crearPNJ(String nombre, String raza, String oficio, String genero, String inventario[], int oro, boolean tieneMision, boolean esInmortal) {

		return new PNJ(nombre, raza, oficio, genero, oro, tieneMision, esInmortal);
	}

	// Generar inventario aleatorio.
	private Inventario generarInventarioAleatorio() {
		Inventario inventario = new Inventario();

		// Generar un número aleatorio de objetos a agregar al inventario.
		int cantidadObjetos = Aleatorio.Int(1, 5);

		// Agregar objetos aleatorios al inventario
		for (int i = 0; i < cantidadObjetos; i++) {
			Objeto objetoAleatorio = generarObjetoAleatorio();
			inventario.agregarObjeto(objetoAleatorio);
		}

		return inventario;
	}
	
	private Objeto generarObjetoAleatorio() {
		// TODO Auto-generated method stub
		return null;
	}

	// Generar arma aleatoria.
	private Arma generarArmaAleatoria() {
		
		Arma.Tipo tipo = Arma.Tipo.values()[Aleatorio.Int(0, Arma.Tipo.values().length - 1)];
		int ataque = Aleatorio.Int(0, 30);
		
		return new Arma(genero, genero, genero, ataque, ataque, esInmortal, esInmortal, genero, ataque, genero, ataque, esInmortal, genero);
		
	}

	// Generar un PNJ automáticamente.
	public static PNJ generarPNJGenerico(boolean esInmortal) {
		Razas.Raza raza = Razas.Raza.values()[Aleatorio.Int(0, Razas.Raza.values().length - 1)];
		Genero genero = Genero.values()[Aleatorio.Int(0, Genero.values().length - 1)];

		String nombre = null;

		if(genero == Genero.HOMBRE) {
			nombre = NombresHombre.values()[Aleatorio.Int(0, NombresHombre.values().length - 1)].toString();
		} else if(genero == Genero.MUJER) {
			nombre = NombresMujer.values()[Aleatorio.Int(0, NombresMujer.values().length - 1)].toString();
		}

		OficiosGenericos oficio = OficiosGenericos.values()[Aleatorio.Int(0, OficiosGenericos.values().length - 1)];

		// Inventario aleatorio.

		int oro = Aleatorio.Int(0, 100);

		boolean tieneMision = Aleatorio.Boolean();

		return new PNJ(nombre, raza.toString(), oficio.toString(), genero.toString(), oro, tieneMision, esInmortal);
	}

}
