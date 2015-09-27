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

import es.iovanamartinez.holyplan.dominio.vo.CheckListPersonaVo;
import es.iovanamartinez.holyplan.dominio.vo.ItemListPersonaVo;
import es.iovanamartinez.holyplan.service.CheckListPersonaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContextTest.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class CheckListPersonaServiceTest {

	@Autowired
	private CheckListPersonaService checkListPersonaService;
	
	@Test
	public void crearCheckListPersonaTest() {
		Integer idUsuario = 1;
		String nombreLista = "prueba test";
		String[] items = {};
		
		CheckListPersonaVo lista = new CheckListPersonaVo(nombreLista);
		lista = checkListPersonaService.crearCheckListPersona(lista, items, idUsuario);
		lista = checkListPersonaService.getCheckList(lista.getId());
		assertEquals("prueba test", lista.getNombre());
	}
	
	@Test
	public void getCheckListTest() {
		Integer idCheckList = 1;
		
		CheckListPersonaVo lista = checkListPersonaService.getCheckList(idCheckList);
		assertEquals("PrimeraCheckList", lista.getNombre());
	}
	
	@Test
	public void getItemsListPersonaTest() {
		Integer idCheckList = 1;
		List<ItemListPersonaVo>  items = checkListPersonaService.getItemsListPersona(idCheckList);
		
		ItemListPersonaVo item = items.get(0);
		assertEquals("primerItem", item.getDescripcion());
		assertFalse(item.isRealizado());
	}
	
	@Test
	public void modificarEstadoItemsListPersonaTest() {
		Integer idCheckList = 1;
		String[] itemsIdString = {"1"};
		checkListPersonaService.modificarEstadoItemsListPersona(idCheckList, itemsIdString);
		
		ItemListPersonaVo item = checkListPersonaService.getItemListPersona(1);
		assertTrue(item.isRealizado());
	}
	
	@Test
	public void eliminarCheckListTest() {
		Integer idCheckList = 1;
		checkListPersonaService.eliminarCheckList(idCheckList);
		
		CheckListPersonaVo lista = checkListPersonaService.getCheckList(idCheckList);
		assertNull(lista);
	}
	
	@Test
	public void eliminarItemListTest() {
		Integer idItemList = 1;
		checkListPersonaService.eliminarItemList(idItemList);
		
		ItemListPersonaVo item = checkListPersonaService.getItemListPersona(idItemList);
		assertNull(item);
	}
}
