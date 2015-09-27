package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.ItemListViajeDao;
import es.iovanamartinez.holyplan.dominio.ItemListViaje;

@Repository("ItemListViajeDao")
public class ItemListViajeDaoJpaImpl extends GenericDaoJpaImpl<ItemListViaje> implements ItemListViajeDao{

}
