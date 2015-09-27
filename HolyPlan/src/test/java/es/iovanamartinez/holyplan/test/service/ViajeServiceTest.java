package es.iovanamartinez.holyplan.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dominio.vo.CheckListViajeVo;
import es.iovanamartinez.holyplan.dominio.vo.PlaningVo;
import es.iovanamartinez.holyplan.dominio.vo.TemaVo;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeVo;
import es.iovanamartinez.holyplan.service.ViajeService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ViajeServiceTest {
	private static final Integer ID_ROL_CREADOR = 1;
	private static final Integer ID_ESTADO_ACEPTADO = 1;
	private static final Integer ID_ROL_INVITADO = 3;
	private static final Integer ID_ESTADO_PENDIENTE = 0;
	private static final Integer ID_ESTADO_ORGANIZADOR = 2;
	
	@Autowired
	private ViajeService viajeService;
	
	@Test
	@Rollback(true)
	public void crearViajeTest() {
		Date fecha = new Date();
	    Integer duracion = 2;
	    BigDecimal bote = new BigDecimal(0);
	    Integer idUsuario = 1;
	    
		ViajeVo viajeVo = new ViajeVo("test", fecha, duracion, "test de prueba viaje", bote);
		
		ViajeUsuarioVo viajeUsuario = viajeService.crearViaje(idUsuario, viajeVo);
		viajeUsuario = viajeService.getViajeUsuario(idUsuario, viajeUsuario.getViaje().getId());
		
		assertEquals("test", viajeUsuario.getViaje().getNombreViaje());
		assertEquals(fecha, viajeUsuario.getViaje().getFecha());
		assertEquals(duracion, viajeUsuario.getViaje().getDuracion());
		assertEquals("test de prueba viaje", viajeUsuario.getViaje().getDescripcion());
		assertEquals(bote, viajeUsuario.getViaje().getBote());
		
		assertEquals(ID_ROL_CREADOR, viajeUsuario.getRol().getId());
		assertEquals(ID_ESTADO_ACEPTADO, viajeUsuario.getEstado().getId());
		assertEquals(idUsuario, viajeUsuario.getUsuario().getId());
	}

	@Test
	public void getViajeTest() {
		BigDecimal bote = new BigDecimal("40.00");
		Integer duracion = 5;
		
		ViajeVo viaje = viajeService.getViaje(1);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "2015-09-15";

		Date fecha= null;
		try {
			fecha = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}	 

		assertEquals("Viaje1", viaje.getNombreViaje());
		assertEquals(fecha, viaje.getFecha());
		assertEquals(duracion, viaje.getDuracion());
		assertEquals("Primer viaje con HolyPlan", viaje.getDescripcion());
		assertEquals(bote, viaje.getBote());
	}
	
	@Test
	public void editarViajeTest() {
		BigDecimal bote = new BigDecimal("30.00");
		Integer duracion = 1;
		Integer idViaje = 1;
		
		ViajeVo viaje = viajeService.getViaje(idViaje);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "2015-08-15";

		Date fecha= null;
		try {
			fecha = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		viaje.seetNombreViaje("prueba");
		viaje.setBote(bote);
		viaje.setDescripcion("descripcion prueba");
		viaje.setDuracion(duracion);
		viaje.setFecha(fecha);
		viajeService.editarViaje(viaje);
		
		assertEquals("prueba", viaje.getNombreViaje());
		assertEquals(fecha, viaje.getFecha());
		assertEquals(duracion, viaje.getDuracion());
		assertEquals("descripcion prueba", viaje.getDescripcion());
		assertEquals(bote, viaje.getBote());
	}
	
	@Test
	public void andadirUsuariosTest() {
		String[] usuariosId = {"2"};
		Integer idViaje = 1;
		Integer idUsuario = 2;
		
		viajeService.andadirUsuarios(usuariosId, idViaje);
		ViajeUsuarioVo viajeUsuario = viajeService.getViajeUsuario(idUsuario, idViaje);
		
		assertEquals(idViaje, viajeUsuario.getViaje().getId());
		assertEquals(idUsuario, viajeUsuario.getUsuario().getId());
		assertEquals(ID_ROL_INVITADO, viajeUsuario.getRol().getId());
		assertEquals(ID_ESTADO_PENDIENTE, viajeUsuario.getEstado().getId());
	}
	
	@Test
	public void obtenerUsuariosRol() {
		Integer idViaje= 1;
		
		List<UsuarioVo> usuarios = viajeService.obtenerUsuariosRol(idViaje, ID_ROL_CREADOR);
		
		UsuarioVo usuario = usuarios.get(0);
		assertEquals("Iovana", usuario.getNombreUsuario());
		assertEquals("iovana18@hotmail.com", usuario.getEmail());
		assertEquals("111111", usuario.getContrasena());
	}
	
	@Test
	public void obtenerAmigosNoInvitadosTest() {
		Integer idViaje = 1;
		Integer idUsuario = 1; 
		
		List<UsuarioVo> amigos = viajeService.obtenerAmigosNoInvitados(idUsuario, idViaje);
		UsuarioVo amigo = amigos.get(0);
		
		assertEquals("Maria", amigo.getNombreUsuario());
		assertEquals("imarcorral@yahoo.es", amigo.getEmail());
		assertEquals("111111", amigo.getContrasena());
	}
	
	@Test
	public void obtenerUsuariosPorEstadoTest() {
		Integer idViaje = 1;
		
		List<UsuarioVo> aceptados = viajeService.obtenerUsuariosPorEstado(idViaje, ID_ESTADO_ACEPTADO);
		
		UsuarioVo aceptado = aceptados.get(0);
		
		assertEquals("Iovana", aceptado.getNombreUsuario());
		assertEquals("iovana18@hotmail.com", aceptado.getEmail());
		assertEquals("111111", aceptado.getContrasena());
	}
	
	@Test
	public void editarEstadoTest() {
		Integer idViaje = 2;
		Integer idUsuario = 2;

		viajeService.editarEstado(idViaje, idUsuario, ID_ESTADO_PENDIENTE);
		
		ViajeUsuarioVo viajeUsuario = viajeService.getViajeUsuario(idUsuario, idViaje);
		assertEquals(ID_ESTADO_PENDIENTE, viajeUsuario.getEstado().getId());
		
		viajeService.editarEstado(idViaje, idUsuario, ID_ESTADO_ACEPTADO);
	}

	@Test
	public void eliminarUsuarioTest() {
		Integer idViaje = 1;
		Integer idInvitado = 1;
		
		viajeService.eliminarUsuario(idInvitado, idViaje);
		
		ViajeUsuarioVo viajeUsuario = viajeService.getViajeUsuario(idInvitado, idViaje);
		assertNull(viajeUsuario);
	}
	
	@Test
	public void getViajeUsuarioTest() {
		Integer idViaje = 1;
		Integer idUsuario = 1;
		
		ViajeUsuarioVo viajeUsuario = viajeService.getViajeUsuario(idUsuario, idViaje);
		
		assertEquals(idViaje, viajeUsuario.getViaje().getId());
		assertEquals(ID_ROL_CREADOR, viajeUsuario.getRol().getId());
		assertEquals(ID_ESTADO_ACEPTADO, viajeUsuario.getEstado().getId());
		assertEquals(idUsuario, viajeUsuario.getUsuario().getId());
	}
	
	@Test
	public void obtenerViajesAntiguosTest() {
		Integer idUsuario = 1;
		Integer duracion = 3;
		
		List<ViajeVo> viajes = viajeService.obtenerViajesAntiguos(idUsuario);
		ViajeVo viaje = viajes.get(0);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "2015-04-24";

		Date fecha= null;
		try {
			fecha = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		assertEquals("ViajeAntiguo", viaje.getNombreViaje());
		assertEquals(fecha, viaje.getFecha());
		assertEquals(duracion, viaje.getDuracion());
		assertEquals("Viaje en historial", viaje.getDescripcion());
		assertNull(viaje.getBote());
	}
	
	@Test
	public void obtenerTemasTest() {
		Integer idViaje = 1;
		
		List<TemaVo> temas = viajeService.obtenerTemas(idViaje);
		
		TemaVo tema = temas.get(0);
		assertEquals("PrimerTema", tema.getTitulo());
		assertTrue(tema.isActivo());
	}
	
	@Test
	public void obtenerCheckListsTest() {
		Integer idViaje = 1;
		
		List<CheckListViajeVo> checkLists = viajeService.obtenerCheckLists(idViaje);
		
		CheckListViajeVo checkList = checkLists.get(0);
		assertEquals("PimeraCheckList", checkList.getNombre());
	}
	
	@Test
	public void cancelarViajeTest() {
		Integer idViaje = 2;
		
		viajeService.cancelarViaje(idViaje);
		
		ViajeVo viaje = viajeService.getViaje(idViaje);
		assertTrue(viaje.isCancelado());
	}
	
	@Test
	public void obtenerUsuariosTest() {
		Integer idViaje = 1;
		
		List<UsuarioVo> usuarios = viajeService.obtenerUsuarios(idViaje);
		
		UsuarioVo usuario = usuarios.get(0);
		assertEquals("Iovana", usuario.getNombreUsuario());
		assertEquals("iovana18@hotmail.com", usuario.getEmail());
		assertEquals("111111", usuario.getContrasena());
	}
	
	@Test
	public void getPlaningsTest() {
		Integer idViaje = 1;
		
		List<PlaningVo> planings = viajeService.getPlanings(idViaje);
		
		PlaningVo planing = planings.get(0);
		assertEquals("PrimerPlaning", planing.getNombre());
		assertEquals("Primer planing del primer viaje con HolyPlan", planing.getDescripcion());
	}
	
	@Test
	public void anadirOrganizadorTest() {
		Integer idViaje = 2;
		Integer idOrganizador = 2;
		
		viajeService.anadirOrganizador(idOrganizador, idViaje);
		
		ViajeUsuarioVo viajeUsuario = viajeService.getViajeUsuario(idOrganizador, idViaje);
		assertEquals(ID_ESTADO_ORGANIZADOR, viajeUsuario.getRol().getId());
	}
	
	@Test
	public void anadirUsuarioTest() {
		Integer idViaje = 1;
		Integer idUsuario = 2;
		String nombreUsuario = "Maria";
		
		viajeService.anadirUsuario(nombreUsuario, idViaje);
		
		ViajeUsuarioVo viajeUsuario = viajeService.getViajeUsuario(idUsuario, idViaje);
		assertEquals(idViaje, viajeUsuario.getViaje().getId());
		assertEquals(ID_ROL_INVITADO, viajeUsuario.getRol().getId());
		assertEquals(ID_ESTADO_PENDIENTE, viajeUsuario.getEstado().getId());
		assertEquals(idUsuario, viajeUsuario.getUsuario().getId());
	}
}
