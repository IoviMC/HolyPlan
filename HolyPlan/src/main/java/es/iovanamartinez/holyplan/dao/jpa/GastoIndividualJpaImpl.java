package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.GastoIndividualDao;
import es.iovanamartinez.holyplan.dominio.GastoIndividual;

@Repository("GastoIndividualDao")
public class GastoIndividualJpaImpl extends GenericDaoJpaImpl<GastoIndividual> implements GastoIndividualDao {

}
