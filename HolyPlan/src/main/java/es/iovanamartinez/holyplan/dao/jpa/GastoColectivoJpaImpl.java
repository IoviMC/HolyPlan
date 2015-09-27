package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.GastoColectivoDao;
import es.iovanamartinez.holyplan.dominio.GastoColectivo;

@Repository("GastoColectivoDao")
public class GastoColectivoJpaImpl extends GenericDaoJpaImpl<GastoColectivo> implements GastoColectivoDao {

}
