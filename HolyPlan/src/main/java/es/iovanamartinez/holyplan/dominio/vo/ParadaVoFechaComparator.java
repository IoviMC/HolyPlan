package es.iovanamartinez.holyplan.dominio.vo;

import java.util.Comparator;

public class ParadaVoFechaComparator implements Comparator<ParadaVo> {

	@Override
	public int compare(ParadaVo o1, ParadaVo o2) {
		return o1.getFecha().compareTo(o2.getFecha());
	}

}
