package objetos;

import java.util.UUID;

import utilidades.Aleatorio;

public abstract class Objeto {

	public enum Rareza {
		// Rareza de los objetos.
		COMUN("%c[obj_comun]Común%/c", 0),
		RARO("%c[obj_raro]Raro%/c", 3),
		DISTINGUIDO("%c[obj_distinguido]Distinguido%/c", 5),
		MITICO("%c[obj_mitico]Mítico%/c", 8),
		SUPREMO("%c[obj_supremo]Supremo%/c", 12),
		UNICO("%c[obj_unico]Único%/c", 0); // Solamente existe uno, como objetos especiales, easter eggs o de misión.
		
		private final String nombre;
		private final int bonusEstadisticas;
		
		Rareza(String nombre, int bonusEstadisticas) {
			this.nombre = nombre;
			this.bonusEstadisticas = bonusEstadisticas;
		}

		public String getNombre() {
			return nombre;
		}
		
		public int getBonusEstadisticas() {
            return bonusEstadisticas;
        }
		
		// Generar rareza aleatoria.
		public static Rareza aleatoria() {
			Rareza[] rarezaes = Rareza.values();
			int i = Aleatorio.Int(0, rarezaes.length - 2); // Ignorar la rareza 'UNICO'.
			return rarezaes[i];
		}
	}
	
	private String id;
	private String icono;
	private String nombre;
	private String descripcion;
	protected Rareza rareza;
	private boolean deMision;
	protected Integer valorVenta;
	
	public Objeto(String icono, String nombre, String descripcion, Rareza rareza, boolean deMision, Integer valorVenta) {
		this.id = UUID.nameUUIDFromBytes(nombre.getBytes()).toString().replace("-", "");
		this.icono = icono;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.rareza = (rareza != null) ? rareza : Rareza.aleatoria();
		this.deMision = deMision;
		this.valorVenta = valorVenta;
		
		//System.out.println("Nombre:\t" + this.nombre + "\t\tId:\t" + this.id);
	}

	public String getId() {
		return id;
	}
	
	public String getIcono() {
		return icono;
	}

	public String getNombre() {
		return nombre.toUpperCase();
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public Rareza getRareza() {
        return rareza;
    }
    
    public boolean isDeMision() {
		return deMision;
	}

	public Integer getValorVenta() {
		return valorVenta;
	}

	@Override
	public String toString() {
		return String.format("%s %s [%s]%s: %s", getIcono(), "%c[destacado]" + getNombre() + "%/c", rareza.getNombre(), isDeMision() ? " [Misión]" : "", getDescripcion());
	}
    
    public String infoBasica() {
    	return String.format("%s %s [%s] %s", getIcono(), "%c[destacado]" + getNombre() + "%/c", rareza.getNombre(), isDeMision() ? "[Misión]" : "");
    }

}
