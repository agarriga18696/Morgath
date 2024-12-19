package personajes;

public interface Conversar {
	
	String obtenerConversacion();
	
	// Saludos.
	String saludoNormal();
	String saludoGrosero();
	String saludoAmigable();

	// Despedidas.
	String despedidaNormal();
	String despedidaGrosera();
	String despedidaAmigable();
    
}
