package es.iovanamartinez.holyplan.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dao.GenericDao;
import es.iovanamartinez.holyplan.dominio.Viaje;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class GenericDaoTest {
	
	@Autowired
	private GenericDao<Viaje> genericDao;

	@Test
	public void crearTest() {
		String nombreViaje = "viajeDaoTest";
		Date fecha = new Date();
		Integer duracion = 2;
		String descripcion = "prueba de unidad";
		BigDecimal bote = new BigDecimal(10);
		
		Viaje viaje = new Viaje(nombreViaje, fecha, duracion, descripcion, bote);
		viaje = genericDao.crear(viaje);
		Viaje viajeDB = genericDao.buscar(viaje.getId());
		
		assertEquals(viaje, viajeDB);
	}
	
	@Test
	public void buscarTest() {
		Integer idViaje = 1;
		String nombreViaje = "Viaje1";
		Integer duracion = 5;
		String descripcion = "Primer viaje con HolyPlan";
		BigDecimal bote = new BigDecimal("40.00");	
		
		Viaje viaje = genericDao.buscar(idViaje);
		
		assertEquals(viaje.getId(), idViaje);
		assertEquals(viaje.getNombreViaje(), nombreViaje);
		assertEquals(viaje.getDuracion(), duracion);
		assertEquals(viaje.getDescripcion(), descripcion);
		assertEquals(viaje.getBote(), bote);
	}
	
	@Test
	public void eliminarTest() {
		Integer idViaje = 1;
		Viaje viaje = genericDao.buscar(idViaje);
		genericDao.eliminar(viaje);
		viaje = genericDao.buscar(idViaje);
		
		assertNull(viaje);
	}
	
	@Test
	public void actualizarTest() {
		Integer idViaje = 1;
		Viaje viaje = genericDao.buscar(idViaje);
		viaje.setNombreViaje("ViajeModi");
		viaje.setDescripcion("Viaje actulizado en Dao");
		
		genericDao.actualizar(viaje);
		
		viaje = genericDao.buscar(idViaje);
		assertEquals(viaje.getId(), idViaje);
		assertEquals(viaje.getNombreViaje(), "ViajeModi");
		assertEquals(viaje.getDescripcion(), "Viaje actulizado en Dao");
	}
}
