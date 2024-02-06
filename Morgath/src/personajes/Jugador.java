package personajes;

import java.util.ArrayList;
import java.util.List;

import localizaciones.Habitacion;
import misiones.Mision;

public class Jugador extends Personaje {

	// Atributos.
	Habitacion ubicacion;
	private int puntos;
	public List<Mision> diario; // lista de misiones.
	private int maxObjetosInventario;
	
	// Constructor.
	public Jugador(String nombre, Habitacion ubicacion, int vidas) {
		super(nombre, vidas);
		this.setVidas(4);
		this.ubicacion = ubicacion;
		this.puntos = 0;
		this.inventario = new ArrayList<>();
		this.diario = new ArrayList<>();
		this.maxObjetosInventario = 6;
	}

	// Getters i setters.
	public int getPuntos() {
		return puntos;
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
	
    
}
