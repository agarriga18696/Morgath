package personajes;

import java.util.ArrayList;
import java.util.List;

import localizaciones.Habitacion;
import objetos.Objeto;

public abstract class Personaje {

	protected String nombre;
	protected List<Objeto> inventario;
	protected Habitacion ubicacion;
	protected int vidas;
	protected boolean muerto;
	protected boolean inmortal;
	
	// Constructor
	public Personaje(String nombre, Habitacion ubicacion, int vidas) {
		this.nombre = nombre;
		this.inventario = new ArrayList<>();
		this.ubicacion = ubicacion;
		this.vidas = vidas;
		this.muerto = false;
		this.inmortal = false;
	}
	
	// Getters.
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Objeto> getInventario() {
        return inventario;
    }
	
	public Habitacion getUbicacion() {
		return ubicacion;
	}
	
	public void setUbicacion(Habitacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public boolean isInmortal() {
		return inmortal;
	}

	public void setInmortal(boolean inmortal) {
		this.inmortal = inmortal;
	}

	public boolean isMuerto() {
		return muerto;
	}

	public void setMuerto(boolean muerto) {
		this.muerto = muerto;
	}

	// Método para agregar objetos al inventario.
    public void agregarObjetoAlInventario(Objeto objeto) {
        inventario.add(objeto);
    }
	
}
