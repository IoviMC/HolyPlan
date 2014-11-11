package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.ViajeDao;
import es.iovanamartinez.holyplan.dominio.Viaje;

@Repository(value = "viajeDao")
public class ViajeDaoJpaImpl extends GenericDaoJpaImpl<Viaje> implements ViajeDao {

}
