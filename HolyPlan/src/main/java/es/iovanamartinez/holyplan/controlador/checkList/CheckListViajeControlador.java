package es.iovanamartinez.holyplan.controlador.checkList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iovanamartinez.holyplan.controlador.formulario.CrearCheckListForm;
import es.iovanamartinez.holyplan.controlador.formulario.EditarCheckListViajeForm;
import es.iovanamartinez.holyplan.controlador.formulario.EditarItemsViajeForm;
import es.iovanamartinez.holyplan.dominio.vo.CheckListViajeVo;
import es.iovanamartinez.holyplan.dominio.vo.ItemListViajeVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.service.CheckListViajeService;

@Controller
public class CheckListViajeControlador {
	@Autowired
	private CheckListViajeService checkListViajeService;
	
	private static final int ID_ROL_INVITADO = 3;
	
	@RequestMapping(value = "/checkListViaje/crearCheckList", method = RequestMethod.GET)
	public String crearCheckLists(ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");

		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
		else {
			CrearCheckListForm crearCheckListViajeForm = new CrearCheckListForm();
			
			model.addAttribute("crearCheckListViajeForm", crearCheckListViajeForm);
			model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
			model.addAttribute("idRol", viajeUsuario.getRol().getId());			
			model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
			return "/private/checklist/crear-checklist-viaje";
		}
	}
	
	@RequestMapping(value = "/checkListViaje/crearCheckList", method = RequestMethod.POST)
	public String crearCheckList(@ModelAttribute CrearCheckListForm crearCheckListViajeForm, HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");

		CheckListViajeVo lista = new CheckListViajeVo(crearCheckListViajeForm.getNombre());
		String[] items = crearCheckListViajeForm.getItems();
		
		checkListViajeService.crearCheckListViaje(lista, items, viajeUsuario.getViaje().getId());
	
		model.addAttribute("crearCheckListViajeForm", crearCheckListViajeForm);
		model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
		model.addAttribute("idRol", viajeUsuario.getRol().getId());
		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
		model.addAttribute("mensajeConfirmacion", "Lista creada con &eacute;xito");
		return "/private/checklist/crear-checklist-viaje";
	}
	
	@RequestMapping(value = "/checkListViaje/modificarCheckList/{id}", method = RequestMethod.GET)
	public String modificarCheckList(@PathVariable("id") final Integer idCheckList, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
			
		CheckListViajeVo checkListViaje = checkListViajeService.getCheckList(idCheckList);
		List<ItemListViajeVo> items = checkListViajeService.getItemsListViaje(idCheckList);
		
		EditarCheckListViajeForm editarCheckListViajeForm = new EditarCheckListViajeForm(checkListViaje, items);
		
		model.addAttribute("modificarCheckListViajeForm", editarCheckListViajeForm);
		model.addAttribute("idRol", viajeUsuario.getRol().getId());
		model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
		return "/private/checklist/editar-checklist-viaje";
	}
	
	@RequestMapping(value = "/checkListViaje/modificarCheckListViaje", method = RequestMethod.POST)
	public String modificarCheckList(@ModelAttribute EditarCheckListViajeForm editarCheckListViajeForm, HttpServletRequest request, ModelMap model){
		String[] itemsIdString = request.getParameterValues("itemsId");
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		List<ItemListViajeVo> items = checkListViajeService.modificarEstadoItemsListViaje(editarCheckListViajeForm.getId(), itemsIdString, viajeUsuario.getUsuario().getId());
		editarCheckListViajeForm.setItems(items);
		
		model.addAttribute("modificarCheckListViajeForm", editarCheckListViajeForm);
		model.addAttribute("idRol", viajeUsuario.getRol().getId());
		model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
//		model.addAttribute("mensajeConfirmacion", "Cambios guardados con &eacute;xito");
//		return "/private/checklist/editar-checklist-viaje";
		return "redirect:/checkListViaje/modificarCheckList/" + editarCheckListViajeForm.getId();
	}
	
	
	@RequestMapping(value = "/checkListViaje/editarItemsViaje/{id}", method = RequestMethod.GET)
	public String editarItemsForm(@PathVariable("id") final Integer idCheckList, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if(viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) != 0){
			CheckListViajeVo checkList = checkListViajeService.getCheckList(idCheckList);
			List<ItemListViajeVo> items = checkListViajeService.getItemsListViaje(idCheckList);
			
			EditarItemsViajeForm editarItemsViajeForm = new EditarItemsViajeForm(checkList, items);

			model.addAttribute("editarItemsViajeForm", editarItemsViajeForm);
			model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
			model.addAttribute("idRol", viajeUsuario.getRol().getId());
			model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
			return "/private/checklist/editar-items-list-viaje";
		}
		else {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
	}
	
	@RequestMapping(value = "/checkListViaje/editarItemsViaje", method = RequestMethod.POST)
	public String crearItems(@ModelAttribute EditarItemsViajeForm editarItemsViajeForm){;
		checkListViajeService.crearItemsListViaje(editarItemsViajeForm.getId(), editarItemsViajeForm.getItemsNuevos());
		
		return "redirect:/checkListViaje/editarItemsViaje/" + editarItemsViajeForm.getId();
	}
	
	@RequestMapping(value = "/checkListViaje/eliminarCheckList/{id}", method = RequestMethod.GET)
	public String eliminarCheckList(@PathVariable("id") final Integer idCheckList, HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
		else { 
			checkListViajeService.eliminarCheckList(idCheckList);
			return "redirect:/viaje/mostrarViaje/" + viajeUsuario.getViaje().getId();
		}
	}
	
	@RequestMapping(value = "/checkListViaje/eliminarItemList/{idItem}/{idCheckList}", method = RequestMethod.GET)
	public String eliminarItemList(@PathVariable("idItem") final Integer idItemList, 
				@PathVariable("idCheckList") final Integer idCheckList, HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
		else {
			checkListViajeService.eliminarItemList(idItemList);
			return "redirect:/checkListViaje/editarItemsViaje/" + idCheckList;
		}
	}
}
