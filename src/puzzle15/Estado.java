package puzzle15;

import lib.Operador;

/**
 * Estado del 15-puzzle.
 */
public class Estado implements Comparable<Estado>
{
	/* static Estado with the target objective */
	public static final Estado objetivo 
		= new Estado(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
	
	public static final Operador<Estado> up = new Operador<Estado>() 
	{ /* UP MOVEMENT */
		@Override
		public Estado run(Estado t) {
			if (t.pos_hole < 4) return null;
			Estado res = new Estado(t);
			res.interchange(-4);
			return res;
		}
	};
	public static final Operador<Estado> down = new Operador<Estado>() 
	{ /* DOWN MOVEMENT */
		@Override
		public Estado run(Estado t) {
			if (t.pos_hole >= N - 4) return null;
			Estado res = new Estado(t);
			res.interchange(+4);
			return res;
		}
	};
	public static final Operador<Estado> left = new Operador<Estado>() 
	{ /* LEFT MOVEMENT */
		@Override
		public Estado run(Estado t) {
			if (t.pos_hole % 4 == 0) return null;
			Estado res = new Estado(t);
			res.interchange(-1);
			return res;
		}
	};
	public static final Operador<Estado> right = new Operador<Estado>() { /* RIGHT MOVEMENT */
		@Override
		public Estado run(Estado t) {
			if (t.pos_hole % 4 == 3) return null;
			Estado res = new Estado(t);
			res.interchange(+1);
			return res;
		}
	};
	
	@SuppressWarnings("unchecked")
	public static final Operador<Estado>[] moves = new Operador[] {
			up, down, left, right,
	};
	
	public static final Heuristica descolocadas = new Heuristica() {
		@Override
		public int getH(Estado e) {
			int res = 0;
			for (int i = 0; i < N; i++) {
				if (e.tiles[i] != i) res++;
			} /* for */
			return res;
		}
	};
	
	public static final Heuristica manhattan = new Heuristica() {
		@Override
		public int getH(Estado e) {
			int res = 0;
			for (int i = 0; i < N; i++) {
				if (e.tiles[i] != i) {
					int dx = (e.tiles[i] % 4) - (i % 4); if (dx < 0) dx = -dx;
					int dy = (e.tiles[i] / 4) - (i / 4); if (dy < 0) dy = -dy;
					res += dx + dy;
				}
			}
			return res;
		}
	};

	private static final int N = 16; /* number of cells of array */
	private int pos_hole; /* hole position */
	private int tiles[] = new int[N]; /* values of the tiles, from zero
											(for the hole) to N-1. */
	private Estado parent; /* previous state */
	private int depth; /* depth of state */
	
	
	/* private function to check the parameters to constructor */
	private static boolean check(int[]f) {
		if (f.length != N) 
			return false;
		int[] aux = new int[f.length];
		for (int n: f) aux[n]++;
		for (int i = 0; i < aux.length; i++)
			if (aux[i] != 1) return false;
		return true;
	}
	
	/* private function to do common initialization in both constructors */
	private void init(Estado p)
	{
		parent = p;
		depth = (p != null)
			? p.depth+1
			: 0;
	}
	
	/* private method to interchange the tiles at pos_hole and pos_hole+off.
	 * off can be positive or negative, depending on the element to inter-
	 * change. */
	private void interchange(int off)
	{
		int new_hole = pos_hole + off;
		int x = tiles[pos_hole];
		tiles[pos_hole] = tiles[new_hole];
		tiles[new_hole] = x;
		pos_hole = new_hole;
	}

	/**
	 * Construye un estado a partir de un array de fichas.
	 * @param f Lista de las 16 fichas (15 y el hueco) del puzzle.
	 * @throws InvalidValueException 
	 */
	public Estado(int...f) {
		if (!check(f))
			throw new IllegalArgumentException(
					"array f is not a valid state");
		for (int i = 0; i < f.length; i++) {
			tiles[i] = f[i];
			if (f[i] == 0) pos_hole = i;
		} /* for */
		init(null); /* no previous state */
	}
	
	private Estado(Estado p)
	{
		pos_hole = p.pos_hole;
		for (int i = 0; i < N; i++) {
			tiles[i] = p.tiles[i];
		}
		init(p); /* previous state is p */
	}

	/**
	 * Devuelve el estado padre.
	 * @return estado padre
	 */
	public Estado getPadre()
	{
		return parent;
	}

	/**
	 * Devuelve la profundidad del estado.
	 * @return profundidad
	 */
	public int getProfundidad()
	{
		return depth;
	}

	/**
	 * Calcula el código hash.
	 * @return Código hash.
	 */
	@Override public int hashCode()
	{
		int acum = 0;
		
		for (int i = 0; i < N; i++) {
			acum *= 687517517;
			acum += tiles[i];
			acum %= 999298081;
		}
		return acum;
	}

	/**
	 * Compara un estado con otro objeto.
	 * @param obj Objeto a comparar.
	 * @return {@code true} si el estado es igual al objeto.
	 */
	@Override public boolean equals(Object obj)
	{
		if (obj instanceof Estado) {
			return compareTo((Estado) obj) == 0;
		}
		return false;
	}

	/**
	 * Compara un estado con otro.
	 * <p>Aparentemente esta comparación no tiene sentido,
	 * pero es necesaria para utilizar la cola de prioridad.
	 * @param e estado
	 * @return un número negativo, cero, o positivo si este estado
	 *         es menor, igual, o mayour que el estado indicado.
	 */
	@Override public int compareTo(Estado e)
	{
		for (int i = 0; i < N; i++)
			if (tiles[i] != e.tiles[i])
				return tiles[i] - e.tiles[i];
		return 0;
	}

	/**
	 * Comprueba si el estado es el objetivo.
	 * @return {@code true} si el estado es el objetivo.
	 */
	public boolean esObjetivo()
	{
		return equals(objetivo);
	}
	
	@Override
	public String toString()
	{
		StringBuffer b = new StringBuffer();
		b.append("[" + hashCode() + "]:");
		for (int i = 0; i < N; i++)
			b.append(" " + tiles[i]);
		return b.toString();
	}
	
	public String solucion()
	{
		StringBuffer b = new StringBuffer();
		if (parent != null) {
			b.append(parent.solucion());
			b.append("=========== ["+depth+"]");
		}
		for (int i = 0; i < N; i++) {
			b.append(i % 4 == 0 ? "\n" : " ");
			if (tiles[i] == 0) {
				b.append("  ");
			} else {
				b.append(String.format("%02d", tiles[i]));
			} /* if */
		} /* for */
		b.append("\n");
		return b.toString();
	}

} // Estado
