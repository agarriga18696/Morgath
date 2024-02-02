package misiones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Misiones {

	private List<Mision> misiones;

	public Misiones() {

		this.misiones = new ArrayList<>();
		
		// Iniciar misiones.
		//misionPrincipal();
		obtenerMisionesArchivo("src/misiones/misiones.txt");

	}

	// Añadir misión al registro de misiones.
	private void agregarMision(Mision mision) {
		misiones.add(mision);
	}
	
	// Acceder a una misión en concreto.
	public Mision buscarMision(String nombre) {
		Mision mision = null;
		
		for(Mision m : misiones) {
			if(nombre != null && m.getNombre().equalsIgnoreCase(nombre.trim())) {
				mision = m;
				break;
			}
		}
		
		return mision;
	}
	
	// Ejecutar las misiones.
	public Mision ejecutarMisiones() {
		
		for(Mision m : misiones) {
			
			if(!m.isActivada()) {
				int i = 0;
				
				Mision misionActual = misiones.get(i);
				misiones.get(i).setActivada(true);
				
				if(misionActual.isCompletada()) {
					misionActual.setActivada(false);
					i++;
				}
					
			}
			
			if(m.isActivada() && !m.isCompletada()) {
				return m;
			}
		}
		
		return null;
		
	}

	// MISIONES
	private void obtenerMisionesArchivo(String nombreArchivo) {
		
		try(BufferedReader bReader = new BufferedReader(new FileReader(nombreArchivo))){
			String linea;
			Mision misionActual = null;
			
			while((linea = bReader.readLine()) != null) {
				if(linea.startsWith("#")) {
					// Nueva misión.
					if(misionActual != null) {
						agregarMision(misionActual);
					}
					
					// Inicializar misión.
					misionActual = new Mision(null, null, null, 0);
					
					// Asignar los valores desde el archivo.
					misionActual.setNombre(linea.substring(2).trim());
					
				} else if(linea.startsWith("Objetivo:")) {
					misionActual.setObjetivo(linea.substring(9).trim());
					
				} else if(linea.startsWith("Mensaje:")) {
					StringBuilder mensaje = new StringBuilder(linea.substring(8).trim());
					
					while((linea = bReader.readLine()) != null && !linea.startsWith("#") && !linea.startsWith("Recompensa:")) {
						mensaje.append("\n").append(linea.trim());
					}
					
					misionActual.setMensaje(mensaje.toString() + "\n");
					
				} else if(linea.startsWith("Recompensa:")) {
					misionActual.setRecompensa(Integer.parseInt(linea.substring(11).trim()));
				}
			}
			
			// Añadir la misión y activarla.
			if(misionActual != null) {
				agregarMision(misionActual);
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
