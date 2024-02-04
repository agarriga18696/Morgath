package localizaciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import localizaciones.Mapa.Direccion;
import objetos.Objeto;
import personajes.Enemigo;
import personajes.NPC;

public class Habitacion {

	// Atributos.
	private int id;
	private String nombre;
	private String descripcion;
	private Map<Direccion, Habitacion> salidas;
	private List<Objeto> objetos;
	
	private List<NPC> npcs;
	private List<Enemigo> enemigos;

	// Constructor.
	public Habitacion(int id, String nombre, String descripcion) {
		this.id = id;
		this.nombre = nombre;	
		this.descripcion = descripcion;
		this.salidas = new HashMap<>();
		this.objetos = new ArrayList<>();
		this.npcs = new ArrayList<>();
		this.enemigos = new ArrayList<>();
	}

	// Getters i setters.
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre.toUpperCase();
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<Objeto> getObjetos() {
        return objetos;
    }
	
	public Map<Direccion, Habitacion> getSalidas(){
		return salidas;
	}
	
	public void setSalidas(Map<Direccion, Habitacion> salidas) {
        this.salidas = salidas;
    }
	
	// Método para agregar un objeto a la habitación.
	public void agregarObjeto(Objeto objeto) {
		objetos.add(objeto);
	}

}
