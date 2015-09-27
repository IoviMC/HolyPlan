package es.iovanamartinez.holyplan.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dominio.vo.ParadaVo;
import es.iovanamartinez.holyplan.service.ParadaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContextTest.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ParadaServiceTest {
	
	@Autowired
	private ParadaService paradaService;
	
	@Test
	public void crearParadaTest() {
		String nombre = "prueba";
		String lugar = "lugarPrueba";
		Integer idPlaning = 1;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "2015-09-15";

		Date fecha= null;
		try {
			fecha = formatter.parse(dateInString);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}	 
		
		ParadaVo parada = paradaService.crearParada(nombre, lugar, fecha, idPlaning);
		parada = paradaService.getParada(parada.getId());
		assertEquals("prueba", parada.getNombre());
		assertEquals("lugarPrueba", parada.getLugar());
		assertEquals(fecha, parada.getFecha());
	}
	
	@Test
	public void eliminarParadaTest() {
		Integer idParada = 1;
		
		paradaService.eliminarParada(idParada);
		
		ParadaVo parada = paradaService.getParada(idParada);
		assertNull(parada);
	}
}
