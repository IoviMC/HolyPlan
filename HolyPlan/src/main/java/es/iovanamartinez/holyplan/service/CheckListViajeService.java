package es.iovanamartinez.holyplan.service;

import java.util.List;

import es.iovanamartinez.holyplan.dominio.vo.CheckListViajeVo;
import es.iovanamartinez.holyplan.dominio.vo.ItemListViajeVo;

public interface CheckListViajeService {
	
	CheckListViajeVo crearCheckListViaje(CheckListViajeVo lista, String[] items, Integer idViaje);

	void crearItemsListViaje(Integer idCheckList, String[] items);

	void eliminarCheckList(Integer idCheckList);

	CheckListViajeVo getCheckList(Integer idCheckList);

	List<ItemListViajeVo> getItemsListViaje(Integer idCheckList);

//	List<ItemListViajeVo> modificarCheckListViaje(Integer idCheckList, String nombre, String[] itemsIdString, Integer idUsuario);
	List<ItemListViajeVo> modificarEstadoItemsListViaje(Integer idCheckList, String[] itemsIdString, Integer idUsuario);

	void eliminarItemList(Integer idItemList);

	ItemListViajeVo getItemListViaje(Integer idItem);
}
