package objetos;

import objetos.Arma.Alcance;

public class Armas {

	public enum Filo {

		CUCHILLO(Alcance.MUY_CORTO),
		DAGA(Alcance.MUY_CORTO),
		PUNAL(Alcance.MUY_CORTO),
		ESPADA_CORTA(Alcance.CORTO),
		ESPADA_LARGA(Alcance.MEDIO),
		ESPADA_MORGATHI(Alcance.MEDIO),
		ESPADA_FJELLFOLK(Alcance.CORTO),
		ESPADA_FLAMBERGA(Alcance.MEDIO),
		ESTOQUE(Alcance.MEDIO),
		FALCATA(Alcance.CORTO),
		KATANA(Alcance.MEDIO),
		CIMITARRA(Alcance.CORTO),
		BRACAMARTE(Alcance.CORTO),
		KHOPESH(Alcance.MEDIO),
		MACHETE(Alcance.CORTO),
		ALFANJE(Alcance.MEDIO),
		CLAYMORE(Alcance.LARGO);

		private final Arma.Alcance alcance;

		Filo(Arma.Alcance alcance) {
			this.alcance = alcance;
		}

		public Arma.Alcance getAlcance() {
			return alcance;
		}

	}

	public enum Hacha {

		HACHA_DE_LENADOR(Alcance.MEDIO),
		HACHA_DE_GUERRA(Alcance.LARGO),
		HACHA_DE_MANO(Alcance.CORTO),
		HACHA_FJELLFOLK(Alcance.MEDIO),
		HACHA_DE_DOBLE_FILO(Alcance.LARGO),
		TOMAHAWK(Alcance.MUY_CORTO);

		private final Arma.Alcance alcance;

		Hacha(Arma.Alcance alcance) {
			this.alcance = alcance;
		}

		public Arma.Alcance getAlcance() {
			return alcance;
		}

	}

	public enum Roma {

		CESTUS(Alcance.MUY_CORTO),
		MAZA(Alcance.MEDIO),
		LUCERO_DEL_ALBA(Alcance.MEDIO),
		MANGUAL(Alcance.LARGO),
		MARTILLO_DE_GUERRA(Alcance.LARGO),
		MARTILLO_DE_MANO(Alcance.CORTO),
		MARTILLO_FJELLFOLK(Alcance.MEDIO),
		PICO_DE_CUERVO(Alcance.MEDIO),
		PORRA(Alcance.MUY_CORTO),
		GARROTE(Alcance.CORTO);
		
		private final Arma.Alcance alcance;

		Roma(Arma.Alcance alcance) {
			this.alcance = alcance;
		}

		public Arma.Alcance getAlcance() {
			return alcance;
		}

	}

	public enum Asta {

		LANZA(Alcance.LARGO),
		PICA(Alcance.LARGO),
		ALABARDA(Alcance.LARGO),
		GUJA(Alcance.LARGO),
		RONCONA(Alcance.LARGO),
		GUADANA_DE_GUERRA(Alcance.LARGO);
		
		private final Arma.Alcance alcance;

		Asta(Arma.Alcance alcance) {
			this.alcance = alcance;
		}

		public Arma.Alcance getAlcance() {
			return alcance;
		}

	}

	public enum Distancia {

		ARCO_CORTO(Alcance.MUY_LARGO),
		ARCO_LARGO(Alcance.MUY_LARGO),
		ARCO_DE_CAZA(Alcance.MUY_LARGO),
		ARCO_DE_MJORVEGR(Alcance.MUY_LARGO),
		BALLESTA(Alcance.MUY_LARGO),
		HONDA(Alcance.MUY_LARGO),
		JABALINA(Alcance.MUY_LARGO),
		HACHA_ARROJADIZA(Alcance.MUY_LARGO),
		CUCHILLO_ARROJADIZO(Alcance.MUY_LARGO),
		CERBATANA(Alcance.MUY_LARGO),
		TIRACHINAS(Alcance.MUY_LARGO),
		ESTRELLA_ARROJADIZA(Alcance.MUY_LARGO);
		
		private final Arma.Alcance alcance;

		Distancia(Arma.Alcance alcance) {
			this.alcance = alcance;
		}

		public Arma.Alcance getAlcance() {
			return alcance;
		}

	}

}
