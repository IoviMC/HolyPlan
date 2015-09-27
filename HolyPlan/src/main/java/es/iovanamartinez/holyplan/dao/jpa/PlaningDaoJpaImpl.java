package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.PlaningDao;
import es.iovanamartinez.holyplan.dominio.Planing;

@Repository("PlaningDao")
public class PlaningDaoJpaImpl extends GenericDaoJpaImpl<Planing> implements PlaningDao {

}
