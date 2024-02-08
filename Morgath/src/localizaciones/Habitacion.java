package localizaciones;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import localizaciones.Mapa.Direccion;
import objetos.Objeto;
import personajes.Enemigo;
import personajes.PNJ;

public class Habitacion {

	// Atributos.
	private double id;
	private String nombre;
	private String descripcion;
	private Tipo tipo;
	private Map<Direccion, Habitacion> salidas;
	private List<Objeto> objetos;
	private List<PNJ> pnjs;
	private List<Enemigo> enemigos;
	private boolean visitada;

	// Constructor.
	public Habitacion(double id, String nombre, String descripcion, Tipo tipo, Objeto[] objetos, PNJ[] pnjs, Enemigo[] enemigos) {
		this.id = id;
		this.nombre = nombre;	
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.salidas = new HashMap<>();
		this.objetos = new ArrayList<>(Arrays.asList(objetos));
		this.pnjs = new ArrayList<>(Arrays.asList(pnjs));
		this.enemigos = new ArrayList<>(Arrays.asList(enemigos));
		this.visitada = false;
	}
	
	public enum Tipo {
		INTERIOR, EXTERIOR, SUPERIOR, INFERIOR
	}

	// Getters i setters.
	public double getId() {
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
	
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public List<Objeto> getObjetos() {
        return objetos;
    }
	
	public List<PNJ> getPnjs() {
		return pnjs;
	}

	public List<Enemigo> getEnemigos() {
		return enemigos;
	}

	public Map<Direccion, Habitacion> getSalidas(){
		return salidas;
	}
	
	public void setSalidas(Map<Direccion, Habitacion> salidas) {
        this.salidas = salidas;
    }

	public boolean isVisitada() {
		return visitada;
	}

	public void setVisitada(boolean visitada) {
		this.visitada = visitada;
	}

}
