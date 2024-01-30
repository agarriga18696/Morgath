package utilidades;

import java.util.Random;

public class Aleatorio {

	private static final Random random = new Random();

	// Generar Int aleatorio.
	public static int Int(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}

	// Generar Double aleatorio.
	public static double Double(double min, double max) {
		return min + (max - min) * random.nextDouble();
	}

	// Generar Boolean aleatorio.
	public static boolean Boolean() {
		
		return random.nextBoolean();
	}

}
