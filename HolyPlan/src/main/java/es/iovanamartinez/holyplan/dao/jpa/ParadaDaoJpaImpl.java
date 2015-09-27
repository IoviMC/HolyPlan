package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.ParadaDao;
import es.iovanamartinez.holyplan.dominio.Parada;

@Repository("ParadaDao")
public class ParadaDaoJpaImpl extends GenericDaoJpaImpl<Parada> implements ParadaDao {

}
