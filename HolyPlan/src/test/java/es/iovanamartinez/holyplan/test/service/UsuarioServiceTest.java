package es.iovanamartinez.holyplan.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeVo;
import es.iovanamartinez.holyplan.service.UsuarioService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UsuarioServiceTest {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	public void crearUsuarioTest() {
		UsuarioVo usuario = new UsuarioVo("Alex", "111111", "alex@pruebas.com");
		
		usuario = usuarioService.crearUsuario(usuario);
		UsuarioVo usuarioBD = usuarioService.getUsuario(usuario.getId());
		
		assertEquals("Alex", usuarioBD.getNombreUsuario());
		assertEquals("alex@pruebas.com", usuarioBD.getEmail());
		assertEquals("111111", usuarioBD.getContrasena());
	}
	
	@Test
	public void getUsuarioPorNombreTest() {		
		UsuarioVo usuarioBD = usuarioService.getUsuarioPorNombre("Iovana");
		assertEquals("Iovana", usuarioBD.getNombreUsuario());
		assertEquals("iovana18@hotmail.com", usuarioBD.getEmail());
		assertEquals("111111", usuarioBD.getContrasena());
	}
	
	@Test
	public void getUsuarioTest() {		
		UsuarioVo usuarioBD = usuarioService.getUsuario(1);
		
		assertEquals("Iovana", usuarioBD.getNombreUsuario());
		assertEquals("iovana18@hotmail.com", usuarioBD.getEmail());
		assertEquals("111111", usuarioBD.getContrasena());
	}
	
	@Test
	public void getUsuarioPorEmailTest() {		
		UsuarioVo usuarioBD = usuarioService.getUsuarioPorEmail("iovana18@hotmail.com");
		assertEquals("Iovana", usuarioBD.getNombreUsuario());
		assertEquals("iovana18@hotmail.com", usuarioBD.getEmail());
		assertEquals("111111", usuarioBD.getContrasena());
	}
	
	@Test
	public void getUsuarioPorHashTest() {
		UsuarioVo usuarioBD = usuarioService.getUsuarioPorHash("Sdkfsalkjfasdf");
		
		assertEquals("Iovana", usuarioBD.getNombreUsuario());
		assertEquals("iovana18@hotmail.com", usuarioBD.getEmail());
		assertEquals("111111", usuarioBD.getContrasena());
	}
	
	@Test
	public void activarUsuarioTest() {
		UsuarioVo usuario = usuarioService.getUsuarioPorNombre("Iovana");
		usuarioService.activarUsuario(usuario.getHash());
		UsuarioVo usuarioBD = usuarioService.getUsuario(usuario.getId());
		
		assertTrue(usuarioBD.isActivo());
	}
	
	@Test
	public void modificarNombreUsuarioTest() {
		UsuarioVo usuario = usuarioService.getUsuarioPorNombre("Iovana");

		usuarioService.modificarNombreUsuario("Iovi", usuario.getId());
		UsuarioVo usuarioBD = usuarioService.getUsuario(usuario.getId());
		assertEquals("Iovi", usuarioBD.getNombreUsuario());
	}
	
	@Test
	public void modificarContrasenaTest() {
		UsuarioVo usuario = usuarioService.getUsuarioPorNombre("Iovana");

		usuarioService.modificarContrasena(usuario.getId(), "222222");
		
		UsuarioVo usuarioBD = usuarioService.getUsuario(usuario.getId());
		assertEquals("222222", usuarioBD.getContrasena());
	}

	
	@Test
	public void cambiarEmailTest() {
		UsuarioVo usuario = usuarioService.getUsuarioPorNombre("Iovana");
		usuarioService.guardarEmailTemp(usuario, "iovanamartinezcorral@gmail.com");
		UsuarioVo usuarioBD = usuarioService.getUsuario(usuario.getId());
		assertEquals("iovanamartinezcorral@gmail.com", usuarioBD.getEmailTemp());
		
		usuarioService.cambiarEmail(usuario.getId());
		usuarioBD = usuarioService.getUsuario(usuario.getId());
		assertEquals("iovanamartinezcorral@gmail.com", usuarioBD.getEmail());
		assertEquals(null, usuarioBD.getEmailTemp());
	}
	
	@Test
	public void eliminarAmigoTest() {
		Integer idAmigo = 2;
		Integer idUsuario = 1;
		usuarioService.eliminarAmigo(idAmigo, idUsuario);
		Boolean esAmigo = usuarioService.esUsuarioAmigo(idUsuario, idAmigo);
		assertFalse(esAmigo);
	}
	
	@Test
	public void obtenerAmigosTest() {
		UsuarioVo usuario = usuarioService.getUsuarioPorNombre("Iovana");
		List<UsuarioVo> amigos = usuarioService.obtenerAmigos(usuario.getId());
		
		UsuarioVo amigo = amigos.get(0);
		assertEquals("Maria", amigo.getNombreUsuario());
		assertEquals("111111", amigo.getContrasena());
		assertEquals("imarcorral@yahoo.es", amigo.getEmail());
	}
	
	@Test
	public void desactivarUsuarioTest() {
		usuarioService.desactivarUsuario(1);
		
		UsuarioVo usuarioBD = usuarioService.getUsuario(1);
		
		Boolean cuentaCerrada = usuarioBD.isCuentaCerrada();
		assertTrue(cuentaCerrada);
	}
	
	@Test
	public void obtenerProximosViajesTest() {
		Integer idUsuario = 1;
		BigDecimal bote = new BigDecimal("40.00");
		Integer duracion = 5;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "2015-09-15";

		Date fecha= null;
		try {
			fecha = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<ViajeVo> viajes = usuarioService.obtenerProximosViajes(idUsuario);
		ViajeVo viaje = viajes.get(0);
		
		assertEquals("Viaje1", viaje.getNombreViaje());
		assertEquals(fecha, viaje.getFecha());
		assertEquals(duracion, viaje.getDuracion());
		assertEquals("Primer viaje con HolyPlan", viaje.getDescripcion());
		assertEquals(bote, viaje.getBote());
	}

}
