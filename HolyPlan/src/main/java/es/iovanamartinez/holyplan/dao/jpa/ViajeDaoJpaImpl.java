package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.ViajeDao;
import es.iovanamartinez.holyplan.dominio.Viaje;

@Repository(value = "viajeDao")
public class ViajeDaoJpaImpl extends GenericDaoJpaImpl<Viaje> implements ViajeDao {
//	
//	@PersistenceContext
//    private EntityManager em;
//	
//	@Override
//	public List<Viaje> listarViajesUsuario(Integer idUsuario){
//
//		TypedQuery<Viaje> query = em.createQuery("SELECT e FROM Viaje e JOIN e.usuarios u where u.id.usuario = :idUsuario", Viaje.class);
//		query.setParameter("idUsuario", idUsuario);
//		
//		List<Viaje> viajes = query.getResultList();
//		return viajes;
//	}
}
