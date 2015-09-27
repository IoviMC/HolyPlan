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
import es.iovanamartinez.holyplan.controlador.formulario.EditarItemsPersonaForm;
import es.iovanamartinez.holyplan.controlador.formulario.ModificarCheckListPersonaForm;
import es.iovanamartinez.holyplan.dominio.vo.CheckListPersonaVo;
import es.iovanamartinez.holyplan.dominio.vo.ItemListPersonaVo;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.service.CheckListPersonaService;

@Controller
public class CheckListPersonaControlador {
	@Autowired
	private CheckListPersonaService checkListPersonaService;
	
	@RequestMapping(value = "/checkListPersona/checkLists", method = RequestMethod.GET)
	public String mostrarCheckLists(ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		List<CheckListPersonaVo> listas = checkListPersonaService.getCheckListsUsuario(usuario.getId());
		CrearCheckListForm crearCheckListPersonaForm = new CrearCheckListForm();
		
		model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
		model.addAttribute("crearCheckListPersonaForm", crearCheckListPersonaForm);
		model.addAttribute("listasPersona", listas);
		return "/private/checklist/checklist-persona";
	}
	
//	@RequestMapping(value = "/checkListPersona/crearCheckList", method = RequestMethod.GET)
//	public String mostrarFormLista(ModelMap model) {
//		CrearCheckListPersonaForm crearListaPersonaForm = new CrearCheckListPersonaForm();
//		
//		model.addAttribute("crearListaPersonaForm", crearListaPersonaForm);
//		return "/private/checklist/crear-checklist-persona";
//	}

	@RequestMapping(value = "/checkListPersona/crearCheckList", method = RequestMethod.POST)
	public String crearCheckList(@ModelAttribute CrearCheckListForm crearCheckListPersonaForm, HttpServletRequest request){
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");

		CheckListPersonaVo lista = new CheckListPersonaVo(crearCheckListPersonaForm.getNombre());
		String[] items = crearCheckListPersonaForm.getItems();
		
		checkListPersonaService.crearCheckListPersona(lista, items, usuario.getId());
	
		return "redirect:/checkListPersona/checkLists";
	}
	
	@RequestMapping(value = "/checkListPersona/mostrarCheckList/{id}", method = RequestMethod.GET)
	public String mostrarCheckList(@PathVariable("id") final Integer idCheckList, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		CheckListPersonaVo checkListPersona = checkListPersonaService.getCheckList(idCheckList);
		List<ItemListPersonaVo> items = checkListPersonaService.getItemsListPersona(idCheckList);
		
		ModificarCheckListPersonaForm editarItemsPersonaForm = new ModificarCheckListPersonaForm(checkListPersona, items);
	
		model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
		model.addAttribute("editarItemsPersonaForm", editarItemsPersonaForm);
		model.addAttribute("nombreCheckList", checkListPersona.getNombre());
		model.addAttribute("idCheckList", checkListPersona.getId());
		return "/private/checklist/mostrar-checklist-persona";
	}
	
	@RequestMapping(value = "/checkListPersona/modificarCheckListPersona", method = RequestMethod.POST)
	public String modificarCheckList(@ModelAttribute ModificarCheckListPersonaForm editarItemsPersonaForm, 
			HttpServletRequest request, ModelMap model){
		String[] itemsIdString = request.getParameterValues("itemsId");
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		List<ItemListPersonaVo> items = checkListPersonaService.modificarEstadoItemsListPersona(editarItemsPersonaForm.getId(), itemsIdString);
		editarItemsPersonaForm.setItems(items);
		
		model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
		model.addAttribute("editarItemsPersonaForm", editarItemsPersonaForm);
		model.addAttribute("nombreCheckList", editarItemsPersonaForm.getNombre());
		model.addAttribute("idCheckList", editarItemsPersonaForm.getId());
		model.addAttribute("mensajeConfirmacion", "Cambios guardados con &eacute;xito");
		return "private/checklist/mostrar-checklist-persona";
	}
	
	@RequestMapping(value = "/checkListPersona/editarItemsPersona/{id}", method = RequestMethod.GET)
	public String editarItemsForm(@PathVariable("id") final Integer idCheckList, ModelMap model){
		CheckListPersonaVo checkList = checkListPersonaService.getCheckList(idCheckList);
		List<ItemListPersonaVo> items = checkListPersonaService.getItemsListPersona(idCheckList);
		
		EditarItemsPersonaForm modificarCheckListPersonaForm = new EditarItemsPersonaForm(checkList, items);
		
		model.addAttribute("modificarCheckListPersonaForm", modificarCheckListPersonaForm);
		return "/private/checklist/editar-items-list-persona";
	}
	
	@RequestMapping(value = "/checkListPersona/editarItemsPersona", method = RequestMethod.POST)
	public String crearItems(@ModelAttribute EditarItemsPersonaForm modificarCheckListPersonaForm){
		checkListPersonaService.crearItemsListPersona(modificarCheckListPersonaForm.getId(), modificarCheckListPersonaForm.getItemsNuevos());
		
		return "redirect:/checkListPersona/editarItemsPersona/" + modificarCheckListPersonaForm.getId();
	}
	
	@RequestMapping(value = "/checkListPersona/eliminarCheckList/{id}", method = RequestMethod.GET)
	public String eliminarCheckList(@PathVariable("id") final Integer idCheckList){		
		checkListPersonaService.eliminarCheckList(idCheckList);
		
		return "redirect:/checkListPersona/checkLists";
	}
	
	@RequestMapping(value = "/checkListPersona/eliminarItemList/{idItem}/{idCheckList}", method = RequestMethod.GET)
	public String eliminarItemList(@PathVariable("idItem") final Integer idItemList, @PathVariable("idCheckList") final Integer idCheckList){
		checkListPersonaService.eliminarItemList(idItemList);
		
		return "redirect:/checkListPersona/editarItemsPersona/" + idCheckList;
	}
}
