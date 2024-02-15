package personajes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utilidades.Aleatorio;

public class NombresPersonajes {
	
	// Nombres masculinos.
    private static final String[] nombresMasculinos = {
            "Eydran", "Alistair", "Galen", "Cedric", "Tristan", "Eldric", "Rowan",
            "Lucian", "Aldric", "Einar", "Torin", "Galahad"
    };
    
    // Nombres femeninos.
    private static final String[] nombresFemeninos = {
            "Lyra", "Isolde", "Elara", "Seraphina", "Morgana", "Adrianne", "Freya",
            "Gwen", "Elowen", "Ysabel", "Astrid", "Amara"
    };

    private static final List<String> listaNombresMasculinos = new ArrayList<>(Arrays.asList(nombresMasculinos));
    private static final List<String> listaNombresFemeninos = new ArrayList<>(Arrays.asList(nombresFemeninos));
    private static final List<String> listaTodosLosNombres = new ArrayList<>();
    
    static {
        listaTodosLosNombres.addAll(listaNombresMasculinos);
        listaTodosLosNombres.addAll(listaNombresFemeninos);
    }

    public static String getNombreMasculino() {
        return listaNombresMasculinos.get(Aleatorio.Int(0, listaNombresMasculinos.size() - 1));
    }

    public static String getNombreFemenino() {
        return listaNombresFemeninos.get(Aleatorio.Int(0, listaNombresFemeninos.size() - 1));
    }

    public static String getNombreAleatorio() {
        return listaTodosLosNombres.get(Aleatorio.Int(0, listaTodosLosNombres.size() - 1));
    }
}
