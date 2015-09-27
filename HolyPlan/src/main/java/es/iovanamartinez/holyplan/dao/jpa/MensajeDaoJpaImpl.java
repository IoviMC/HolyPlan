package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.MensajeDao;
import es.iovanamartinez.holyplan.dominio.Mensaje;

@Repository("MensajeDao")
public class MensajeDaoJpaImpl extends GenericDaoJpaImpl<Mensaje> implements MensajeDao {

}
