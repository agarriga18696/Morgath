package personajes;

import java.util.List;

import localizaciones.Habitacion;
import objetos.Objeto;

public abstract class Personaje {

	protected List<Objeto> inventario;
	protected Habitacion ubicacion;
	
	// Constructor
	public Personaje(Habitacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	// Getters.
	public List<Objeto> getInventario() {
        return inventario;
    }
	
	public Habitacion getUbicacion() {
		return ubicacion;
	}
	
	public void setUbicacion(Habitacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	// Método para agregar objetos al inventario.
    public void agregarObjetoAlInventario(Objeto objeto) {
        inventario.add(objeto);
    }
	
}
