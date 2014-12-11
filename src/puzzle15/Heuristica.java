package puzzle15;

import java.util.*;

/**
 * Interfaz para implementar heurísticas.
 */
public abstract class Heuristica implements Comparator<Estado>
{
/**
 * Compara dos estados según su heurística.
 * @param a Primer estado a comparar.
 * @param b Segundo estado a comparar.
 * @return Un número negativo, cero, o positivo si el primer estado
 *         tiene una heurística menor, igual o mayor que el segundo.
 */
@Override public int compare(Estado a, Estado b)
{
    int h = getH(a) - getH(b),
        r = getG(a) - getG(b) + h;

    return r == 0 ? h : r;
}

//------------------------------------------------------------------------
public int get(Estado e)
{
    return getG(e) + getH(e);
}

/**
 * Calcula el coste hasta el estado indicado.
 * @param e estado
 * @return coste hasa el estado indicado
 */
public int getG(Estado e)
{
    return e.getProfundidad();
}

/**
 * Calcula el coste estimado desde el estado indicado hasta un objetivo.
 * @param e estado
 * @return coste estimado desde el estado indicado hasta un objetivo
 */
public abstract int getH(Estado e);

} // Heuristica
