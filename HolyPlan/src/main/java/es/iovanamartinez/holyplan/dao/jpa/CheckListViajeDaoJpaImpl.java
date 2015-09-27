package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.CheckListViajeDao;
import es.iovanamartinez.holyplan.dominio.CheckListViaje;

@Repository("CheckListViajeDao")
public class CheckListViajeDaoJpaImpl extends GenericDaoJpaImpl<CheckListViaje> implements CheckListViajeDao{

}
