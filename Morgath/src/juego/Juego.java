package juego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;

import comandos.Comandos;
import configuracion.Config;
import configuracion.Fuente;
import localizaciones.Habitacion;
import localizaciones.Mapa;
import misiones.Mision;
import misiones.Misiones;
import objetos.ListaObjetos;
import personajes.Jugador;
import personajes.ListaEnemigos;
import utilidades.Simbolo;

public class Juego extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel panelPrincipal, panelCentral, panelSuperior, panelInferior;
	public JLabel labelUbicacion, labelPuntuacion, labelCursor;
	public JScrollPane barraScroll;
	public JScrollBar barraScrollPersonalizada;
	public JTextPane outputTexto;
	public JTextField inputTexto;

	public static Jugador jugador;
	public static Misiones misiones;
	public static Mapa mapa;
	public static Habitacion ubicacionInicial;
	public static ListaObjetos listaObjetos;
	public static ListaEnemigos listaEnemigos;
	private Comandos comandos;
	public Mision misionActiva;
	//public boolean empezarJuego;
	public static String ultimoComandoUsado;
	public static String nombreJugador;


	/*
	 * 
	 *  
	 *  
	 *  INICIALIZACIÓN DE JUEGO
	 *  
	 *  
	 *  
	 *  
	 */

	public Juego() {

		// Juego
		mapa = new Mapa();
		ubicacionInicial = mapa.obtenerHabitacionInicial();
		nombreJugador = "Jugador";
		jugador = new Jugador(nombreJugador, ubicacionInicial, 4);
		listaObjetos = new ListaObjetos();
		comandos = new Comandos(jugador, this, mapa);
		misiones = new Misiones();
		listaEnemigos = new ListaEnemigos();

		// Ventana
		setUndecorated(false);  // Elimina la barra de título
		setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximiza la ventana
		//setSize(Config.anchoVentana, Config.altoVentana);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("MORGATH");

		// Paneles.
		panelPrincipal = new JPanel();
		panelCentral = new JPanel();
		panelSuperior = new JPanel();
		panelInferior = new JPanel();

		// Area Texto.
		outputTexto = new JTextPane();
		inputTexto = new JTextField();

		// Barra Scroll del área de texto.
		barraScroll = new JScrollPane(outputTexto);
		barraScrollPersonalizada = new JScrollBar(JScrollBar.VERTICAL);
		barraScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		barraScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		barraScroll.setVerticalScrollBar(barraScrollPersonalizada);

		// Etiquetas del panel superior.
		labelUbicacion = new JLabel();
		labelPuntuacion = new JLabel();
		labelCursor = new JLabel(Simbolo.SISTEMA_CURSOR);

		// Action Listener para el input de texto.
		inputTexto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String comando = inputTexto.getText();
				outputTexto(Simbolo.SISTEMA_CURSOR + " " + comando, Fuente.fuenteBase);
				comandos.ejecutarComando(comando);
				ultimoComandoUsado = comando;

				// Limpiar el campo de texto después de obtener el texto.
				inputTexto.setText("");

				// Notificar al bucle que se ha ingresado un comando.
				notificarComandoIngresado();
			}
		});

		SwingUtilities.invokeLater(() -> configurarVentana());

		// Hacer autofocus en la ventana y en el inputTexto.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				inputTexto.requestFocusInWindow();
			}
		});

		setVisible(true);
	}



	/*
	 * 
	 *  
	 *  
	 *  
	 *  CONFIGURACIÓN DE LA VENTANA DEL JUEGO (INTERFAZ)
	 *  
	 *  
	 *  
	 *  
	 */

	private void configurarVentana() {
		SwingUtilities.invokeLater(() -> {
			// Establecer fuente y color.
			labelUbicacion.setFont(Fuente.fuenteBase);
			labelPuntuacion.setFont(Fuente.fuenteBase);
			labelCursor.setFont(Fuente.fuenteBase);
			outputTexto.setFont(Fuente.fuenteBase);
			inputTexto.setFont(Fuente.fuenteBase);

			// Panel principal.
			panelPrincipal.setLayout(new BorderLayout());
			panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
			panelPrincipal.add(panelCentral, BorderLayout.CENTER);
			panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

			// Panel superior.
			panelSuperior.setLayout(new GridLayout(1, 2));
			panelSuperior.add(labelUbicacion);
			panelSuperior.add(labelPuntuacion);
			labelPuntuacion.setHorizontalAlignment(SwingConstants.RIGHT);
			panelSuperior.setBorder(Config.borde);

			// Panel central.
			panelCentral.setLayout(new BorderLayout());
			panelCentral.add(barraScroll, BorderLayout.CENTER);
			barraScroll.setBorder(null);
			//outputTexto.setLineWrap(true);
			//outputTexto.setWrapStyleWord(true);
			outputTexto.setBorder(Config.borde);
			outputTexto.setHighlighter(null);
			outputTexto.setEditable(false);

			// Panel inferior.
			panelInferior.setLayout(new BorderLayout());
			panelInferior.add(labelCursor, BorderLayout.WEST);
			panelInferior.add(inputTexto, BorderLayout.CENTER);
			labelCursor.setBorder(new EmptyBorder(0, 20, 0, 0));
			inputTexto.setBorder(new EmptyBorder(20, 10, 20, 20));
			inputTexto.setHighlighter(null);

			// Añadir paneles a la juego.
			getContentPane().add(panelPrincipal);

			// Aplicar el esquema de colores.
			comandos.actualizarTemaInterfaz();
		});
	}


	/*
	 * 
	 *  
	 *  
	 *  
	 *  INICIAR PARTIDA
	 *  
	 *  
	 *  
	 *  
	 */

	public void iniciarPartida() {
		ultimoComandoUsado = "";
		ejecutarMision();

		while(!ultimoComandoUsado.equalsIgnoreCase("terminar")) {
			mostrarMensajeDeMision();

			// Verificar condiciones específicas de finalización de la misión.
			if(misionActiva != null && misionActiva.verificarCondicionesEspecificas(jugador)) {
				misionActiva.finalizarMision(jugador);
				procesarMisionCompletada();
			}

			actualizarLabelUbicacion();
			actualizarLabelPuntos();
			esperarComando();
		}
	}



	/*
	 * 
	 *  
	 *  
	 *  
	 *  MÉTODOS AUXILIARES
	 *  
	 *  
	 *  
	 *  
	 */

	/*// Método para mostrar mensajes animados en el outputTexto (JTextPane).
	public void outputTexto(String texto, Font fuente) {
		SwingUtilities.invokeLater(() -> {
			try {
				StyledDocument doc = outputTexto.getStyledDocument();
				SimpleAttributeSet estilo = new SimpleAttributeSet();
				StyleConstants.setFontFamily(estilo, fuente.getFamily());
				StyleConstants.setFontSize(estilo, fuente.getSize());
				StyleConstants.setLineSpacing(estilo, Fuente.INTERLINEADO);
				doc.setParagraphAttributes(0, doc.getLength(), estilo, false);

				// Variable para texto animado.
				StringBuilder textoMostrado = new StringBuilder();

				// Efecto texto animado.
				Texto.maquinaEscribir(texto, () ->{
					try {
						textoMostrado.append(texto.charAt(textoMostrado.length()));
						doc.insertString(doc.getLength(), String.valueOf(textoMostrado.charAt(textoMostrado.length() - 1)), estilo);
						outputTexto.setCaretPosition(doc.getLength());

					} catch(BadLocationException e) {
						e.printStackTrace();
					}
				});

			} catch(Exception e) {
				e.printStackTrace();
			}
		});
	}*/

	// Método para mostrar mensajes en el outputTexto (JTextPane).
	/*public void outputTexto(String texto, Font fuente) {
		SwingUtilities.invokeLater(() -> {
			try {
				StyledDocument doc = outputTexto.getStyledDocument();
				SimpleAttributeSet estilo = new SimpleAttributeSet();
				StyleConstants.setFontFamily(estilo, fuente.getFamily());
				StyleConstants.setFontSize(estilo, fuente.getSize());
				StyleConstants.setLineSpacing(estilo, Fuente.INTERLINEADO);
				doc.setParagraphAttributes(0, doc.getLength(), estilo, false);

				outputTexto.setCaretPosition(outputTexto.getDocument().getLength());

				doc.insertString(doc.getLength(), texto + "\n", estilo);

			} catch(BadLocationException e) {
				e.printStackTrace();
			}
		});
	}*/

	// Método para mostrar mensajes del juego.
	public void outputTexto(String texto, Font fuente) {
		SwingUtilities.invokeLater(() -> {
			try {
				StyledDocument doc = outputTexto.getStyledDocument();

				// Establecer los estilos base
				SimpleAttributeSet estilo = new SimpleAttributeSet();
				StyleConstants.setFontFamily(estilo, fuente.getFamily());
				StyleConstants.setFontSize(estilo, fuente.getSize());
				StyleConstants.setLineSpacing(estilo, Fuente.INTERLINEADO);
				doc.setParagraphAttributes(0, doc.getLength(), estilo, false);

				outputTexto.setCaretPosition(outputTexto.getDocument().getLength());

				// Procesar y aplicar los estilos en el texto
				procesarEstilosYInsertar(texto, doc);

			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		});
	}

	// Método para procesar y aplicar los estilos al texto
	private void procesarEstilosYInsertar(String texto, StyledDocument doc) throws BadLocationException {
		// Estilos predefinidos
		SimpleAttributeSet negrita = new SimpleAttributeSet();
		StyleConstants.setBold(negrita, true);

		SimpleAttributeSet cursiva = new SimpleAttributeSet();
		StyleConstants.setItalic(cursiva, true);

		// Atributo dinámico para colores
		SimpleAttributeSet estiloColor = new SimpleAttributeSet();

		// Regex ajustada para estilos y colores, con cierre de etiquetas y prioridad para iconos
		Pattern pattern = Pattern.compile("(%icon\\[(.*?)\\]|%b(.*?)%/b|%i(.*?)%/i|%c\\[(.*?)\\](.*?)%/c|\\n|[^%]+)");
		Matcher matcher = pattern.matcher(texto);

		while(matcher.find()) {
			// Salto de línea
			if(matcher.group(0).equals("\n")) {
				doc.insertString(doc.getLength(), "\n", null);
			} 
			// Insertar un icono (%icon[icono])
			else if(matcher.group(2) != null) {
				String nombreIcono = matcher.group(2);  // Obtener el nombre del icono
				ImageIcon icono = cargarIcono(nombreIcono, Config.temaActual.getColorEnfasis()); // Cargar el icono

				if(icono != null) {
					// Crear un atributo de estilo para insertar el icono
					SimpleAttributeSet iconoAtributo = new SimpleAttributeSet();
					StyleConstants.setIcon(iconoAtributo, icono);

					// Insertar el icono en el documento
					doc.insertString(doc.getLength(), " ", iconoAtributo);  // Espacio para no romper el flujo de texto
				}
			} 
			// Texto en negrita (%b...%/b)
			else if(matcher.group(3) != null) {
				doc.insertString(doc.getLength(), matcher.group(3), negrita);
			} 
			// Texto en cursiva (%i...%/i)
			else if(matcher.group(4) != null) {
				doc.insertString(doc.getLength(), matcher.group(4), cursiva);
			} 
			// Texto con color (%c[color]...%/c)
			else if(matcher.group(6) != null) {
				String colorNombre = matcher.group(5).toLowerCase(); // Obtener el color
				Color color = obtenerColorPorNombre(colorNombre);
				StyleConstants.setForeground(estiloColor, color);

				// Insertar texto con color
				doc.insertString(doc.getLength(), matcher.group(6), estiloColor);

				// Resetear el color
				StyleConstants.setForeground(estiloColor, Color.BLACK);
			} 
			// Texto normal (sin estilo)
			else {
				doc.insertString(doc.getLength(), matcher.group(0), null);
			}
		}

		// Insertar un salto de línea final para separar bloques de texto
		doc.insertString(doc.getLength(), "\n", null);
	}

	// Método auxiliar para obtener un color por su nombre
	private Color obtenerColorPorNombre(String colorNombre) {
		switch (colorNombre) {
		case "red": return Color.RED;
		case "blue": return Color.BLUE;
		case "green": return Color.GREEN;
		case "yellow": return Color.YELLOW;
		case "black": return Color.BLACK;
		case "white": return Color.WHITE;
		case "gray": return Color.GRAY;
		case "orange": return Color.ORANGE;
		case "pink": return Color.PINK;
		default: 
			try {
				// Intentar interpretar un color en formato hexadecimal
				return Color.decode(colorNombre);  // Permite colores como "#FF5733"
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Color desconocido o no válido: " + colorNombre);
			}
		}
	}

	// Método para cargar el icono y cambiar su color con redimensión
	public static ImageIcon cargarIcono(String nombreIcono, Color nuevoColor) {
		try {
			// Ajusta la ruta de acuerdo con la ubicación de la carpeta 'resources'
			File archivoIcono = new File("resources/icons/" + nombreIcono + ".png");

			// Verifica si el archivo existe
			if(archivoIcono.exists()) {
				// Cargar la imagen original
				Image img = new ImageIcon(archivoIcono.getAbsolutePath()).getImage();

				// Redimensionar la imagen a un tamaño más pequeño (por ejemplo, 32x32) usando Graphics2D
				int nuevoAncho = Config.TAMANO_ICONO;  // Tamaño deseado
				int nuevoAlto = Config.TAMANO_ICONO;

				// Crear una imagen con un buffer de memoria
				BufferedImage imgRedimensionada = new BufferedImage(nuevoAncho, nuevoAlto, BufferedImage.TYPE_INT_ARGB);

				// Usar Graphics2D para dibujar la imagen redimensionada
				Graphics2D g2d = imgRedimensionada.createGraphics();
				g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

				// Dibujar la imagen redimensionada
				g2d.drawImage(img, 0, 0, nuevoAncho, nuevoAlto, null);
				g2d.dispose(); // Liberar recursos del gráfico

				// Cambiar el color de los píxeles de la imagen
				for(int x = 0; x < imgRedimensionada.getWidth(); x++) {
					for(int y = 0; y < imgRedimensionada.getHeight(); y++) {
						int pixel = imgRedimensionada.getRGB(x, y);

						// Si el píxel no es transparente, cambiar su color
						if((pixel >> 24) != 0x00) { // El valor del canal alfa (transparente es 0x00)
							Color colorOriginal = new Color(pixel, true);
							// Verificar si el color original es blanco (o el color que deseas cambiar)
							if(colorOriginal.getRed() == 255 && colorOriginal.getGreen() == 255 && colorOriginal.getBlue() == 255) {
								// Cambiar el color del píxel a nuevoColor
								imgRedimensionada.setRGB(x, y, nuevoColor.getRGB());
							}
						}
					}
				}

				// Crear y devolver un ImageIcon con la imagen redimensionada y coloreada
				return new ImageIcon(imgRedimensionada);
			} else {
				System.out.println("Icono no encontrado: " + nombreIcono);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Método para ejecutar la misión.
	private void ejecutarMision() {
		misionActiva = misiones.ejecutarMisiones();
	}

	// Mostrar mensaje de la misión.
	private void mostrarMensajeDeMision() {
		if(misionActiva != null && !misionActiva.isCompletada() && !misionActiva.isMensajeMostrado()) {
			String mensajeSistema = "%icon[scroll-unfurled] NUEVA MISIÓN: " + misionActiva.getNombre() + "\n" + misionActiva.getMensaje();
			outputTexto(mensajeSistema, Fuente.fuenteBase);
			misionActiva.setMensajeMostrado(true);
		}
	}

	// Completar una misión.
	private void procesarMisionCompletada() {
		int recompensa = misionActiva.getRecompensa();

		String mensaje = "%icon[scroll-unfurled] " + String.format("¡MISIÓN \"%s\" COMPLETADA!\n- Recompensa: %d puntos.", misionActiva.getNombre(), recompensa);
		outputTexto(mensaje, Fuente.fuenteBase);

		// Sumar los puntos de la misión.
		jugador.setPuntos(jugador.getPuntos() + recompensa);
		actualizarLabelPuntos();

		// Avanzar a la siguiente misión.
		avanzarASiguienteMision();
	}

	// Método para avanzar a la siguiente misión al completar una.
	private void avanzarASiguienteMision() {
		if(misionActiva != null && misionActiva.isCompletada()) {
			misionActiva = misiones.ejecutarMisiones();

			if(misionActiva != null) {
				if(!misionActiva.isMensajeMostrado()) {
					String mensajeSistema = "\n%icon[scroll-unfurled] NUEVA MISIÓN: " + misionActiva.getNombre() + "\n" + misionActiva.getMensaje();
					outputTexto(mensajeSistema, Fuente.fuenteBase);
					misionActiva.setMensajeMostrado(true);
				}

				notificarComandoIngresado();
			}
		}
	}

	// Actualizar label de puntos.
	public void actualizarLabelPuntos() {
		labelPuntuacion.setText("PUNTOS: " + jugador.getPuntos());
	}

	public void actualizarLabelUbicacion() {
		labelUbicacion.setText("UBICACIÓN: " + jugador.getUbicacion().getNombre());
	}

	// Método para esperar a que el jugador introduzca un comando.
	private void esperarComando() {
		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		outputTexto(obtenerUltimoComando(), Fuente.fuenteBase);
	}

	// Método para notificar al hilo principal cuando se ha ingresado un comando.
	public synchronized void notificarComandoIngresado() {
		notify();
	}

	// Método para obtener el comando del jugador del inputTexto.
	public String obtenerUltimoComando() {
		return inputTexto.getText().trim();
	}

}