package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.PagoGastoColectivoDao;
import es.iovanamartinez.holyplan.dominio.PagoGastoColectivo;

@Repository("PagoGastoColectivoDao")
public class PagoGastoColectivoDaoJpaImpl extends GenericDaoJpaImpl<PagoGastoColectivo> implements PagoGastoColectivoDao{

}
