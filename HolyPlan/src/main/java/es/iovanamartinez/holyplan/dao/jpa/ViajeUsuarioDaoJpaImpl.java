package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.ViajeUsuarioDao;
import es.iovanamartinez.holyplan.dominio.ViajeUsuario;



@Repository("ViajeUsuarioDao")
public class ViajeUsuarioDaoJpaImpl extends GenericDaoJpaImpl<ViajeUsuario> implements ViajeUsuarioDao {

}
