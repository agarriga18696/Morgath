package objetos;

public class Objeto_Llave extends Objeto {
	
	private String idObjetoVinculado;

	public Objeto_Llave(String nombre, String descripcion, String idObjetoVinculado) {
		super(nombre, descripcion);
		
		this.idObjetoVinculado = idObjetoVinculado;
	}

	public String getIdObjetoVinculado() {
		return idObjetoVinculado;
	}

	public void setIdObjetoVinculado(String idObjetoVinculado) {
		this.idObjetoVinculado = idObjetoVinculado;
	}
	
}
