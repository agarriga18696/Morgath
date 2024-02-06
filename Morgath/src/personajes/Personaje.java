package personajes;

import java.util.ArrayList;
import java.util.List;

import objetos.Objeto;

public abstract class Personaje {

	protected String nombre;
	protected List<Objeto> inventario;
	protected int vidas;
	protected boolean muerto;
	protected boolean inmortal;
	
	// Constructor
	public Personaje(String nombre, int vidas) {
		this.nombre = nombre;
		this.inventario = new ArrayList<>();
		this.vidas = vidas;
		this.muerto = false;
		this.inmortal = false;
	}
	
	// Getters.
	public String getNombre() {
		return nombre.toUpperCase();
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Objeto> getInventario() {
        return inventario;
    }
	
	public void setInventario(List<Objeto> inventario) {
		this.inventario = inventario;
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
