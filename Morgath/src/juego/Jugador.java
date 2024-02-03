package juego;

import java.util.ArrayList;
import java.util.List;

import localizaciones.Habitacion;
import objetos.Objeto;

public class Jugador {

	// Atributos.
	private int puntos;
	private Habitacion ubicacion;
	private List<Objeto> inventario;
	
	// Constructor.
	public Jugador(Habitacion ubicacion) {
		
		this.inventario = new ArrayList<>();
		this.puntos = 0;
		this.ubicacion = ubicacion;	
		
	}

	// Getters i setters.
	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public Habitacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Habitacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public List<Objeto> getInventario() {
        return inventario;
    }
    
	// Método para agregar objetos al inventario.
    public void agregarObjetoAlInventario(Objeto objeto) {
        inventario.add(objeto);
    }
	
}
