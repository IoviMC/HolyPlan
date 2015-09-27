package es.iovanamartinez.holyplan.service;

import java.util.List;

import es.iovanamartinez.holyplan.dominio.vo.CheckListPersonaVo;
import es.iovanamartinez.holyplan.dominio.vo.ItemListPersonaVo;

public interface CheckListPersonaService {

	CheckListPersonaVo crearCheckListPersona(CheckListPersonaVo lista, String[] items, Integer idUsuario);

	List<CheckListPersonaVo> getCheckListsUsuario(Integer idUsuario);

	void eliminarCheckList(Integer idCheckList);

	CheckListPersonaVo getCheckList(Integer idCheckList);

	List<ItemListPersonaVo> getItemsListPersona(Integer idCheckList);

	List<ItemListPersonaVo> modificarEstadoItemsListPersona(Integer idCheckList, String[] itemsIdString);

	void crearItemsListPersona(Integer idCheckList, String[] items);

	void eliminarItemList(Integer idItemList);

	ItemListPersonaVo getItemListPersona(Integer idItemList);
}
