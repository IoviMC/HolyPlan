package es.iovanamartinez.holyplan.test.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dominio.vo.CheckListViajeVo;
import es.iovanamartinez.holyplan.dominio.vo.ItemListViajeVo;
import es.iovanamartinez.holyplan.service.CheckListViajeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContextTest.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class CheckListViajeServiceTest {

	@Autowired
	private CheckListViajeService checkListViajeService;
	
	@Test
	public void crearCheckListViajeTest() {
		Integer idViaje = 1;
		String nombreLista = "prueba test";
		String[] items = {};
		
		CheckListViajeVo lista = new CheckListViajeVo(nombreLista);	
		lista = checkListViajeService.crearCheckListViaje(lista, items, idViaje);
		lista = checkListViajeService.getCheckList(lista.getId());
		assertEquals("prueba test", lista.getNombre());
	}
	
	@Test
	public void getCheckListTest() {
		Integer idCheckList = 1;
		
		CheckListViajeVo lista = checkListViajeService.getCheckList(idCheckList);
		assertEquals("PimeraCheckList", lista.getNombre());
	}
	
	@Test
	public void getItemsListViajeTest() {
		Integer idCheckList = 1;
		List<ItemListViajeVo>  items = checkListViajeService.getItemsListViaje(idCheckList);
		
		ItemListViajeVo item = items.get(0);
		assertEquals("primer item lista", item.getDescripcion());
		assertFalse(item.isRealizado());
	}
	
	@Test
	public void modificarEstadoItemsListViajeTest() {
		Integer idCheckList = 1;
		String[] itemsIdString = {"1"};
		Integer idUsuario = 1;
		List<ItemListViajeVo> items = checkListViajeService.modificarEstadoItemsListViaje(idCheckList, itemsIdString, idUsuario);
		
		ItemListViajeVo item = items.get(0);
		assertTrue(item.isRealizado());
	}
	
	@Test
	public void eliminarCheckListTest() {
		Integer idCheckList = 1;
		checkListViajeService.eliminarCheckList(idCheckList);
		
		CheckListViajeVo lista = checkListViajeService.getCheckList(idCheckList);
		assertNull(lista);
	}
	
	@Test
	public void eliminarItemListTest() {
		Integer idItemList = 1;
		checkListViajeService.eliminarItemList(idItemList);
		
		ItemListViajeVo item = checkListViajeService.getItemListViaje(idItemList);
		assertNull(item);
	}
}
