package es.iovanamartinez.holyplan.test.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dao.UsuarioDao;
import es.iovanamartinez.holyplan.dominio.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class UsuarioDaoTest {
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Test
	public void buscarPorNombreUsuarioTest() {
		String nombreUsuario= "Iovana";
		Usuario usuario = usuarioDao.buscarPorNombreUsuario(nombreUsuario);
		
		assertEquals("Iovana", usuario.getNombreUsuario());
		assertEquals("iovana18@hotmail.com", usuario.getEmail());
		assertEquals("111111", usuario.getContrasena());
	}
	
	@Test
	public void buscarUsuarioPorEmail() {	
		String email = "iovana18@hotmail.com";
		Usuario usuario = usuarioDao.buscarUsuarioPorEmail(email);
		
		assertEquals("Iovana", usuario.getNombreUsuario());
		assertEquals("iovana18@hotmail.com", usuario.getEmail());
		assertEquals("111111", usuario.getContrasena());
	}

	@Test
	public void buscarUsuarioPorHash() {
		String hash = "Sdkfsalkjfasdf";
		Usuario usuario = usuarioDao.buscarUsuarioPorHash(hash);
		
		assertEquals("Iovana", usuario.getNombreUsuario());
		assertEquals("iovana18@hotmail.com", usuario.getEmail());
		assertEquals("111111", usuario.getContrasena());
	}
}
