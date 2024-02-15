package juego;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.EmptyBorder;

import comandos.Comandos;
import configuracion.Config;
import localizaciones.Habitacion;
import localizaciones.Mapa;
import misiones.Mision;
import misiones.Misiones;
import objetos.ListaObjetos;
import personajes.Jugador;
import personajes.ListaEnemigos;

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
	public static String nombreJugador = "Jugador";


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
		jugador = new Jugador(nombreJugador, ubicacionInicial, 4);
		listaObjetos = new ListaObjetos();
		comandos = new Comandos(jugador, this, mapa);
		misiones = new Misiones();
		listaEnemigos = new ListaEnemigos();

		// Ventana
		setSize(Config.anchoVentana, Config.altoVentana);
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
		labelCursor = new JLabel(Config.CURSOR);

		// Action Listener para el input de texto.
		inputTexto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String comando = inputTexto.getText();
				outputTexto(Config.CURSOR + " " + comando);
				comandos.ejecutarComando(comando);
				ultimoComandoUsado = comando;

				// Limpiar el campo de texto después de obtener el texto.
				inputTexto.setText("");

				// Notificar al bucle que se ha ingresado un comando.
				notificarComandoIngresado();
			}
		});

		SwingUtilities.invokeLater(() -> configurarVentana());

		SwingUtilities.invokeLater(() -> {
			// Hacer autofocus en la ventana y en el inputTexto.
			requestFocusInWindow();
			inputTexto.requestFocus();
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
			labelUbicacion.setFont(Config.fuente);
			labelPuntuacion.setFont(Config.fuente);
			labelCursor.setFont(Config.fuente);
			outputTexto.setFont(Config.fuente);
			inputTexto.setFont(Config.fuente);

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

		while (!ultimoComandoUsado.equalsIgnoreCase("terminar")) {
			mostrarMensajeDeMision();

			// Verificar condiciones específicas de finalización de la misión.
			if (misionActiva != null && misionActiva.verificarCondicionesEspecificas(jugador)) {
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

	// Método para mostrar mensajes en el outputTexto (JTextPane).
	public void outputTexto(String texto) {
		SwingUtilities.invokeLater(() -> {
			SimpleAttributeSet atributos = new SimpleAttributeSet();
			StyledDocument doc = outputTexto.getStyledDocument();
			StyleConstants.setLineSpacing(atributos, Config.espaciadoLinea);
			doc.setParagraphAttributes(0, doc.getLength(), atributos, false);
			
			outputTexto.setCaretPosition(outputTexto.getDocument().getLength());

			try {
				doc.insertString(doc.getLength(), texto + "\n", atributos);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		});
	}

	// Método para ejecutar la misión.
	private void ejecutarMision() {
		misionActiva = misiones.ejecutarMisiones();
	}

	// Mostrar mensaje de la misión.
	private void mostrarMensajeDeMision() {
		if (misionActiva != null && !misionActiva.isCompletada() && !misionActiva.isMensajeMostrado()) {
			String mensajeSistema = "- Nueva Misión: " + misionActiva.getNombre() + "\n\n" + misionActiva.getMensaje();
			outputTexto(mensajeSistema);

			misionActiva.setMensajeMostrado(true);
		}
	}

	// Completar una misión.
	private void procesarMisionCompletada() {
		int recompensa = misionActiva.getRecompensa();

		String mensaje = String.format("- ¡Misión \"%s\" completada!\n- Recompensa: %d puntos.", misionActiva.getNombre(), recompensa);
		outputTexto(mensaje);

		// Sumar los puntos de la misión.
		jugador.setPuntos(jugador.getPuntos() + recompensa);
		actualizarLabelPuntos();

		// Avanzar a la siguiente misión.
		avanzarASiguienteMision();
	}

	// Método para avanzar a la siguiente misión al completar una.
	private void avanzarASiguienteMision() {
		if (misionActiva != null && misionActiva.isCompletada()) {
			misionActiva = misiones.ejecutarMisiones();

			if (misionActiva != null) {
				if (!misionActiva.isMensajeMostrado()) {
					String mensajeSistema = "\nNueva Misión: " + misionActiva.getNombre() + "\n\n" + misionActiva.getMensaje();
					outputTexto(mensajeSistema);
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

		outputTexto(obtenerUltimoComando());
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