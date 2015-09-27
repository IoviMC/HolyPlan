package es.iovanamartinez.holyplan.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dao.CheckListPersonaDao;
import es.iovanamartinez.holyplan.dao.ItemListPersonaDao;
import es.iovanamartinez.holyplan.dao.UsuarioDao;
import es.iovanamartinez.holyplan.dominio.CheckListPersona;
import es.iovanamartinez.holyplan.dominio.ItemListPersona;
import es.iovanamartinez.holyplan.dominio.Usuario;
import es.iovanamartinez.holyplan.dominio.vo.CheckListPersonaVo;
import es.iovanamartinez.holyplan.dominio.vo.ItemListPersonaVo;
import es.iovanamartinez.holyplan.service.CheckListPersonaService;

@Component
public class CheckListPersonaServiceImpl implements CheckListPersonaService {
	@Autowired
	private CheckListPersonaDao checkListPersonaDao;
	
	@Autowired
	private ItemListPersonaDao itemListPersonaDao;
	
	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	@Transactional
	public CheckListPersonaVo crearCheckListPersona(CheckListPersonaVo lista,	String[] items, Integer idUsuario) {
		CheckListPersona checkListPersona = new CheckListPersona(lista.getNombre());
		Usuario usuario = usuarioDao.buscar(idUsuario);
		
		checkListPersona.setUsuario(usuario);
		CheckListPersona checkListPersonaCreada = checkListPersonaDao.crear(checkListPersona);
		
		if(items.length != 0)
			crearItemsListPersona(checkListPersonaCreada.getId(), items);
		return new CheckListPersonaVo(checkListPersonaCreada);
	}

	@Override
	@Transactional
	public void crearItemsListPersona(Integer idCheckList, String[] items) {
		CheckListPersona checkListPersona = checkListPersonaDao.buscar(idCheckList);
		ItemListPersona item;
		for(int x = 0; x < items.length; x++){
			if(!items[x].isEmpty()){
				item = new ItemListPersona(items[x]);
				item.setCheckListPersona(checkListPersona);
				itemListPersonaDao.crear(item);
			}
		}
	}

//	private void crearItemsListPersona(Integer idCheckList,	Set<ItemListPersonaVo> items) {
//		CheckListPersona checkListPersona = checkListPersonaDao.buscar(idCheckList);
//		ItemListPersona item;
//		
//		for(ItemListPersonaVo itemVo: items){
//			item = new ItemListPersona(itemVo);
//			item.setCheckListPersona(checkListPersona);
//			itemListPersonaDao.crear(item);
//		}
//	}
	
	@Override
	@Transactional(readOnly = true)
	public CheckListPersonaVo getCheckList(Integer idCheckList) {
		CheckListPersona checkList = checkListPersonaDao.buscar(idCheckList);
		
		if (checkList == null)
			return null;
		else
			return new CheckListPersonaVo(checkList);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ItemListPersonaVo> getItemsListPersona(Integer idCheckList) {
		CheckListPersona checkList = checkListPersonaDao.buscar(idCheckList);
		
		List<ItemListPersonaVo> itemsListPersona = new ArrayList<ItemListPersonaVo>();
		for(ItemListPersona item: checkList.getItems()){
			itemsListPersona.add(new ItemListPersonaVo(item));
		}
		return itemsListPersona;
	}	

	@Override
	@Transactional(readOnly = true)
	public List<CheckListPersonaVo> getCheckListsUsuario(Integer idUsuario) {
		Usuario usuario = usuarioDao.buscar(idUsuario);
		
		List <CheckListPersonaVo> checkListsPersonaVo = new ArrayList<CheckListPersonaVo>();
		for(CheckListPersona checkListPersona: usuario.getCheckListsPersona()){
			checkListsPersonaVo.add(new CheckListPersonaVo(checkListPersona));
		}
		return checkListsPersonaVo;
	}

	@Override
	@Transactional
	public void eliminarCheckList(Integer idCheckList) {
		CheckListPersona checkListPersona = checkListPersonaDao.buscar(idCheckList);
		Usuario  usuario = checkListPersona.getUsuario();
		
		Set<CheckListPersona> checkListsPersona = usuario.getCheckListsPersona();
		checkListsPersona.remove(checkListPersona);
		
		checkListPersonaDao.eliminar(checkListPersona);
	}

	@Override
	@Transactional
	public List<ItemListPersonaVo> modificarEstadoItemsListPersona(Integer idCheckList, String[] itemsIdString) {
		CheckListPersona checkListPersona = checkListPersonaDao.buscar(idCheckList);
		List<ItemListPersonaVo> itemsVo = new ArrayList<ItemListPersonaVo>();
		
		if (itemsIdString != null){
			Set<ItemListPersona> itemsAmodificar = checkListPersona.getItems();
			
			List<ItemListPersona> itemsRealizados = new ArrayList<ItemListPersona>();
			for(int x = 0; x < itemsIdString.length; x++){
				itemsRealizados.add(itemListPersonaDao.buscar(Integer.parseInt(itemsIdString[x])));
			}
			
			for(ItemListPersona item: itemsAmodificar){
				if(itemsRealizados.contains(item))
					item.setRealizado(true);
				else
					item.setRealizado(false);
			}
			
			checkListPersona.setItems(itemsAmodificar);
			
			for(ItemListPersona item: itemsAmodificar){
				itemsVo.add(new ItemListPersonaVo(item));
			}
		}
		else {
			for(ItemListPersona item: checkListPersona.getItems()){
				itemsVo.add(new ItemListPersonaVo(item));
			}
		}
		checkListPersonaDao.actualizar(checkListPersona);
		return itemsVo;
	}

	@Override
	@Transactional
	public void eliminarItemList(Integer idItemList) {
		ItemListPersona item = itemListPersonaDao.buscar(idItemList);
		CheckListPersona checkListPersona = item.getCheckListPersona();
		
		checkListPersona.getItems().remove(item);
		itemListPersonaDao.eliminar(item);
	}
	
	@Override
	@Transactional
	public ItemListPersonaVo getItemListPersona(Integer idItemList) {
		ItemListPersona item = itemListPersonaDao.buscar(idItemList);
		if (item == null)
			return null;
		else
			return new ItemListPersonaVo(item);
	}
}
