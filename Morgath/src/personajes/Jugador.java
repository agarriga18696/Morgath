package personajes;

import java.util.ArrayList;
import java.util.List;

import localizaciones.Habitacion;
import misiones.Mision;

public class Jugador extends PersonajeBase {

	// Atributos.
	private final String NOMBRE_POR_DEFECTO = "Jugador"; 
	Habitacion ubicacion;
	private int puntos;
	public List<Mision> diario; // lista de misiones.
	private int maxObjetosInventario;
	private boolean dePie;
	
	// Constructor.
	public Jugador(String nombre, Habitacion ubicacion, int vidas) {
		super(nombre, vidas);
		this.nombre = "Jugador";
		this.setVidas(5);
		this.ubicacion = ubicacion;
		this.puntos = 0;
		this.inventario = new ArrayList<>();
		this.diario = new ArrayList<>();
		this.maxObjetosInventario = 6;
		this.dePie = false;
	}

	// Getters i setters.
	public int getPuntos() {
		return puntos;
	}

	public String getNombrePorDefecto() {
		return NOMBRE_POR_DEFECTO;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	
	public List<Mision> getDiario(){
		return diario;
	}
	
	public void setDiario(Mision mision) {
		diario.add(mision);
	}
	
	public Habitacion getUbicacion() {
		return ubicacion;
	}
	
	public void setUbicacion(Habitacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public int getMaxObjetosInventario() {
		return maxObjetosInventario;
	}

	public void setMaxObjetosInventario(int maxObjetosInventario) {
		this.maxObjetosInventario = maxObjetosInventario;
	}

	public boolean isDePie() {
		return dePie;
	}

	public void setDePie(boolean dePie) {
		this.dePie = dePie;
	}
	
    
}
