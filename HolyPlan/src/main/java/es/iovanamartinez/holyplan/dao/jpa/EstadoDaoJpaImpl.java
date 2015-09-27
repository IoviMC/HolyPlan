package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.EstadoDao;
import es.iovanamartinez.holyplan.dominio.Estado;

@Repository("EstadoDao")
public class EstadoDaoJpaImpl extends GenericDaoJpaImpl<Estado> implements EstadoDao{

}
