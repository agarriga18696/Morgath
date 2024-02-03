package juego;

import java.util.ArrayList;
import java.util.List;

import localizaciones.Habitacion;
import misiones.Mision;
import objetos.Objeto;

public class Jugador {

	// Atributos.
	private int puntos;
	private Habitacion ubicacion;
	private List<Objeto> inventario;
	private List<Mision> misionesCompletadas;
	
	// Constructor.
	public Jugador(Habitacion ubicacion) {
		
		this.puntos = 0;
		this.ubicacion = ubicacion;	
		this.inventario = new ArrayList<>();
		this.misionesCompletadas = new ArrayList<>();
		
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
	
	public List<Mision> getMisionesCompletadas(){
		return misionesCompletadas;
	}
    
	// Método para agregar objetos al inventario.
    public void agregarObjetoAlInventario(Objeto objeto) {
        inventario.add(objeto);
    }
	
}
