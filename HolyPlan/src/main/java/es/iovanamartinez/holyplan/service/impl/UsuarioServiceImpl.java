package es.iovanamartinez.holyplan.service.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dao.UsuarioDao;
import es.iovanamartinez.holyplan.dominio.Usuario;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.service.UsuarioService;

@Component
public class UsuarioServiceImpl implements UsuarioService {
	private static final String DIRECCION_CORREO_ORGIEN = "projectbike@empresagalega.eu";
	private static final String CONTRASENA = "Project_";
	private static final String PROTOCOLO = "smtp";
	private static final String PUERTO = "587";
	private static final String NOMBRE_HOST = "smtp.mundo-r.com";
	private static final String REQUIERE_AUTENTICARSE = "true";
	
	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	@Transactional
	public void crearUsuario(final UsuarioVo usuarioVo) {
		Usuario usuario = new Usuario(usuarioVo);
		usuarioDao.crear(usuario);
		
		try {
			enviarEmailActivacion(usuario);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public UsuarioVo getUsuarioActual() {
		return  this.getUsuarioPorNombre(SecurityContextHolder.getContext().getAuthentication().getName());
		//return (UsuarioVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@Override
	@Transactional(readOnly = true)
	public UsuarioVo getUsuarioPorNombre(String nombreUsuario) {
		Usuario usuario = usuarioDao.buscarPorNombreUsuario(nombreUsuario);
		if (usuario == null)
			return null;
		else
			return new UsuarioVo(usuario);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UsuarioVo getUsuarioPorEmail(String email){
		Usuario usuario = usuarioDao.buscarUsuarioPorEmail(email);
		if (usuario == null)
			return null;
		else
			return new UsuarioVo(usuario);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UsuarioVo getUsuarioPorHash(String hash){
		Usuario usuario = usuarioDao.buscarUsuarioPorEmail(hash);
		if (usuario == null)
			return null;
		else
			return new UsuarioVo(usuario);
	}
	
	@Override
	@Transactional
	public void activarUsuario(String hash) {
		Usuario usuario = usuarioDao.buscarUsuarioPorHash(hash);
		usuario.setActivo(true);
		usuarioDao.actualizar(usuario);
	}
	
	@Override
	@Transactional
	public void modificarNombreUsuario(String nuevoNombre, Integer id) {
		Usuario usuario = usuarioDao.buscar(id);
		usuario.setNombreUsuario(nuevoNombre);
		usuarioDao.actualizar(usuario);
	}
	
	@Override
	@Transactional
	public void modificarContrasena(Integer id, String contrasena) {
		Usuario usuario = usuarioDao.buscar(id);
		usuario.setContrasena(contrasena);
		usuarioDao.actualizar(usuario);
	}
	
	@Override
	@Transactional
	public void guardarEmailTemp(UsuarioVo usuarioVo, String email) {
		try {
			enviarEmailModificacion(usuarioVo, email);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		Usuario usuario = usuarioDao.buscar(usuarioVo.getId());
		usuario.setEmailTemp(email);
		usuarioDao.actualizar(usuario);
	}
	
	private void enviarEmailModificacion(UsuarioVo usuario, String email) throws AddressException, MessagingException {
		if (usuario.getHash() == null) throw new IllegalArgumentException("El hash de usuario no ha sido generado");
		
		String asunto = "Cambio de email en HolyPlan";

		String textoNuevoCorreo = "<p>Hola " + usuario.getNombreUsuario() + ",<br /><br />"
				+ "Has solicitado el cambio de la direcci&oacute;n de email asociada a tu cuenta. Si no est&aacute;s registrado en HolyPlan, simplemente ignora este correo.<br /><br />"
				+ "Si deseas asociar tu cuenta a esta direcci&ocatue;n de email solo tienes que hacer clic en el siguiente enlace: "
				+ "<a href=\"http://localhost:8080/HolyPlan/private/cambiarEmail?uid=" + usuario.getHash() + "\">cambiar email</a><br /><br />"
				+ "Si el anterior enlace no funciona, copia el siguiente enlace y p&eacute;galo en tu navegador: "
				+ "http://localhost:8080/HolyPlan/private/cambiarEmaill?uid=" + usuario.getHash() + "<br /><br />"
				+ "El equipo de HolyPlan.</p>";
		
		String textoAntiguoCorreo = "<p>Hola " + usuario.getNombreUsuario() + ",<br /><br />"
				+ "Recientemente cambiaste la dirección de correo electrónico asociada a tu cuenta. Para confirmar tu nuevo correo electrónico de contacto, por favor, "
				+ "sigue el enlace en el mensaje de confirmación enviado a esa dirección.<br /><br />"
				+ "Si tú no pediste este cambio y crees que tu cuenta ha sido comprometida, contacta al soporte técnico de HolyPlan.<br /><br />"
				+ "El equipo de HolyPlan.</p>";

		enviarEmailUsuario(asunto, textoNuevoCorreo, email);
		enviarEmailUsuario(asunto, textoAntiguoCorreo, usuario.getEmail());
	}
	
	private void enviarEmailActivacion(Usuario usuario) throws MessagingException, AddressException, IllegalArgumentException {
		if (usuario.getHash() == null) throw new IllegalArgumentException("El hash de usuario no ha sido generado");

		String asunto = "Bienvenido a HolyPlan";

		String texto = "<p>Hola " + usuario.getNombreUsuario() + ",<br /><br />"
				+ "Para completar el registro solo tienes que hacer clic en el siguiente enlace: "
				+ "<a href=\"http://localhost:8080/HolyPlan/public/activar?uid=" + usuario.getHash() + "\">activar cuenta</a><br /><br />"
				+ "Si el anterior enlace no funciona, copia la siguiente direcci&oacute;n y p&eacute;gala en tu navegador: "
				+ "http://localhost:8080/HolyPlan/public/activar?uid=" + usuario.getHash() + "<br /><br />"
				+ "El equipo de HolyPlan.</p>";

		enviarEmailUsuario(asunto, texto, usuario.getEmail());
	}
	
	private void enviarEmailUsuario(String asunto, String texto, String email) throws MessagingException, AddressException {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", NOMBRE_HOST);
		props.setProperty("mail.smtp.port", PUERTO);
		props.setProperty("mail.smtp.user", DIRECCION_CORREO_ORGIEN);
		props.setProperty("mail.smtp.auth", REQUIERE_AUTENTICARSE);

		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);

		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(DIRECCION_CORREO_ORGIEN));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		message.setSubject(asunto);
		message.setText(texto, "ISO-8859-1", "html");
		//TODO Contrasena en texto plano?
		Transport t = session.getTransport(PROTOCOLO);
		t.connect(DIRECCION_CORREO_ORGIEN,CONTRASENA);
		t.sendMessage(message,message.getAllRecipients());
		t.close();
	}

//	@Override
//	public void cambiarEmail(String hash) {
//		usuarioDao.cambiarEmail(hash);
//	}
	
	@Override
	@Transactional
	public void cambiarEmail(Integer id) {
		Usuario usuario = usuarioDao.buscar(id);
		if (usuario.getEmailTemp() != null && !usuario.getEmailTemp().isEmpty()) {
			usuario.setEmail(usuario.getEmailTemp());
			usuario.setEmailTemp(null);
			usuarioDao.actualizar(usuario);
		}
	}

//	@Override
//	public void desactivarUsuario(Integer id) {
//		usuarioDao.desactivarUsuario(id);
//		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
//	}
	
	@Override
	@Transactional
	public void desactivarUsuario(Integer id) {
		Usuario usuario = usuarioDao.buscar(id);
		usuario.setCuentaCerrada(true);
		usuarioDao.actualizar(usuario);
		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
	}
}
