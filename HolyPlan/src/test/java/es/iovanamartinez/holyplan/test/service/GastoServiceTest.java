package es.iovanamartinez.holyplan.test.service;

import static org.junit.Assert.*;

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

import es.iovanamartinez.holyplan.dominio.vo.GastoColectivoVo;
import es.iovanamartinez.holyplan.dominio.vo.GastoIndividualVo;
import es.iovanamartinez.holyplan.service.GastoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContextTest.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class GastoServiceTest {
	
	@Autowired
	private GastoService gastoService;
	
	@Test
	public void crearGastoColectivosTest() {
		String concepto = "gastoColectivoTest";
		Integer idViaje = 1;
		BigDecimal importe = new BigDecimal(0);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "2015-09-15";

		Date fecha= null;
		try {
			fecha = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		GastoColectivoVo gasto = new GastoColectivoVo(concepto, importe, fecha, false);

		gasto = gastoService.crearGastoColectivo(gasto, idViaje);
		gastoService.getGastoColectivo(gasto.getId());
		assertEquals(concepto, gasto.getConcepto());
		assertEquals(fecha, gasto.getFecha());
		assertEquals(importe, gasto.getImporte());
	}
	
	@Test
	public void obtenerGastosColectivosTest() {
		Integer idViaje = 1;
		BigDecimal importe = new BigDecimal("40.00");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "2015-09-15";

		Date fecha= null;
		try {
			fecha = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<GastoColectivoVo> gastos = gastoService.obtenerGastosColectivos(idViaje);
		GastoColectivoVo gasto = gastos.get(0);
		assertEquals("PrimerGastoColectivo", gasto.getConcepto());
		assertEquals(importe, gasto.getImporte());
		assertEquals(fecha, gasto.getFecha());
		assertFalse(gasto.isPagoBote());
	}
	
	@Test
	public void eliminarGastoColectivoTest() {
		Integer idGasto = 1;
		gastoService.eliminarGastoColectivo(idGasto);
		
		GastoColectivoVo gasto = gastoService.getGastoColectivo(idGasto);
		assertNull(gasto);
	}
	
	@Test
	public void crearGastoIndividualTest() {
		String concepto = "gastoIndividualTest";
		Integer idViaje = 1;
		Integer idUsuario = 1;
		BigDecimal importe = new BigDecimal(0);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "2015-09-15";

		Date fecha= null;
		try {
			fecha = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		GastoIndividualVo gasto = new GastoIndividualVo(concepto, importe, fecha);

		gasto = gastoService.crearGastoIndividual(gasto, idViaje, idUsuario);
		gasto = gastoService.getGastoIndividual(gasto.getId());
		assertEquals(concepto, gasto.getConcepto());
		assertEquals(fecha, gasto.getFecha());
		assertEquals(importe, gasto.getImporte());
	}

	@Test
	public void obtenerGastosIndividualesTest() {
		Integer idViaje = 1;
		Integer idUsuario = 1;
		BigDecimal importe = new BigDecimal("40.00");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "2015-09-15";

		Date fecha= null;
		try {
			fecha = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<GastoIndividualVo> gastos = gastoService.obtenerGastosIndividuales(idViaje, idUsuario);
		GastoIndividualVo gasto = gastos.get(0);
		assertEquals("PrimerGastoIndividual", gasto.getConcepto());
		assertEquals(importe, gasto.getImporte());
		assertEquals(fecha, gasto.getFecha());
	}
	
	@Test
	public void eliminarGastoIndividualTest() {
		Integer idGasto = 1;
		gastoService.eliminarGastoIndividual(idGasto);
		
		GastoIndividualVo gasto = gastoService.getGastoIndividual(idGasto);
		assertNull(gasto);
	}
	
	@Test
	public void getGastoColectivoTest() {
		Integer idGasto = 1;
		BigDecimal importe = new BigDecimal("40.00");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "2015-09-15";

		Date fecha= null;
		try {
			fecha = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		GastoColectivoVo gasto = gastoService.getGastoColectivo(idGasto);
		
		assertEquals("PrimerGastoColectivo", gasto.getConcepto());
		assertEquals(importe, gasto.getImporte());
		assertEquals(fecha, gasto.getFecha());
		assertFalse(gasto.isPagoBote());
	}
	
	@Test
	public void getGastoIndividualTest() {
		Integer idGasto = 1;
		BigDecimal importe = new BigDecimal("40.00");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "2015-09-15";

		Date fecha= null;
		try {
			fecha = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		GastoIndividualVo gasto = gastoService.getGastoIndividual(idGasto);
		
		assertEquals("PrimerGastoIndividual", gasto.getConcepto());
		assertEquals(importe, gasto.getImporte());
		assertEquals(fecha, gasto.getFecha());
	}
	
	
	@Test
	public void editarGastoColectivoTest() {
		Integer idGasto = 1;
		BigDecimal importe = new BigDecimal("35");
		GastoColectivoVo gasto = gastoService.getGastoColectivo(idGasto);
		gasto.setConcepto("gasto modificado test");
		gasto.setImporte(importe);
		gasto.setPagoBote(true);
		
		gasto = gastoService.editarGastoColectivo(gasto);
		gasto = gastoService.getGastoColectivo(gasto.getId());
		assertEquals("gasto modificado test", gasto.getConcepto());
		assertEquals(importe, gasto.getImporte());
		assertTrue(gasto.isPagoBote());
	}
	
	@Test
	public void editarGastoIndividualTest() {
		Integer idGasto = 1;
		BigDecimal importe = new BigDecimal("35");
		
		GastoIndividualVo gasto = gastoService.getGastoIndividual(idGasto);
		gasto.setConcepto("gasto modificado test");
		gasto.setImporte(importe);
		
		gastoService.editarGastoIndividual(gasto);
		gasto = gastoService.getGastoIndividual(idGasto);
		assertEquals("gasto modificado test", gasto.getConcepto());
		assertEquals(importe, gasto.getImporte());		
	}
	
	@Test
	public void obtenerGastoTotalPorPersonaTest() {
		Integer idViaje = 1;
		BigDecimal gastoEsperado = new BigDecimal("40.00");
		
		BigDecimal gastoTotalPorPersona = gastoService.obtenerGastoTotalPorPersona(idViaje);
		assertEquals(gastoEsperado, gastoTotalPorPersona);
	}
	
	@Test
	public void obtenerGastosPagoBoteTest() {
		Integer idViaje = 1;
		BigDecimal gastoEsperado = new BigDecimal(0);
		
		BigDecimal gastoPagoBote = gastoService.obtenerGastosPagoBote(idViaje);
		assertEquals(gastoEsperado, gastoPagoBote);
	}
}
