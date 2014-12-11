package lib;

import java.util.*;

/**
 * Cola de prioridad.
 * @param <T> Tipo de los elementos almacenados en el conjunto.
 */
public class ColaPrioridad<T extends Comparable<T>> implements Iterable<T>
{
private HashMap<T,T> map = new HashMap<T,T>();
private TreeSet<T> set;

/**
 * Construye una cola de prioridad mediante un comparador.
 * @param c Comparador que establece la prioridad entre los elementos.
 */
@SuppressWarnings({ "unchecked", "hiding", "rawtypes" })
public <T extends Comparable<T>> ColaPrioridad(final Comparator<T> c)
{
    Comparator<T> comparator = new Comparator<T>()
    {@Override public int compare(T e1, T e2)
    {
        int r = c.compare(e1, e2);
        return r == 0 ? e1.compareTo(e2) : r;
    }};

    set = new TreeSet(comparator);
}

/**
 * Comprueba si existe un elemento aunque con distinta prioridad.
 * @param e Elemento a buscar.
 * @return true si existe un elemento igual aunque tenga distinta prioridad.
 */
public boolean contains(T e)
{
    return map.containsKey(e);
}

/**
 * Añade un elemento a la cola de prioridad. El elemento no debe existir.
 * @param e Elemento que se añade a la cola.
 */
public void add(T e)
{
    if(map.put(e, e) != null)
        throw new AssertionError();

    if(!set.add(e))
        throw new AssertionError();
}

/**
 * Obtiene un elemento idéntico si existe.
 * Puede que el elemento devuelto tenga una prioridad distinta.
 * @param e Elemento con el que se buscará un elemento idéntico.
 * @return Elemento idéntico si existe o null si no existe.
 */
public T get(T e)
{
    return map.get(e);
}

/**
 * Obtiene el elemento con mayor prioridad.
 * @return Primer elemento de la cola de prioridad.
 */
public T first()
{
    return set.first();
}

/**
 * Obtiene el elemento con menor prioridad.
 * @return Último elemento de la cola de prioridad.
 */
public T last()
{
    return set.last();
}

/**
 * Obtiene y borra el elemento con mayor prioridad.
 * @return Primer elemento de la cola de prioridad.
 */
public T poll()
{
    T e = set.first();
    remove(e);
    return e;
}

/**
 * Elimina un elemento de la cola. El elemento debe existir.
 * @param e Elemento a eliminar.
 */
public void remove(T e)
{
    if(map.remove(e) != e)
        throw new AssertionError();

    if(!set.remove(e))
        throw new AssertionError();
}

/**
 * Indica si la cola está vacía.
 * @return True si la cola está vacía.
 */
public boolean isEmpty()
{
    return set.isEmpty();
}

/**
 * Obtiene el tamaño de la cola.
 * @return Tamaño de la cola.
 */
public int size()
{
    return set.size();
}

/**
 * Obtiene un iterador sobre los elementos por orden de prioridad.
 * @return iterador
 */
@Override public Iterator<T> iterator()
{
    return set.iterator();
}

} // ColaPrioridad
