package misiones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Misiones {

	private List<Mision> misiones;
	private Mision misionActual = null;

	public Misiones() {
		this.misiones = new ArrayList<>();

		// Iniciar misiones.
		obtenerMisionesArchivo("src/misiones/misiones.txt");
	}

	// Método para agregar una misión a la lista.
	private void agregarMision(Mision mision) {
		misiones.add(mision);
	}

	// Método para ejecutar las misiones.
	public Mision ejecutarMisiones() {
	    for (Mision m : misiones) {
	        if (!m.isActivada() && !m.isCompletada()) {
	            m.setActivada(true);
	            return m;
	        }
	    }
	    return null;
	}

	// Leer mensaje del archivo de misiones
	private String leerMensaje(BufferedReader bReader) throws IOException {
		StringBuilder mensaje = new StringBuilder();
		String linea = bReader.readLine();

		// Leer las líneas de texto hasta encontrar '#' o 'Recompensa:'.
		while (linea != null && !linea.trim().startsWith("#") && !linea.trim().startsWith("-Recompensa:")) {
			mensaje.append(linea.trim()).append("\n");
			linea = bReader.readLine(); // Leer la siguiente línea dentro del bucle.
		}

		// Obtener el valor de la recompensa si la línea comienza con 'Recompensa:'.
		if (linea != null && linea.trim().startsWith("-Recompensa:")) {
			String recompensaString = linea.substring(12).trim();
			int recompensa = Integer.parseInt(recompensaString);
			misionActual.setRecompensa(recompensa);

		}

		return mensaje.toString();
	}

	// Método para obtener las misiones del archivo de misiones.
	private void obtenerMisionesArchivo(String nombreArchivo) {
		try (BufferedReader bReader = new BufferedReader(new FileReader(nombreArchivo))) {
			String linea;

			while ((linea = bReader.readLine()) != null) {
				if (linea.startsWith("#")) {
					// Nueva misión.
					if (misionActual != null) {
						agregarMision(misionActual);
					}

					// Inicializar misión.
					misionActual = new Mision(null, null, null, 0);

					// Asignar los valores desde el archivo.
					misionActual.setNombre(linea.substring(2).trim());

				} else if (linea.startsWith("-Objetivo:")) {
					misionActual.setObjetivo(linea.substring(10).trim());

				} else if (linea.startsWith("-Mensaje:")) {
					misionActual.setMensaje(leerMensaje(bReader));

				}
			}

			// Añadir la misión y activarla.
			if (misionActual != null) {
				agregarMision(misionActual);
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("No se ha podido obtener la misión del archivo.");
		}
	}

}
