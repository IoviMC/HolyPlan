package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.RolDao;
import es.iovanamartinez.holyplan.dominio.Rol;

@Repository("RolDao")
public class RolDaoJpaImpl extends GenericDaoJpaImpl<Rol> implements RolDao{

}
