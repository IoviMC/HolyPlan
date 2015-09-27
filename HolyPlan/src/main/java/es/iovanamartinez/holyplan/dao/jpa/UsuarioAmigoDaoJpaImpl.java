package es.iovanamartinez.holyplan.dao.jpa;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.UsuarioAmigoDao;
import es.iovanamartinez.holyplan.dominio.UsuarioAmigo;

@Repository("UsuarioAmigoDao")
public class UsuarioAmigoDaoJpaImpl extends GenericDaoJpaImpl<UsuarioAmigo> implements UsuarioAmigoDao {


}
