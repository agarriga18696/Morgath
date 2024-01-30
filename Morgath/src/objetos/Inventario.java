package objetos;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
	
	private List<Objeto> objetos;
	//private List<Arma> armas;
	//private List<Armadura> armaduras;
	//private List<Pocion> pociones;
	//private List<Ingrediente> ingredientes;
	//private List<Miscelaneo> miscelaneos;

	public Inventario() {
		
		this.objetos = new ArrayList<>();
		//this.armas = new ArrayList<>();
		//this.armaduras = new ArrayList<>();
		//this.pociones = new ArrayList<>();
		//this.ingredientes = new ArrayList<>();
		//this.miscelaneos = new ArrayList<>();

	}

	// Agregar un objeto al inventario.
	public void agregarObjeto(Objeto objeto) {
		
		objetos.add(objeto);
		//armas.add((Arma) objeto);
		//armaduras.add((Armadura) objeto);
		//pociones.add((Pocion) objeto);
		//ingredientes.add((Ingrediente) objeto);
		//miscelaneos.add((Miscelaneo) objeto);

	}

}
