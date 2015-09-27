package es.iovanamartinez.holyplan.controlador.formulario;

import java.util.List;

import es.iovanamartinez.holyplan.dominio.vo.CheckListViajeVo;
import es.iovanamartinez.holyplan.dominio.vo.ItemListViajeVo;

public class EditarItemsViajeForm {
	//ATRIBUTOS
	private Integer id;
	private String nombre;
	private String[] itemsNuevos;
	private List<ItemListViajeVo> items;
	
	//CONTRUCTORES
	public EditarItemsViajeForm() {
		super();
	}
	
	public EditarItemsViajeForm(CheckListViajeVo checkList, List<ItemListViajeVo> items) {
		this.id = checkList.getId();
		this.nombre = checkList.getNombre();
		this.items = items;
	}

	//GETTERS AND SETTERSs
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<ItemListViajeVo> getItems() {
		return items;
	}

	public void setItems(List<ItemListViajeVo> items) {
		this.items = items;
	}

	public String[] getItemsNuevos() {
		return itemsNuevos;
	}

	public void setItemsNuevos(String[] itemsNuevos) {
		this.itemsNuevos = itemsNuevos;
	}
}
