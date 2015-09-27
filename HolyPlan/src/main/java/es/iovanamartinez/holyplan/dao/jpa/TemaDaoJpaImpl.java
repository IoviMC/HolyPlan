package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.TemaDao;
import es.iovanamartinez.holyplan.dominio.Tema;

@Repository("TemaDao")
public class TemaDaoJpaImpl extends GenericDaoJpaImpl<Tema> implements TemaDao {

}
