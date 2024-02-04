package personajes;

import java.util.ArrayList;
import java.util.List;

import localizaciones.Habitacion;
import misiones.Mision;

public class Jugador extends Personaje {

	// Atributos.
	private int puntos;
	private List<Mision> diario; // lista de misiones.
	
	// Constructor.
	public Jugador(Habitacion ubicacion) {
		super(ubicacion);
		this.ubicacion = ubicacion;
		this.puntos = 0;
		this.inventario = new ArrayList<>();
		this.diario = new ArrayList<>();
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
    
}
