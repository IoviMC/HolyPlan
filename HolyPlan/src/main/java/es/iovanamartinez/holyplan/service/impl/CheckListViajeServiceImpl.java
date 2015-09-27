package es.iovanamartinez.holyplan.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dao.CheckListViajeDao;
import es.iovanamartinez.holyplan.dao.ItemListViajeDao;
import es.iovanamartinez.holyplan.dao.UsuarioDao;
import es.iovanamartinez.holyplan.dao.ViajeDao;
import es.iovanamartinez.holyplan.dominio.CheckListViaje;
import es.iovanamartinez.holyplan.dominio.ItemListViaje;
import es.iovanamartinez.holyplan.dominio.Usuario;
import es.iovanamartinez.holyplan.dominio.Viaje;
import es.iovanamartinez.holyplan.dominio.vo.CheckListViajeVo;
import es.iovanamartinez.holyplan.dominio.vo.ItemListViajeVo;
import es.iovanamartinez.holyplan.service.CheckListViajeService;

@Component
public class CheckListViajeServiceImpl implements CheckListViajeService {
	@Autowired
	ViajeDao viajeDao;
	
	@Autowired
	UsuarioDao usuarioDao;
	
	@Autowired
	CheckListViajeDao checkListViajeDao;
	
	@Autowired
	ItemListViajeDao itemListViajeDao;
	
	@Override
	@Transactional
	public CheckListViajeVo crearCheckListViaje(CheckListViajeVo lista, String[] items, Integer idViaje) {
		CheckListViaje checkListViaje = new CheckListViaje(lista.getNombre());
		Viaje viaje = viajeDao.buscar(idViaje);
	
		checkListViaje.setViaje(viaje);
		CheckListViaje checkListViajeCreada = checkListViajeDao.crear(checkListViaje);
		
		if(items.length != 0)
			crearItemsListViaje(checkListViajeCreada.getId(), items);
		
		return new CheckListViajeVo(checkListViajeCreada);
	}

	@Override
	@Transactional
	public void crearItemsListViaje(Integer idCheckList, String[] items) {
		CheckListViaje checkListViaje = checkListViajeDao.buscar(idCheckList);
		ItemListViaje item;
		for(int x = 0; x < items.length; x++){
			if(!items[x].isEmpty()){
				item = new ItemListViaje(items[x]);
				item.setCheckListViaje(checkListViaje);
				itemListViajeDao.crear(item);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public CheckListViajeVo getCheckList(Integer idCheckList) {
		CheckListViaje checkList = checkListViajeDao.buscar(idCheckList);
		
		if (checkList == null)
			return null;
		else
			return new CheckListViajeVo(checkList);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemListViajeVo> getItemsListViaje(Integer idCheckList) {
		CheckListViaje checkList = checkListViajeDao.buscar(idCheckList);
		ItemListViajeVo itemVo; 
		
		List<ItemListViajeVo> itemsListViaje = new ArrayList<ItemListViajeVo>();
		for(ItemListViaje item: checkList.getItems()){
			itemVo = new ItemListViajeVo(item);
			if (item.getUsuario() != null)
				itemVo.setNombreUsuario(item.getUsuario().getNombreUsuario());
			
			itemsListViaje.add(itemVo);
		}
		return itemsListViaje;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ItemListViajeVo getItemListViaje(Integer idItem) {
		ItemListViaje item = itemListViajeDao.buscar(idItem);
		
		if (item == null)
			return null;
		else
			return new ItemListViajeVo();
	}

	@Override
	@Transactional
	public List<ItemListViajeVo> modificarEstadoItemsListViaje(Integer idCheckList, String[] itemsIdString, Integer idUsuario) {
		CheckListViaje checkListViaje = checkListViajeDao.buscar(idCheckList);
		Usuario usuario = usuarioDao.buscar(idUsuario);
		
		List<ItemListViajeVo> itemsVo = new ArrayList<ItemListViajeVo>();
		if (itemsIdString != null){
			List<ItemListViaje> itemsRealizados = new ArrayList<ItemListViaje>();
			for(int x = 0; x < itemsIdString.length; x++){
				itemsRealizados.add(itemListViajeDao.buscar(Integer.parseInt(itemsIdString[x])));
			}
			
			Set<ItemListViaje> itemsAmodificar = checkListViaje.getItems();
			for(ItemListViaje item: itemsAmodificar){
				if(itemsRealizados.contains(item)) {
					item.setRealizado(true);
					if (item.getUsuario() == null)
						item.setUsuario(usuario);
				}
				else {
					item.setRealizado(false);
					item.setUsuario(null);
				}
				itemsVo.add(new ItemListViajeVo(item));
			}
			
			checkListViaje.setItems(itemsAmodificar);
		}
		checkListViajeDao.actualizar(checkListViaje);
		return itemsVo;
	}
	
	@Override
	@Transactional
	public void eliminarCheckList(Integer idCheckList) {
		CheckListViaje checkList = checkListViajeDao.buscar(idCheckList);
		Viaje viaje = checkList.getViaje();
		
		viaje.getCheckLists().remove(checkList);
		checkListViajeDao.eliminar(checkList);
	}
	
	@Override
	@Transactional
	public void eliminarItemList(Integer idItemList) {
		ItemListViaje item = itemListViajeDao.buscar(idItemList);
		CheckListViaje checkListViaje = item.getCheckListViaje();
		
		checkListViaje.getItems().remove(item);
		itemListViajeDao.eliminar(item);
	}
}
