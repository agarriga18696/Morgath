package juego;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import comandos.Comandos;
import configuracion.Config;
import localizaciones.Habitacion;
import localizaciones.Mapa;
import localizaciones.Mapa.Direccion;
import misiones.Mision;
import misiones.Misiones;

public class Juego extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel panelPrincipal, panelCentral, panelSuperior, panelInferior;
	public JLabel labelUbicacion, labelPuntuacion, labelCursor;
	public JScrollPane barraScrollOutput;
	public JTextArea outputTexto;
	public JTextField inputTexto;

	private Jugador jugador;
	private Misiones misiones;
	private Mapa mapa;
	private Comandos comandos;
	public Mision misionActiva;
	private boolean empezarJuego = false;

	public Juego() {

		mapa = new Mapa();
		Habitacion ubicacionInicial = mapa.obtenerHabitacionInicial();
		jugador = new Jugador(ubicacionInicial);
		misiones = new Misiones();
		comandos = new Comandos(jugador, this, mapa);

		setSize(Config.anchoVentana, Config.altoVentana);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("MORGATH I");

		// Paneles.
		panelPrincipal = new JPanel();
		panelCentral = new JPanel();
		panelSuperior = new JPanel();
		panelInferior = new JPanel();

		// Area Texto.
		outputTexto = new JTextArea();
		inputTexto = new JTextField();

		inputTexto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String comando = inputTexto.getText();
				outputTexto(Config.CURSOR + " " + comando);
				comandos.ejecutarComando(comando);

				// Limpiar el campo de texto después de obtener el texto.
				inputTexto.setText("");

				// Notificar al bucle que se ha ingresado un comando.
				notificarComandoIngresado();
			}
		});

		// Barra Scroll.
		barraScrollOutput = new JScrollPane(outputTexto);
		barraScrollOutput.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		barraScrollOutput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		// Etiquetas.
		labelUbicacion = new JLabel("UBICACIÓN: " + jugador.getUbicacion().getNombre());
		labelPuntuacion = new JLabel("PUNTOS: " + jugador.getPuntos());
		labelCursor = new JLabel(Config.CURSOR);

		configurarVentana();
		setVisible(true);

		SwingUtilities.invokeLater(() -> {
			// Hacer autofocus en la ventana y en el inputTexto.
			requestFocusInWindow();
			inputTexto.requestFocus();
		});

	}

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
			panelCentral.add(barraScrollOutput, BorderLayout.CENTER);
			barraScrollOutput.setBorder(null);
			outputTexto.setLineWrap(true);
			outputTexto.setWrapStyleWord(true);
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
			comandos.actualizarInterfazSegunTema();
		});
	}

	// Método para mostrar mensajes en el outputTexto.
	public void outputTexto(String texto) {
		outputTexto.append(texto + "\n");
		outputTexto.setCaretPosition(outputTexto.getDocument().getLength());
	}

	// Método para iniciar la partida.
	public void nuevaPartida() {
		misionActiva = misiones.ejecutarMisiones();
		mostrarMensajeDeMision();
		empezarJuego = true;
		
		while(empezarJuego) {
			ejecutarMision();

			// Verificar si has terminado todas las misiones.
			if (misionActiva == null) {
				outputTexto("¡Has completado todas las misiones! ¡Felicidades!");
				break;
			}
			mostrarMensajeDeMision();

			// Finalizar la misión inicial (introducción).
			if (misionActiva.getNombre().equalsIgnoreCase("Iniciación")) {
			    if (obtenerUltimoComando().equalsIgnoreCase("EXPLORAR")) {
			        misionActiva.finalizarMision(misionActiva);
			        // Obtener la habitación hacia el norte desde la habitación actual del jugador.
			        mapa.agregarConexion(jugador.getUbicacion(), mapa.habitacion1, Direccion.ESTE);
			    }
			}


			if(misionActiva.getNombre().equalsIgnoreCase("Despertar")) {
				if(jugador.getInventario() != null && !jugador.getInventario().isEmpty() && jugador.getInventario().get(0).getNombre().equalsIgnoreCase("Espada")) {
					misionActiva.finalizarMision(misionActiva);
				}
			}

			if (misionActiva.isCompletada()) {
				procesarMisionCompletada();
			}

			actualizarLabelPuntos();
			esperarComando();
		}
	}

	// Método para ejecutar la misión.
	private void ejecutarMision() {
		misionActiva = misiones.ejecutarMisiones();
	}

	// Mostrar mensaje de la misión.
	private void mostrarMensajeDeMision() {
		if (!misionActiva.isMensajeMostrado()) {
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

		// Añadir misión a la lista de misiones completadas.
		jugador.getMisionesCompletadas().add(misionActiva);

		// Avanzar a la siguiente misión.
		avanzarASiguienteMision();
	}

	// Método para avanzar a la siguiente misión al completar una.
	private void avanzarASiguienteMision() {
		misionActiva = misiones.ejecutarMisiones();

		if (misionActiva != null && !misionActiva.isMensajeMostrado()) {
			String mensajeSistema = "\nNueva Misión: " + misionActiva.getNombre() + "\n\n" + misionActiva.getMensaje();
			outputTexto(mensajeSistema);
			misionActiva.setMensajeMostrado(true);
		}

		notificarComandoIngresado();
	}

	// Actualizar label de puntos.
	private void actualizarLabelPuntos() {
		labelPuntuacion.setText("PUNTOS: " + jugador.getPuntos());
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
	private String obtenerUltimoComando() {
		return inputTexto.getText().trim();
	}

}