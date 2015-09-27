package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.CheckListPersonaDao;
import es.iovanamartinez.holyplan.dominio.CheckListPersona;

@Repository("checkListPersonaDao")
public class CheckListPersonaDaoJpaImpl extends GenericDaoJpaImpl<CheckListPersona> implements CheckListPersonaDao{

}
