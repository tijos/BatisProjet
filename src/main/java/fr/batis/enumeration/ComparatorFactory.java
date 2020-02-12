package main.java.fr.batis.enumeration;

import java.util.Comparator;

import main.java.fr.batis.entites.NomsMateriaux;


public class ComparatorFactory implements Comparator<NomsMateriaux> {

	@Override
	public int compare(NomsMateriaux o1, NomsMateriaux o2) {
		return o1.getNom().compareTo(o2.getNom());
	}

}
