package es.iovanamartinez.holyplan.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

import es.iovanamartinez.holyplan.dominio.vo.ParadaVo;
import es.iovanamartinez.holyplan.dominio.vo.PlaningVo;
import es.iovanamartinez.holyplan.service.PlaningService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContextTest.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class PlaningServiceTest {
	
	@Autowired
	private PlaningService planingService;
	
	@Test
	public void crearPlaningTest() {
		String nombre = "pruebaTest";
		String descripcion = "esto es una prueba para crear un test";
		Integer idViaje = 1;
		
		PlaningVo planing = planingService.crearPlaning(nombre, descripcion, idViaje);
		planing = planingService.getPlaning(planing.getId());
		assertEquals("pruebaTest", planing.getNombre());
		assertEquals("esto es una prueba para crear un test", planing.getDescripcion());
	}
	
	@Test
	public void getPlaningTest() {
		Integer idPlaning = 1;
		
		PlaningVo planing = planingService.getPlaning(idPlaning);
		assertEquals("PrimerPlaning", planing.getNombre());
		assertEquals("Primer planing del primer viaje con HolyPlan", planing.getDescripcion());
	}
	

	@Test
	public void editarPlaningTest() {
		Integer idPlaning = 1;
		String nombre = "planingEditado";
		String descripcion = "Editado en el test";
		
		planingService.editarPlaning(idPlaning, nombre, descripcion);
		
		PlaningVo planingEditado = planingService.getPlaning(idPlaning);
		assertEquals(nombre, planingEditado.getNombre());
		assertEquals(descripcion, planingEditado.getDescripcion());
	}
	
	@Test
	public void eliminarPlaningTest() {
		Integer idPlaning = 1;
		Integer idViaje = 1;
		
		planingService.eliminarPlaning(idPlaning, idViaje);
		
		PlaningVo planing = planingService.getPlaning(idPlaning);
		assertNull(planing);
	}
	
	@Test
	public void getParadasTest() {
		//TODO a veces FALLA AL INSERTAR NUEVAS PARADAS
		Integer idPlaning = 1;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "2015-09-15";

		Date fecha= null;
		try {
			fecha = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<ParadaVo> paradas = planingService.getParadas(idPlaning);
		ParadaVo parada = paradas.get(0);
		
		assertEquals("primeraParada", parada.getNombre());
		assertEquals("Arteixo", parada.getLugar());
		assertEquals(fecha, parada.getFecha());
	}
}
