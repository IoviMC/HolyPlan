package es.iovanamartinez.holyplan.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dominio.vo.MensajeVo;
import es.iovanamartinez.holyplan.dominio.vo.TemaVo;
import es.iovanamartinez.holyplan.service.MuroService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContextTest.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class MurosServiceTest {
	
	@Autowired
	private MuroService muroService;

	@Test
	public void crearTemaTest() {
		TemaVo tema = new TemaVo("temaPrueba");
		MensajeVo mensaje = new MensajeVo("mensajeCrearTest");
		Integer idUsuario = 1;
		Integer idViaje= 1;
		
		tema = muroService.crearTema(tema, mensaje, idViaje, idUsuario);
		tema = muroService.getTema(tema.getId());
		assertEquals("temaPrueba", tema.getTitulo());
	}
	
	@Test
	public void getTemaTest() {
		Integer idTema = 1;
		TemaVo tema = muroService.getTema(idTema);
		
		assertEquals("PrimerTema", tema.getTitulo());
	}
	
	@Test
	public void eliminarTemaTest() {
		Integer idTema = 1;
		
		muroService.eliminarTema(idTema);
		
		TemaVo tema = muroService.getTema(idTema);
		assertFalse(tema.isActivo());
	}
	
	@Test
	public void crearMensajeTest() {
		MensajeVo mensaje = new MensajeVo("mensajePrueba");
		Integer idUsuario = 1;
		Integer idTema  = 1;
		
		mensaje = muroService.crearMensaje(mensaje, idTema, idUsuario);
		assertEquals("mensajePrueba", mensaje.getDescripcion());
		assertEquals("Iovana", mensaje.getNombreUsuario());
	}
	
	@Test
	public void obtenerMensajesTest() {		
		Integer idTema = 1;
		List<MensajeVo> mensajes = muroService.obtenerMensajes(idTema);
		
		MensajeVo mensaje = mensajes.get(0);
		assertEquals("PrimerMensajeHP", mensaje.getDescripcion());
		assertEquals("Iovana", mensaje.getNombreUsuario());
	}
}
