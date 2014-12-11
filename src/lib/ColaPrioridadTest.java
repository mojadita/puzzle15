package lib;

import java.util.*;

public class ColaPrioridadTest
{
	public static void main(String args[])
	{
		Comparator<Nodo> cmp = new Comparator<Nodo>()
		{@Override public int compare(Nodo o1, Nodo o2)
		{
			return o1.coste - o2.coste;
		}};

		ColaPrioridad<Nodo> q = new ColaPrioridad<Nodo>(cmp);
		q.add(new Nodo("tres", 3));
		q.add(new Nodo("dos",  2));
		q.add(new Nodo("uno",  1));
		System.out.println("Cola original");

		for(Nodo n : q)
			System.out.println(n);

		Nodo n1 = new Nodo("tres", 0),
		n2 = q.get(n1);

		q.remove(n2);
		q.add(n1);
		System.out.println("\nCola final");

		for(Nodo n : q)
			System.out.println(n);
	}

	private static class Nodo implements Comparable<Nodo>
	{
		private String estado;
		private int coste;

		public Nodo(String estado, int coste)
		{
			this.estado = estado;
			this.coste  = coste;
		}

		@Override public int compareTo(Nodo o)
		{
			return estado.compareTo(o.estado);
		}

		@Override public boolean equals(Object obj)
		{
			if(obj == null || getClass() != obj.getClass())
				return false;
			else
				return estado.equals(((Nodo)obj).estado);
		}

		@Override public int hashCode()
		{
			return estado.hashCode();
		}

		@Override public String toString()
		{
			return estado +": "+ coste;
		}

	} // Nodo

} // ColaPrioridadTest
