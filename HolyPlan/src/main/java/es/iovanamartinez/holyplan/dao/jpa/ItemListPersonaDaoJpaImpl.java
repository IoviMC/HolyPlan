package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.ItemListPersonaDao;
import es.iovanamartinez.holyplan.dominio.ItemListPersona;

@Repository("ItemListPersonaDao")
public class ItemListPersonaDaoJpaImpl extends GenericDaoJpaImpl<ItemListPersona> implements ItemListPersonaDao{

}
